package server;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;


public class ConnectionManager {

    private final Gson gson = new Gson();
    public final ConcurrentHashMap<Integer, ConcurrentHashMap.KeySetView<Session, Boolean>> connections
            = new ConcurrentHashMap<>();

    public void add(Session session) {
        connections.put(session, session);
    }

    public void remove(Session session) {
        connections.remove(session);
    }

    public void broadcast(Session excludeSession, ServerMessage serverMessage) throws IOException {
        String msg = gson.toJson(serverMessage);
        for (Connection c : connections) {
            if (c.isOpen()) {
                if (!c.equals(excludeSession)) {
                    c.getRemote().sendString(msg);
                }
            }
        }
    }

    public void broadcastToAll(Integer gameID, ServerMessage serverMessage) throws IOException {
        String msg = new Gson().toJson(serverMessage);
        var gameSessions = connections.getOrDefault(gameID, ConcurrentHashMap.newKeySet());
        for (Session s : gameSessions) {
            if (s.isOpen()) {
                s.getRemote().sendString(msg);
            }
        }
    }

}