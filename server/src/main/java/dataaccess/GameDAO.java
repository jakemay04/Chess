package dataaccess;

import model.GameData;
import model.UserData;

import java.util.Collection;

public interface GameDAO {
    int insertGame(GameData g) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    void updateGame(String playerColor, int gameID, String username) throws DataAccessException;
    Collection<GameData> gameList() throws DataAccessException;
    void clear() throws DataAccessException;;
}
