package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO{
    private Map<String, AuthData> auth = new HashMap<String, AuthData>();

    public void insertAuth(AuthData a) throws DataAccessException {
        if (a.username() != null) {
            auth.put(a.authToken(), a);
        }
        else {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        if (authToken != null && auth.containsKey(authToken)) {
            return auth.get(authToken);
        }
        else {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        if (authToken != null && auth.containsKey(authToken)) {
            auth.remove(authToken);
        }
        else {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public void clear() {
        auth.clear();
    }
}
