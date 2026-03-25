package exception;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ResponseException extends Exception {

    public enum Code {
        ServerError,
        ClientError,
    }

    public ResponseException(Code code, String message) {
        super(message);
    }

    public static ResponseException fromJson(String json) {
        try {
            var map = new Gson().fromJson(json, HashMap.class);
            var message = map.get("message") != null ? map.get("message").toString() : json;
            Code status = Code.ServerError; // default
            if (map.get("status") != null) {
                try {
                    status = Code.valueOf(map.get("status").toString());
                } catch (IllegalArgumentException ignored) {}
            }
            return new ResponseException(status, message);
        } catch (Exception e) {
            return new ResponseException(Code.ServerError, json);
        }
    }

    public static Code fromHttpStatusCode(int httpStatusCode) {
        return switch (httpStatusCode) {
            case 500 -> Code.ServerError;
            case 400 -> Code.ClientError;
            default -> throw new IllegalArgumentException("Unknown HTTP status code: " + httpStatusCode);
        };
    }
}