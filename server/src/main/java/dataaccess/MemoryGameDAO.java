package dataaccess;

import model.GameData;

import java.util.*;

public class MemoryGameDAO implements GameDAO {
    private Map<Integer, GameData> games = new HashMap<>();
    private int id = 1;

    public int insertGame(GameData g) throws DataAccessException {
        if (g != null) {
            int nid = id++;
            GameData withID = new GameData(nid, g.whiteUsername(), g.blackUsername(), g.gameName(), g.game());
            games.put(nid, withID);
            return nid;
        }
        else {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public GameData getGame(int gameID) throws DataAccessException {
        GameData game = games.get(gameID);
        if (game == null) {
            throw new DataAccessException("Error: bad request");
        }
        return game;
    }

    public void updateGame(String playerColor, int gameID, String username) throws DataAccessException {
        GameData currentGame = games.get(gameID);
        if (currentGame == null) {
            throw new DataAccessException("Error: bad request");
        }
        GameData newGame;
        if (playerColor.equals("WHITE")) {
            newGame = new GameData(gameID, username, currentGame.blackUsername(), currentGame.gameName(), currentGame.game());
        }
        else if (playerColor.equals("BLACK")){
            newGame = new GameData(gameID, currentGame.whiteUsername(), username, currentGame.gameName(), currentGame.game());
        }
        else {
            throw new DataAccessException("Error: bad request");
        }
        games.put(gameID, newGame);
    }

    public Collection<GameData> gameList() throws DataAccessException {
        return games.values();
    }

    public void clear() {
        games.clear();
    }
}

