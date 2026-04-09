package server;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.MakeMoveCommand;
import websocket.messages.ServerMessage;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.util.ArrayList;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final Gson gson = new Gson();
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public WebSocketHandler(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    @Override
    public void handleConnect(WsConnectContext ctx) {
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) {
        try {
            UserGameCommand action = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            switch (action.getCommandType()) {
                case CONNECT -> connect(action.getAuthToken(), action.getGameID(), ctx.session);
                case LEAVE -> leave(action.getAuthToken(), action.getGameID(), ctx.session);
                case RESIGN -> resign(action.getAuthToken(), action.getGameID(), ctx.session);
                case MAKE_MOVE -> {
                    MakeMoveCommand moveAction = gson.fromJson(ctx.message(), MakeMoveCommand.class);
                    makeMove(moveAction.getAuthToken(), moveAction.getGameID(), moveAction.getMove(), ctx.session);
                }

            }
        } catch (IOException | DataAccessException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }

    private void connect(String authToken, Integer gameID, Session session) throws IOException, DataAccessException {
        try {
            connections.add(session, gameID);
            System.out.println("sessions in game " + gameID + ": " + connections.connections.get(gameID).size());
            String playerNameDebug = authDAO.getAuth(authToken) != null ? authDAO.getAuth(authToken).username() : "unknown";
            System.out.println("connecting player: " + playerNameDebug);            var auth = authDAO.getAuth(authToken);
            GameData game = gameDAO.getGame(gameID);
            //validate authtoken
            if (!validate(auth, game, session)) {
                return;
            }

            //send load_game to root player
            String playerName = auth.username();
            var loadGameMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
            session.getRemote().sendString(new Gson().toJson(loadGameMessage));

            //send notification to all other players

            String role = playerName.equals(game.whiteUsername()) ? "white" :
                    playerName.equals(game.blackUsername()) ? "black" : "observer";
            connections.broadcast(session, gameID, new ServerMessage(
                    ServerMessage.ServerMessageType.NOTIFICATION,
                    String.format("%s has joined as %s", playerName, role)));


        } catch (Exception e) {
            sendError(session, "Error: " + e.getMessage());
        }

    }

    private void leave(String authToken, Integer gameID, Session session) throws IOException, DataAccessException {
        try {
            AuthData auth = authDAO.getAuth(authToken);
            String playerName = auth.username();
            GameData game = gameDAO.getGame(gameID);

            if (!validate(auth, game, session)) {
                return;
            }

            if (playerName.equals(game.whiteUsername())) {
                gameDAO.updateGame("WHITE", gameID, null);
            } else if (playerName.equals(game.blackUsername())) {
                gameDAO.updateGame("BLACK", gameID, null);
            }

            sendMessage(session, "%s has left the game", playerName, gameID);

            connections.remove(session, gameID);
        } catch (Exception e) {
            sendError(session, "Error: " + e.getMessage());
        }

    }

    private void resign(String authToken, Integer gameID, Session session) throws IOException, DataAccessException {
        try {
            AuthData auth = authDAO.getAuth(authToken);
            GameData game = gameDAO.getGame(gameID);

            if (!validate(auth, game, session)) {
                return;
            }
            //check game status
            if (game.game().getTeamTurn() == null) {
                sendError(session, "Error: Game already over");
                return;
            }

            // check if is observer
            String playerName = auth.username();
            if (!playerName.equals(game.whiteUsername()) && !playerName.equals(game.blackUsername())) {
                sendError(session, "Error: Observers cannot resign");
                return;
            }

            game.game().setTeamTurn(null); //reset game turn
            gameDAO.updateGame(game);

            String winner = playerName.equals(game.whiteUsername()) ? "black" : "white";
            connections.broadcastToAll(gameID, new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                    String.format("%s has resigned. %s wins!", playerName, winner)));
        } catch (Exception e) {
            sendError(session, "Error: " + e.getMessage());
        }
    }

    private void makeMove(String authToken, Integer gameID, ChessMove move, Session session) throws IOException, DataAccessException {
        try {

            //validate auth and game
            AuthData auth = authDAO.getAuth(authToken);
            GameData game = gameDAO.getGame(gameID);
            System.out.println("teamTurn: " + game.game().getTeamTurn());

            if (!validate(auth, game, session)) {
                return;
            }
            String playerName = auth.username();
            //check if game is over
            if (game.game().getTeamTurn() == null) {
                sendError(session, "Error: Game over");
                return;
            }
            //check whos turn
            //check white turn
            if (playerName.equals(game.whiteUsername()) && game.game().getTeamTurn() != ChessGame.TeamColor.WHITE) {
                sendError(session, "Error: not your turn");
                return;
            }
            //check black turn
            if (playerName.equals(game.blackUsername()) && game.game().getTeamTurn() != ChessGame.TeamColor.BLACK) {
                sendError(session, "Error: not your turn");
                return;
            }
            //check observer
            if (!playerName.equals(game.whiteUsername()) && !playerName.equals(game.blackUsername())) {
                sendError(session, "Error: Observers cannot make moves");
                return;
            }

            //apply move
            try  {
                game.game().makeMove(move);

            } catch (InvalidMoveException e) {
                sendError(session, "Error: invalid move - " + e.getMessage());
                return;
            }
            //update game
            gameDAO.updateGame(game);

            //send updated game
            var loadGameMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
            connections.broadcastToAll(gameID, loadGameMessage);
            //send message for that dub
            if (game.game().isInCheckmate(ChessGame.TeamColor.WHITE) ||
                    game.game().isInCheckmate(ChessGame.TeamColor.BLACK)) {

                String winner = game.game().isInCheckmate(ChessGame.TeamColor.WHITE) ? "black" : "white";

                connections.broadcast(session, gameID, new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                        String.format("%s has made a move", playerName)));

                connections.broadcastToAll(gameID, new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                        String.format("Checkmate, %s wins!", winner)));

            } else if (game.game().isInCheck(ChessGame.TeamColor.WHITE)) {
                connections.broadcastToAll(gameID, new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                        "White is in check!"));
            } else if (game.game().isInCheck(ChessGame.TeamColor.BLACK)) {
                connections.broadcastToAll(gameID, new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                        "Black is in check!"));
            } else {
                connections.broadcast(session, gameID, new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                        String.format("%s moved from %s to %s", playerName,
                                moveToString(move.getStartPosition()), moveToString(move.getEndPosition()))));
            }

        } catch (Exception e) {
            sendError(session, "Error: " + e.getMessage());
        }
    }

    private void sendError(Session session, String msg) throws IOException {
        var errorMessage = new ServerMessage(ServerMessage.ServerMessageType.ERROR, new Exception(msg));
        session.getRemote().sendString(new Gson().toJson(errorMessage));
    }

    private void sendMessage(Session session, String msg, String playerName, Integer gameID) throws IOException {
        var message = String.format(msg, playerName);
        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(session, gameID, serverMessage);
    }

    private String moveToString(chess.ChessPosition pos) {
        char col = (char) ('a' + pos.getColumn() - 1);
        return "" + col + pos.getRow();
    }

    private boolean validate(AuthData auth, GameData game, Session session) throws IOException {
        if (auth == null) {
            //if invalid, throw error and leave session
            sendError(session, "Error: Unauthorized");
            return false;
        }
        //validate gameid
        if (game == null) {
            //if invalid, throw error and leave session
            sendError(session, "Error: Bad game ID");
            return false;
        }
        return true;
    }

}