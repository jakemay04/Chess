package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import org.jetbrains.annotations.NotNull;
import service.*;

import io.javalin.http.Context;
import java.util.Map;

public class Handler {
    private final Gson gson = new Gson();
    private final UserService userService;
    private final GameService gameService;
    private final ClearService clearService;

    public Handler(UserService u) {
        this.userService = u;
    }

    public void register (Context ctx) {
        try {
            var request = gson.fromJson(ctx.body(), RegisterRequest.class);
            var result = userService.register(request);
            ctx.status(200).result(gson.toJson(result));
        } catch (DataAccessException e) {
            ctx.status(400).result(gson.toJson(Map.of("message", e.getMessage())));
        }
    }

    public void login(Context ctx) {
        try {
            var request = gson.fromJson(ctx.body(), LoginRequest.class);
            var result = userService.login(request);
            ctx.status(200).result(gson.toJson(result));
        } catch (DataAccessException e) {
            ctx.status(400).result(gson.toJson(Map.of("message", e.getMessage())));

        }
    }

    public void clear(Context ctx) {
        try {
            ClearService.clear();
            ctx.status(200).json("{}");
        }  catch (DataAccessException e) {
            ctx.status(400).result(gson.toJson(Map.of("message", e.getMessage())));

        }
    }

    public void logout(Context ctx) {
        try {
            String authToken = ctx.header("authorization");
            System.out.println("Logout token received: " + authToken);
            userService.logout(new LogoutRequest(authToken));
            ctx.status(200).json("{}");
        } catch (DataAccessException e) {
            ctx.status(400).result(gson.toJson(Map.of("message", e.getMessage())));

        }
    }
}
