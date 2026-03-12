package dataaccess;

import model.AuthData;
import model.UserData;

public interface AuthDAO {
    void insertAuth(AuthData a) throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    void clear() throws DataAccessException;
}

