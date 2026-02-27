package dataaccess;

import model.GameData;
import model.UserData;

public interface GameDAO {
    int insertGame(GameData g) throws DataAccessException;
    void getGame(UserData u, GameData g) throws DataAccessException;
    void updateGame(UserData u, GameData g) throws DataAccessException;
    void deleteGame(UserData u, GameData g) throws DataAccessException;
    void gameList(UserData u, GameData g) throws DataAccessException;
    void clear();
}
