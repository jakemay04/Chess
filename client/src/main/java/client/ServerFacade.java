package client;

import records.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import java.net.http.*;


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

    public void logout(LogoutRequest request) throws exception.ResponseException {
        var httpRequest = buildRequest("DELETE", "/session", request);
        var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response, RegisterResult.class);
    }

    //helper functions
    private HttpRequest buildRequest(String method, String path, Object body) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        }
        return request.build();
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws exception.ResponseException {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                throw exception.ResponseException.fromJson(body);
            }

            throw new exception.ResponseException(exception.ResponseException.fromHttpStatusCode(status), "other failure: " + status);
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private HttpRequest.BodyPublisher makeRequestBody(Object body) {
        if (body != null) {
            return HttpRequest.BodyPublishers.ofString(new Gson().toJson(body));
        }
        return HttpRequest.BodyPublishers.noBody();
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
