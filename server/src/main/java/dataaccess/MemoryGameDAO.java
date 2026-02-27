package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {
    private Map<Integer, GameData> game = new HashMap<Integer, GameData>();
    private int id = 0;

    public int insertGame(UserData u, GameData g) throws DataAccessException {
        if (u != null && g != null) {
            id++;
            game.put(id, g);
            return id;
        }
        else {
            throw new DataAccessException("Invalid");
        }
    }

    public void clear() {
        game = null;
    }
}

