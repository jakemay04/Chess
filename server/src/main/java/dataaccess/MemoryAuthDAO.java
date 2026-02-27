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
            auth.put(a.username(), a.authToken());
        }
        else {
            throw new DataAccessException("Invalid session");
        }
    }

    public void getAuth(AuthData a) throws DataAccessException {
        AuthDAO.super.getAuth(a);
    }

    public void clear() {
        auth = null;
    }
}
