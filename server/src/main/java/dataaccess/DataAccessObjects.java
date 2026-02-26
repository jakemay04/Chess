package dataaccess;

import model.UserData;

public class DataAccessObjects {

    void public interface UserDAO {
        default void insertUser(UserData u) throws DataAccessException {}
        default void getUser(UserData u) throws DataAccessException {}
        default void updateUser(UserData u) throws DataAccessException {}
        default void deleteUser(UserData u) throws DataAccessException {}


    }

    void public interface GameDAO {
    }

    void public interface AuthDAO {
    }




}
