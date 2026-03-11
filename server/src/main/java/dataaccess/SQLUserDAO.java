package dataaccess;

import java.sql.SQLException;


public class SQLUserDAO implements UserDAO {

    public SQLUserDAO() throws DataAccessException, SQLException {
        try {
            DatabaseManager.createDatabase();
            DatabaseManager.createTable("user");
        } catch(DataAccessException | SQLException e) {
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

//    public int insertUser(UserData u) throws DataAccessException {
//        if (u.username() == null) {
//            throw new DataAccessException("Error: bad request");
//        }
//
//        String statement = "INSERT INTO users (username, email, password) VALUES (?,?,?)";
//
//        return SQLFunctions.executeUpdate(statement, u.username(), u.email(), u.password());
//    }
//
//    public UserData getUser(String u) throws DataAccessException {
//        UserData user = users.get(u);
//        if (user == null) {
//            throw new DataAccessException("Error: unauthorized");
//        }
//        return user;
//    }

}
