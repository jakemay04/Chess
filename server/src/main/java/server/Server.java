package server;

import dataaccess.*;
import io.javalin.*;
import service.UserService;

public class Server {

    private final Javalin javalin;
    private final UserDAO userDAO = new MemoryUserDAO();
    private final GameDAO gameDAO = new MemoryGameDAO();
    private final AuthDAO authDAO = new MemoryAuthDAO();


    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        var userService = new UserService(userDAO,authDAO);

        var handler = new Handler(userService);

        javalin.post("/user", handler::register);
        javalin.post("/session", handler::login);

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
