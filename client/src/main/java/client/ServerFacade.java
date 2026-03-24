package client;

import records.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import java.net.http.*;
import exception.ResponseException;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverURL;

    public ServerFacade(String url) {
        serverURL = url;
    }

    public RegisterResult register(RegisterRequest request) throws ResponseException {
        var httpRequest = buildRequest("POST", "/user", request, null);
        var response = sendRequest(httpRequest);
        return handleResponse(response, RegisterResult.class);
    }

    public LoginResult login(LoginRequest request) throws ResponseException {
        var httpRequest = buildRequest("POST", "/session", request, null);
        var response = sendRequest(httpRequest);
        return handleResponse(response, LoginResult.class);
    }

    public void joinGame(JoinGameRequest request, String authToken) throws ResponseException {
        var httpRequest = buildRequest("PUT", "/game", request, authToken);
        var response = sendRequest(httpRequest);
        handleResponse(response, null);
    }
    public CreateGameResult createGame(CreateGameRequest request, String authToken) throws ResponseException {
        var httpRequest = buildRequest("POST", "/game", request, authToken);
        var response = sendRequest(httpRequest);
        return handleResponse(response, CreateGameResult.class);
    }

    public ListGamesResult listGames(ListGamesRequest request, String authToken) throws ResponseException {
        var httpRequest = buildRequest("GET", "/game", null, authToken);
        var response = sendRequest(httpRequest);
        return handleResponse(response, ListGamesResult.class);
    }

    public void logout(LogoutRequest request, String authToken) throws ResponseException {
        var httpRequest = buildRequest("DELETE", "/session", null, authToken);
        var response = sendRequest(httpRequest);
        handleResponse(response, null);
    }

    //helper functions
    private HttpRequest buildRequest(String method, String path, Object body, String authToken) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.header("Content-Type", "application/json");
        }
        if (authToken != null) {
            request.header("authorization", authToken);
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
            System.out.println("Raw JSON: " + response.body());

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

    private HttpResponse<String> sendRequest(HttpRequest request) throws ResponseException {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
