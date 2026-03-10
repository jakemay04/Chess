package dataaccess;

import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static java.sql.Statement.RETURN_GENERATED_KEYS;



public class SQLUserDAO

{
    public SQLUserDAO() throws DataAccessException, SQLException {
        DatabaseManager.createDatabase();
        DatabaseManager.createTable("user");

    }


    public void clear() throws DataAccessException {
        String statement = "DROP TABLE IF EXISTS users";

        SQLFunctions.executeUpdate(statement);

    }

    public int insertUser(UserData u) throws DataAccessException {
        if (u.username() == null) {
            throw new DataAccessException("Error: bad request");
        }

        String statement = "INSERT INTO users (username, email, password) VALUES (?,?,?)";

        return SQLFunctions.executeUpdate(statement, u.username(), u.email(), u.password());
    }

    public UserData getUser(String u) throws DataAccessException {
        UserData user = users.get(u);
        if (user == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        return user;
    }

}
