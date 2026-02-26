package dataaccess;

import model.UserData;

public interface AuthDAO {
    default void createAuth(UserData u) throws DataAccessException {}
    default void getAuth(UserData u) throws DataAccessException {}
    default void updateAuth(UserData u) throws DataAccessException {}
    default void deleteAuth(UserData u) throws DataAccessException {}
    default void gameList(UserData u) throws DataAccessException {}
}

