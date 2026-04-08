package server;

import chess.ChessMove;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import exception.ResponseException;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import model.UserData;
import org.eclipse.jetty.websocket.api.Session;
import server.ConnectionManager;
import service.UserService;
import websocket.commands.MakeMoveCommand;
import websocket.messages.ServerMessage;
import websocket.commands.UserGameCommand;
import com.google.gson.Gson;

import java.io.IOException;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final Gson gson = new Gson();
    private final UserDAO userDAO;

    public WebSocketHandler(UserDAO userDAO) {
        this.userDAO = userDAO;
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
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }

    private void connect(String authToken, Integer gameID, Session session) throws IOException, DataAccessException {
        connections.add(session);
        UserData playerName = userDAO.getUser(authToken);
        var message = String.format("%s has entered the game", playerName);
        var serverMessage = new ServerMessage(ServerMessage.Type.CONNECT, message);
        connections.broadcast(session, serverMessage);
    }

    private void leave(String authToken, Integer gameID, Session session) throws IOException, DataAccessException {
        UserData playerName = userDAO.getUser(authToken);
        var message = String.format("%s has left the game", playerName);
        var serverMessage = new ServerMessage(ServerMessage.Type.LEAVE, message);
        connections.broadcast(session, serverMessage);
        connections.remove(session);
    }

    private void resign(String authToken, Integer gameID, Session session) throws IOException, DataAccessException {
        UserData playerName = userDAO.getUser(authToken);
        var message = String.format("%s has resigned", playerName);
        var serverMessage = new ServerMessage(ServerMessage.Type.RESIGN, message);
        connections.broadcast(session, serverMessage);
        connections.remove(session);
        //END GAME HERE after leaving

    }

    private void makeMove(String authToken, Integer gameID, ChessMove move, Session session) throws IOException, DataAccessException {
        UserData playerName = userDAO.getUser(authToken);
        var message = String.format("%s has resigned", playerName);
        var serverMessage = new ServerMessage(ServerMessage.Type.MAKE_MOVE, message);
        //call make move from server
        connections.broadcast(session, serverMessage);
    }

}