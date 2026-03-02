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

    public Handler(UserService u, GameService g, ClearService c) {
        this.userService = u;
        this.gameService = g;
        this.clearService = c;
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
            userService.logout(new LogoutRequest(authToken));
            ctx.status(200).result(gson.toJson(Map.of()));
        } catch (DataAccessException e) {
            ctx.status(400).result(gson.toJson(Map.of("message", e.getMessage())));

        }
    }

    public void createGame(Context ctx) {
        try {
            var body = gson.fromJson(ctx.body(), Map.class);
            var req = new CreateGameRequest(ctx.header("authorization"), (String) body.get("gameName"));
            var result = gameService.createGame(req);
            ctx.status(200).result(gson.toJson(result));

        } catch (DataAccessException e) {
            ctx.status(400).result(gson.toJson(Map.of("message", e.getMessage())));

        }
    }

    public void listGames(Context ctx) {
        try {
            var req = new ListGamesRequest(ctx.header("authorization"));
            var result = gameService.listGames(req);
            ctx.status(200).result(gson.toJson(result));
        } catch (DataAccessException e) {
            ctx.status(400).result(gson.toJson(Map.of("message", e.getMessage())));

        }
    }
    public void listGames(Context ctx) {
        try {
            var req = new ListGamesRequest(ctx.header("authorization"));
            var result = gameService.listGames(req);
            ctx.status(200).result(gson.toJson(result));
        } catch (DataAccessException e) {
            ctx.status(400).result(gson.toJson(Map.of("message", e.getMessage())));

        }
    }

}
