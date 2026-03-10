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
        if (authToken == null) {
            throw new DataAccessException("Error: bad request");
        }
        else if (!auth.containsKey(authToken)){
            throw new DataAccessException("Error: unauthorized");
        }
        return auth.get(authToken);
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        if (authToken == null) {
            throw new DataAccessException("Error: bad request");
        }
        else if (!auth.containsKey(authToken)){
            throw new DataAccessException("Error: unauthorized");
        }
        auth.remove(authToken);

    }

    public void clear() {
        auth.clear();
    }
}
