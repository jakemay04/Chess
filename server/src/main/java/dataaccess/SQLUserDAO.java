package dataaccess;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SQLUserDAO implements UserDAO {

    public SQLUserDAO() throws DataAccessException {
        try {
            DatabaseManager.createDatabase();
            DatabaseManager.createTable("user");
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException("Error: bad request");
        }

    }

    public void clear() {
        try {
            String statement = "DROP TABLE IF EXISTS users";
            SQLFunctions.executeUpdate(statement);

        } catch (DataAccessException ignored) {
        }
    }

    public void insertUser(UserData u) throws DataAccessException {
        if (u.username() == null) {
            throw new DataAccessException("Error: bad request");
        }

        String statement = "INSERT INTO users (username, email, password) VALUES (?,?,?)";

        SQLFunctions.executeUpdate(statement, u.username(), u.email(), u.password());
    }

    public UserData getUser(String u) throws DataAccessException {
        String statement = "SELECT username, email, password FROM users WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(statement)) {
            ps.setString(1, u);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserData(
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                } else {
                    throw new DataAccessException("Error: unauthorized");
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

    }

}
