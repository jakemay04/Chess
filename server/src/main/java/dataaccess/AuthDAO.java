package dataaccess;

import model.AuthData;
import model.UserData;

public interface AuthDAO {
    default void insertAuth(AuthData a) throws DataAccessException {}
    default void getAuth(AuthData a) throws DataAccessException {}
    default void updateAuth(AuthData a) throws DataAccessException {}
    default void deleteAuth(AuthData a) throws DataAccessException {}
    default void gameList(AuthData a) throws DataAccessException {}

}

