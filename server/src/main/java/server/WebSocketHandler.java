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
            connections.add(session);
            var auth = authDAO.getAuth(authToken);
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
//            var message = String.format("%s has entered the game", playerName);
//            var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
//            connections.broadcast(session, serverMessage);

            sendMessage(session, "%s has entered the game", playerName);

        } catch (Exception e) {
            sendError(session, "Error: " + e.getMessage());
        }

    }

    private void leave(String authToken, Integer gameID, Session session) throws IOException, DataAccessException {
        try {
            AuthData auth = authDAO.getAuth(authToken);
            String playerName = authDAO.getAuth(authToken).username();
            GameData game = gameDAO.getGame(gameID);

            if (!validate(auth, game, session)) {
                return;
            }

            if (playerName.equals(game.whiteUsername())) {
                gameDAO.updateGame("WHITE", gameID, null);
            } else if (playerName.equals(game.blackUsername())) {
                gameDAO.updateGame("BLACK", gameID, null);
            }
//        var message = String.format("%s has left the game", playerName);
//        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
//        connections.broadcast(session, serverMessage);

            sendMessage(session, "%s has left the game", playerName);

            connections.remove(session);
        } catch (Exception e) {
            sendError(session, "Error: " + e.getMessage());
        }

    }

    private void resign(String authToken, Integer gameID, Session session) throws IOException, DataAccessException {
        try {
            AuthData auth = authDAO.getAuth(authToken);
            GameData game = gameDAO.getGame(gameID);

//        var message = String.format("%s has resigned", playerName);
//        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
//        connections.broadcast(session, serverMessage);
            //END GAME HERE after leaving
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

            connections.broadcastToAll(new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                    String.format("%s has resigned", playerName)));
        } catch (Exception e) {
            sendError(session, "Error: " + e.getMessage());
        }
    }

    private void makeMove(String authToken, Integer gameID, ChessMove move, Session session) throws IOException, DataAccessException {
        try {

            //validate auth and game
            AuthData auth = authDAO.getAuth(authToken);
            GameData game = gameDAO.getGame(gameID);

            if (!validate(auth, game, session)) {
                return;
            }
            String playerName = auth.username();
            //check if game is over
            if (game.game().getTeamTurn() == null) {
                sendError(session, "Error: Game over");
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
            connections.broadcastToAll(loadGameMessage);

            //send message
            String gameMessage = "";
            if (game.game().isInCheckmate(ChessGame.TeamColor.WHITE)) {
                gameMessage = "Checkmate, black wins!";
            } else if (game.game().isInCheckmate(ChessGame.TeamColor.BLACK)) {
                gameMessage = "Checkmate, white wins!";
            } else {
                gameMessage = String.format("%s has made a move", playerName);
            }

            connections.broadcastToAll(new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, gameMessage));

        } catch (Exception e) {
            sendError(session, "Error: " + e.getMessage());
        }
    }

    private void sendError(Session session, String msg) throws IOException {
        var errorMessage = new ServerMessage(ServerMessage.ServerMessageType.ERROR, new Exception(msg));
        session.getRemote().sendString(new Gson().toJson(errorMessage));
    }

    private void sendMessage(Session session, String msg, String playerName) throws IOException {
        var message = String.format(msg, playerName);
        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(session, serverMessage);
    }

    private boolean validate(AuthData auth, GameData game, Session session) throws IOException, DataAccessException {
        if (auth == null) {
            //if invalid, throw error and leave session
            sendError(session, "Error: Unauthorized");
            connections.remove(session);
            return false;
        }
        //validate gameid
        if (game == null) {
            //if invalid, throw error and leave session
            sendError(session, "Error: Bad game ID");
            connections.remove(session);
            return false;
        }
        return true;
    }

}