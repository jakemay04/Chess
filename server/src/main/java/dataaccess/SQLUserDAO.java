package dataaccess;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SQLUserDAO implements UserDAO {

    public SQLUserDAO() {
        try {
            DatabaseManager.createDatabase();
            DatabaseManager.createTable("users");
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException("Error: failed to initialize DB:" + e.getMessage());
        }

    }

    public void clear() throws DataAccessException {
        try {
            String statement = "TRUNCATE TABLE users";
            SQLFunctions.executeUpdate(statement);

        } catch (DataAccessException e) {
            throw new DataAccessException("Error: failed to clear game table: " + e.getMessage(), e);
        }
    }

    public void insertUser(UserData u) throws DataAccessException {
        if (u.username() == null) {
            throw new DataAccessException("Error: bad request");
        }
        String hashedPassword = BCrypt.hashpw(u.password(), BCrypt.gensalt());
        String statement = "INSERT INTO users (username, password, email) VALUES (?,?,?)";

        SQLFunctions.executeUpdate(statement, u.username(),hashedPassword, u.email());
    }

    public UserData getUser(String u) throws DataAccessException {
        String statement = "SELECT username, password, email FROM users WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(statement)) {
            ps.setString(1, u);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserData(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                } else {
                    throw new DataAccessException("Error: unauthorized");
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }

    }

}
