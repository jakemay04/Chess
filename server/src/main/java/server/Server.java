package server;

import dataaccess.*;
import io.javalin.*;
import io.javalin.websocket.WsConnectHandler;
import org.jetbrains.annotations.NotNull;
import service.ClearService;
import service.GameService;
import service.UserService;

public class Server {

    private final Javalin javalin;
    private final UserDAO userDAO = new SQLUserDAO();
    private final GameDAO gameDAO = new SQLGameDAO();
    private final AuthDAO authDAO = new SQLAuthDAO();
    private final WebSocketHandler webSocketHandler;


    public Server() {
        this.webSocketHandler = new WebSocketHandler(authDAO);
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        var userService = new UserService(userDAO,authDAO);
        var gameService  = new GameService(userDAO, authDAO, gameDAO);
        var clearService = new ClearService(userDAO, gameDAO, authDAO);

        var handler = new Handler(userService, gameService, clearService);

        javalin.delete("/db", handler::clear);
        javalin.post("/user", handler::register);
        javalin.post("/session", handler::login);
        javalin.delete("/session", handler::logout);
        javalin.post("/game", handler::createGame);
        javalin.get("/game", handler::listGames);
        javalin.put("/game", handler::joinGame);
        javalin.ws("/ws", ws -> {
            ws.onConnect(webSocketHandler);
            ws.onMessage(webSocketHandler);
            ws.onClose(webSocketHandler);
        });


    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
