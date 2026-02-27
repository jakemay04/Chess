package dataaccess;

import model.GameData;
import model.UserData;

public interface GameDAO {
    default int insertGame(UserData u, GameData g) throws DataAccessException {}
    default void getGame(UserData u, GameData g) throws DataAccessException {}
    default void updateGame(UserData u, GameData g) throws DataAccessException {}
    default void deleteGame(UserData u, GameData g) throws DataAccessException {}
    default void gameList(UserData u, GameData g) throws DataAccessException {}
    void clear();
}
