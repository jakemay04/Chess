package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import org.jetbrains.annotations.NotNull;
import service.LoginRequest;
import service.RegisterRequest;
import service.UserService;

import io.javalin.http.Context;
import java.util.Map;

public class Handler {
    private final Gson gson = new Gson();
    private final UserService userService;

    public Handler(UserService u) {
        this.userService = u;
    }

    public void register (Context ctx) {
        try {
            var request = gson.fromJson(ctx.body(), RegisterRequest.class);
            var result = userService.register(request);
            ctx.status(200).result(gson.toJson(result));
        } catch (DataAccessException e) {
            if (e.getMessage().contains("bad request")) ctx.status(400).json(Map.of("message", e.getMessage()));
        }
    }

    public void login(Context ctx) {
        try {
            var request = gson.fromJson(ctx.body(), LoginRequest.class);
            var result = userService.login(request);
            ctx.status(200).result(gson.toJson(result));
        } catch (DataAccessException e) {
            if (e.getMessage().contains("bad request")) ctx.status(400).json(Map.of("message", e.getMessage()));
        }
    }
}
