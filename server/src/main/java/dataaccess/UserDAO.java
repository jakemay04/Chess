package dataaccess;

import model.UserData;

public interface UserDAO {
    void insertUser(UserData u) throws DataAccessException;
    UserData getUser(UserData u) throws DataAccessException;
    default void updateUser(UserData u) throws DataAccessException {}
    default void deleteUser(UserData u) throws DataAccessException {}
}

