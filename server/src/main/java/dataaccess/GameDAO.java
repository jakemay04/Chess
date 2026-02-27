package dataaccess;

import model.GameData;
import model.UserData;

import java.util.Collection;

public interface GameDAO {
    int insertGame(GameData g) throws DataAccessException;
    void getGame(UserData u, GameData g) throws DataAccessException;
    void updateGame(UserData u, GameData g) throws DataAccessException;
    void deleteGame(UserData u, GameData g) throws DataAccessException;
    Collection<GameData> gameList() throws DataAccessException;
    void clear();
}
