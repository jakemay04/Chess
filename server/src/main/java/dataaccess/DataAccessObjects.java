package dataaccess;

import model.GameData;
import model.UserData;

public class DataAccessObjects {

    void public interface UserDAO {
        default void insertUser(UserData u) throws DataAccessException {}
        default void getUser(UserData u) throws DataAccessException {}
        default void updateUser(UserData u) throws DataAccessException {}
        default void deleteUser(UserData u) throws DataAccessException {}
    }

    void public interface GameDAO {
        default void createGame(UserData u, GameData g) throws DataAccessException {}
        default void getGame(UserData u, GameData g) throws DataAccessException {}
        default void updateGame(UserData u, GameData g) throws DataAccessException {}
        default void deleteGame(UserData u, GameData g) throws DataAccessException {}
        default void gameList(UserData u, GameData g) throws DataAccessException {}
    }

    void public interface AuthDAO {
        default void createAuth(UserData u) throws DataAccessException {}
        default void getAuth(UserData u) throws DataAccessException {}
        default void updateAuth(UserData u) throws DataAccessException {}
        default void deleteAuth(UserData u) throws DataAccessException {}
        default void gameList(UserData u) throws DataAccessException {}
    }




}
