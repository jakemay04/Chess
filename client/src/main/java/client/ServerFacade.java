package client;

import records.*;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverURL;

    public ServerFacade(String url) {
        serverURL = url;
    }

    public RegisterResult register(RegisterRequest request) {
        var httpRequest = buildRequest("POST", "/user", request);
        var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response, RegisterResult.class);
    }

    public LoginResult login(LoginRequest request) {
        var httpRequest = buildRequest("POST", "/session", request);
        var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response, RegisterResult.class);
    }

    public void joinGame(JoinGameRequest request) {
        var httpRequest = buildRequest("PUT", "/game", request);
        var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response, RegisterResult.class);
    }

    public CreateGameResult createGame(CreateGameRequest request) {
        var httpRequest = buildRequest("POST", "/game", request);
        var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response, RegisterResult.class);
    }

    public ListGamesResult listGames(ListGamesRequest request) {
        var httpRequest = buildRequest("GET", "/game", request);
        var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response, RegisterResult.class);
    }

    public void logout(LogoutRequest request) {
        var httpRequest = buildRequest("DELETE", "/session", request);
        var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response, RegisterResult.class);
    }

    //helper functions

}
