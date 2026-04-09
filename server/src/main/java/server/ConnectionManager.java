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

    public void add(Session session, Integer gameID) {
        connections.putIfAbsent(gameID, ConcurrentHashMap.newKeySet());
        connections.get(gameID).add(session);
    }

    public void remove(Session session, Integer gameID) {
        if (connections.containsKey(gameID)) {
            connections.get(gameID).remove(session);
        }
    }

    public void broadcast(Session excludeSession, Integer gameID, ServerMessage serverMessage) throws IOException {
        String msg = gson.toJson(serverMessage);
        var sessions = connections.getOrDefault(gameID, ConcurrentHashMap.newKeySet());
        for (Session s : sessions) {
            if (s.isOpen()) {
                if (!s.equals(excludeSession)) {
                    s.getRemote().sendString(msg);
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