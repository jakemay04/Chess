package server;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;


public class ConnectionManager {

    private final Gson gson = new Gson();
    public final ConcurrentHashMap<Session, Session> connections = new ConcurrentHashMap<>();

    public void add(Session session) {
        connections.put(session, session);
    }

    public void remove(Session session) {
        connections.remove(session);
    }

    public void broadcast(Session excludeSession, ServerMessage serverMessage) throws IOException {
        String msg = gson.toJson(serverMessage);
        for (Session c : connections.values()) {
            if (c.isOpen()) {
                if (!c.equals(excludeSession)) {
                    c.getRemote().sendString(msg);
                }
            }
        }
    }

    public void broadcastToAll(ServerMessage serverMessage) throws IOException {
        String msg = gson.toJson(serverMessage);
        for (Session c : connections.values()) {
            if (c.isOpen()) {
                c.getRemote().sendString(msg);
            }
        }
    }

}