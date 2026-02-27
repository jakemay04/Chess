package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO{
    private Map<String, String> auth = new HashMap<String, String>();

    public void insertAuth(AuthData a) throws DataAccessException {
        if (a.username() != null) {
            auth.put(a.authToken(), a.username());
        }
        else {
            throw new DataAccessException("Invalid session");
        }
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        if (authToken != null && auth.containsKey(authToken)) {
            return new AuthData(auth.get(authToken), authToken);
        }
        else {
            throw new DataAccessException("Invalid session");
        }
    }

    public void deleteAuth(AuthData a) throws DataAccessException {
        if (a.username() != null && auth.containsKey(a.username())) {
            auth.remove(a.username());
        }
        else {
            throw new DataAccessException("Invalid session");
        }
    }

    public void clear() {
        auth = null;
    }
}
