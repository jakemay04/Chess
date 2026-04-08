package server;

import chess.ChessMove;
import com.google.gson.Gson;
import exception.ResponseException;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import org.eclipse.jetty.websocket.api.Session;
import server.ConnectionManager;
import websocket.messages.ServerMessage;
import websocket.commands.UserGameCommand;

import java.io.IOException;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @Override
    public void handleConnect(WsConnectContext ctx) {
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) {
        try {
            UserGameCommand action = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            switch (action.type()) {
                case CONNECT -> connect(action.playerName(), ctx.session);
                case LEAVE -> leave(action.playerName(), ctx.session);
                case RESIGN -> resign(action.playerName(), ctx.session);
                case MAKE_MOVE -> makeMove(action.playerName(), ChessMove move, ctx.session);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }

    private void connect(String playerName, Session session) throws IOException {
        connections.add(session);
        var message = String.format("%s has entered the game", playerName);
        var notification = new ServerMessage(ServerMessage.Type.CONNECT, message);
        connections.broadcast(session, serverMessage);
    }

    private void leave(String playerName, Session session) throws IOException {
        var message = String.format("%s has left the game", playerName);
        var notification = new ServerMessage(ServerMessage.Type.LEAVE, message);
        connections.broadcast(session, serverMessage);
        connections.remove(session);
    }

    private void resign(String playerName, Session session) throws IOException {
        var message = String.format("%s has resigned", playerName);
        var notification = new ServerMessage(ServerMessage.Type.LEAVE, message);
        connections.broadcast(session, serverMessage);
        connections.remove(session);
    }

    private void makeMove(String playerName, ChessMove move, Session session) throws IOException {

    }



        public void makeNoise(String petName, String sound) throws ResponseException {
        try {
            var message = String.format("%s says %s", petName, sound);
            var notification = new Notification(Notification.Type.RESIGN, message);
            connections.broadcast(null, notification);
        } catch (Exception ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }
}