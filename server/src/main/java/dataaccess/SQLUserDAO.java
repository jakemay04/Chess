package dataaccess;

import java.sql.SQLException;


public class SQLUserDAO

{
    public SQLUserDAO() throws DataAccessException, SQLException {
        try {
            DatabaseManager.createDatabase();
            DatabaseManager.createTable("user");
        }

    }


    public int clear() throws DataAccessException {
        String statement = "DROP TABLE IF EXISTS users";

        return SQLFunctions.executeUpdate(statement);

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
