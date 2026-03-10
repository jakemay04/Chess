package dataaccess;

import model.UserData;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class SQLUserDAO

{
    public SQLUserDAO() throws DataAccessException {
        DatabaseManager.createDatabase();
        DatabaseManager.createTable("user");

    }


    public void clear() {
        users.clear();
    }

    public void insertUser(UserData u) throws DataAccessException {
        if (u.username() == null) {
            throw new DataAccessException("Error: bad request");
        }
        if (users.containsKey(u.username())){
            throw new DataAccessException(("Error: already taken"));
        }

        users.put(u.username(), u);
    }

    public UserData getUser(String u) throws DataAccessException {
        UserData user = users.get(u);
        if (user == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        return user;
    }
}
