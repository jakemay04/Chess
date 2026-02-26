package dataaccess;

import model.UserData;

public interface UserDAO {
    default void insertUser(UserData u) throws DataAccessException {}
    default void getUser(UserData u) throws DataAccessException {}
    default void updateUser(UserData u) throws DataAccessException {}
    default void deleteUser(UserData u) throws DataAccessException {}
}

