package server;

import chess.ChessMove;
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
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.MakeMoveCommand;
import websocket.messages.ServerMessage;
import websocket.commands.UserGameCommand;

import java.io.IOException;

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
            //validate authtoken
            if (auth == null) {
                //if invalid, throw error and leave session
                var errorMessage = new ServerMessage(ServerMessage.ServerMessageType.ERROR, "Error: Unauthorized");
                session.getRemote().sendString(new Gson().toJson(errorMessage));
                connections.remove(session);
                return;
            }
            //validate gameid
            GameData game = gameDAO.getGame(gameID);
            if (game == null) {
                //if invalid, throw error and leave session
                var errorMessage = new ServerMessage(ServerMessage.ServerMessageType.ERROR, "Error: Unauthorized");
                session.getRemote().sendString(new Gson().toJson(errorMessage));
                connections.remove(session);
                return;
            }

            //send load_game to root player
            String playerName = auth.username();
            var loadGameMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
            session.getRemote().sendString(new Gson().toJson(loadGameMessage));

            //send notification to all other players
            var message = String.format("%s has entered the game", playerName);
            var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(session, serverMessage);
        } catch (Exception e) {
            sendError(session, "Error: " + e.getMessage());
        }

    }

    private void leave(String authToken, Integer gameID, Session session) throws IOException, DataAccessException {
        String playerName = authDAO.getAuth(authToken).username();
        var message = String.format("%s has left the game", playerName);
        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(session, serverMessage);
        connections.remove(session);
    }

    private void resign(String authToken, Integer gameID, Session session) throws IOException, DataAccessException {
        String playerName = authDAO.getAuth(authToken).username();
        var message = String.format("%s has resigned", playerName);
        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(session, serverMessage);
        connections.remove(session);
        //END GAME HERE after leaving

    }

    private void makeMove(String authToken, Integer gameID, ChessMove move, Session session) throws IOException, DataAccessException {
        String playerName = authDAO.getAuth(authToken).username();
        var message = String.format("%s has made a move", playerName);
        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, message);
        //call make move from server
        connections.broadcast(session, serverMessage);
    }

    private void sendError(Session session, String message) throws IOException {
        var errorMessage = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
        session.getRemote().sendString(new Gson().toJson(errorMessage));
    }

}