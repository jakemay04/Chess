package dataaccess;

import model.AuthData;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static dataaccess.SQLFunctions.executeUpdate;

public class SQLAuthDAO implements AuthDAO{
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
            String statement = "TRUNCATE TABLE auth";
            executeUpdate(statement);

        } catch (DataAccessException ignored) {
        }
    }

    public void insertAuth(AuthData a) throws DataAccessException {
        if (a.username() == null) {
            throw new DataAccessException("Error: unauthorized");

        }
        else {
            String statement = "INSERT INTO auth (authToken, username) VALUES (?,?)";

            executeUpdate(statement, a.authToken(), a.username());
        }
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        String statement = "SELECT authToken, username FROM auth WHERE authToken = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(statement)) {
            ps.setString(1, authToken);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new AuthData(
                            rs.getString("authToken"),
                            rs.getString("username")
                    );
                } else {
                    throw new DataAccessException("Error: unauthorized");
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        String statement = "DELETE FROM auth WHERE authToken = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(statement)) {
            ps.setString(1, authToken);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }
}
