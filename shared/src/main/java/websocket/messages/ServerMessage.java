package websocket.messages;

import model.GameData;

import java.util.Objects;

/**
 * Represents a Message the server can send through a WebSocket
 * <p>
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class ServerMessage {
    ServerMessageType serverMessageType;
    String message;
    String errorMessage;
    GameData game;

    public enum ServerMessageType {
        LOAD_GAME,
        ERROR,
        NOTIFICATION
    }

    public ServerMessage(ServerMessageType type) {
        this.serverMessageType = type;
    }

    //only runs with server message
    public ServerMessage(ServerMessageType type, String message) {
        this.serverMessageType = type;
        this.message = message;
    }

    //only runs with error message
    public ServerMessage(ServerMessageType type, Exception e) {
        this.serverMessageType = type;
        this.errorMessage = e.getMessage();
    }

    //only runs with game data
    public ServerMessage(ServerMessageType type, GameData game) {
        this.serverMessageType = type;
        this.game = game;
    }

    public String getMessage() {
        return message;
    }

    public GameData getGame() {
        return game;
    }

    public ServerMessageType getServerMessageType() {
        return this.serverMessageType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

//    public loadGame() {
//
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServerMessage that)) {
            return false;
        }
        return getServerMessageType() == that.getServerMessageType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServerMessageType());
    }
}
