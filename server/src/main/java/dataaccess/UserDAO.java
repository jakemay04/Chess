package dataaccess;

import model.UserData;

public interface UserDAO {
    void insertUser(UserData u) throws DataAccessException;
    UserData getUser(String u) throws DataAccessException;
    void clear() throws DataAccessException;
}

