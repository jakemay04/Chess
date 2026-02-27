package dataaccess;

import model.AuthData;
import model.UserData;

public interface AuthDAO {
    void insertAuth(AuthData a) throws DataAccessException;
    AuthData getAuth(AuthData a) throws DataAccessException;
    void deleteAuth(AuthData a) throws DataAccessException;
    void clear();
}

