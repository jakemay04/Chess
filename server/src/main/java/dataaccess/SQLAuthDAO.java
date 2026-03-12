package dataaccess;

import model.AuthData;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SQLAuthDAO {
    public SQLAuthDAO() {
        try {
            DatabaseManager.createDatabase();
            DatabaseManager.createTable("auth");
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException("Failed to initialize DB:" + e.getMessage());
        }

    }

    public void clear() {
        try {
            String statement = "DROP TABLE IF EXISTS auth";
            SQLFunctions.executeUpdate(statement);

        } catch (DataAccessException ignored) {
        }
    }

    public void insertAuth(AuthData a) throws DataAccessException {
        if (a.username() == null) {
            throw new DataAccessException("Error: unauthorized");

        }
        else {
            String statement = "INSERT INTO auth (authToken, username) VALUES (?,?)";

            SQLFunctions.executeUpdate(statement, a.authToken(), a.username());
        }
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        if (authToken == null) {
            throw new DataAccessException("Error: bad request");
        }
        else if (!auth.containsKey(authToken)){
            throw new DataAccessException("Error: unauthorized");
        }
        return auth.get(authToken);
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        if (authToken == null) {
            throw new DataAccessException("Error: bad request");
        }
        else if (!auth.containsKey(authToken)){
            throw new DataAccessException("Error: unauthorized");
        }
        auth.remove(authToken);

    }

}
