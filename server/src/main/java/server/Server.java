package server;

import dataaccess.*;
import io.javalin.*;
import service.ClearService;
import service.GameService;
import service.UserService;

public class Server {

    private final Javalin javalin;
    private final UserDAO userDAO = new SQLUserDAO();
    private final GameDAO gameDAO = new MemoryGameDAO();
    private final AuthDAO authDAO = new MemoryAuthDAO();


    public Server() {
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


    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
