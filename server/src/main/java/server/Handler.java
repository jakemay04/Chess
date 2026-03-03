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

    private void handleException (Context ctx, DataAccessException e) {
        if (e.getMessage().contains("Error: bad request")) {
            ctx.status(400).result(gson.toJson(Map.of("message", e.getMessage())));
        }
        else if (e.getMessage().contains("unauthorized")){
            ctx.status(401).result(gson.toJson(Map.of("message", e.getMessage())));
        }
        else if (e.getMessage().contains("already taken")) {
            ctx.status(403).result(gson.toJson(Map.of("message", e.getMessage())));
        }
        else {
            ctx.status(500).result(gson.toJson(Map.of("message", e.getMessage())));
        }
    }

    public void register (Context ctx) {
        try {
            var request = gson.fromJson(ctx.body(), RegisterRequest.class);
            var result = userService.register(request);
            ctx.status(200).result(gson.toJson(result));
        } catch (DataAccessException e) {
            handleException(ctx,e);
        }
    }

    public void login(Context ctx) {
        try {
            var request = gson.fromJson(ctx.body(), LoginRequest.class);
            var result = userService.login(request);
            ctx.status(200).result(gson.toJson(result));
        } catch (DataAccessException e) {
            handleException(ctx,e);
        }
    }

    public void clear(Context ctx) {
        try {
            clearService.clear();
            ctx.status(200).json("{}");
        }  catch (DataAccessException e) {
            ctx.status(500).result(gson.toJson(Map.of("message", e.getMessage())));
        }
    }

    public void logout(Context ctx) {
        try {
            String authToken = ctx.header("authorization");
            userService.logout(new LogoutRequest(authToken));
            ctx.status(200).result(gson.toJson(Map.of()));
        } catch (DataAccessException e) {
            handleException(ctx,e);
        }
    }

    public void createGame(Context ctx) {
        try {
            var body = gson.fromJson(ctx.body(), Map.class);
            var req = new CreateGameRequest(ctx.header("authorization"), (String) body.get("gameName"));
            var result = gameService.createGame(req);
            ctx.status(200).result(gson.toJson(result));

        } catch (DataAccessException e) {
            handleException(ctx,e);
        }
    }

    public void listGames(Context ctx) {
        try {
            var req = new ListGamesRequest(ctx.header("authorization"));
            var result = gameService.listGames(req);
            ctx.status(200).result(gson.toJson(result));
        } catch (DataAccessException e) {
            handleException(ctx,e);
        }
    }

    public void joinGame(Context ctx) {
        try {
            var body = gson.fromJson(ctx.body(), Map.class);
            if (body.get("gameID") == null) {
                ctx.status(400).result(gson.toJson(Map.of("message", "Error: bad request")));
                return;
            }
            var req = new JoinGameRequest(
                    ctx.header("authorization"),
                    (String) body.get("playerColor"),
                    ((Double) body.get("gameID")).intValue()
            );
            gameService.joinGame(req);
            ctx.status(200).json("{}");
        } catch (DataAccessException e) {
            if (e.getMessage().contains("Error: unauthorized")) {
                ctx.status(401).result(gson.toJson(Map.of("message", e.getMessage())));
            }
            else if (e.getMessage().contains("Error: bad request")) {
                ctx.status(400).result(gson.toJson(Map.of("message", e.getMessage())));
            }
            else if (e.getMessage().contains("Error: already taken")) {
                ctx.status(403).result(gson.toJson(Map.of("message", e.getMessage())));
            }
            else {
                ctx.status(500).result(gson.toJson(Map.of("message", e.getMessage())));
            }
        } catch (Exception e) {
            ctx.status(500).result(gson.toJson(Map.of("message", "Error" + e.getMessage())));
        }
    }

}
