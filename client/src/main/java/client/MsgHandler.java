package client;

import websocket.messages.ServerMessage;

public interface MsgHandler {
    void notify(ServerMessage message);
}