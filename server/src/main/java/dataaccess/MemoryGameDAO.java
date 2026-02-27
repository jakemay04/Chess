package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.*;

public class MemoryGameDAO implements GameDAO {
    private Map<Integer, GameData> game = new HashMap<Integer, GameData>();
    private int id = 0;

    public int insertGame(GameData g) throws DataAccessException {
        if (g != null) {
            id++;
            game.put(id, g);
            return id;
        }
        else {
            throw new DataAccessException("Invalid");
        }
    }

    public void getGame(UserData u, GameData g) throws DataAccessException {

    }

    public void updateGame(UserData u, GameData g) throws DataAccessException {

    }

    public void deleteGame(UserData u, GameData g) throws DataAccessException {

    }

    public Collection<GameData> gameList() throws DataAccessException {
        return game.values();
    }

    public void clear() {
        game = null;
    }
}

