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
            throw new DataAccessException("Invalid");
        }
    }

    public void getGame(int gameID) throws DataAccessException {
        GameData game = games.get(gameID);
        if (game == null) {
            throw new DataAccessException("game does not exist");
        }
    }

    public void updateGame(String playerColor, int gameID, String username) throws DataAccessException {
        if (playerColor != null) {
            GameData currentGame = games.get(gameID);
            if (playerColor.equals("WHITE")) {
                if (username != null) {
                    GameData newGame = new GameData(
                            gameID,
                            username,
                            currentGame.blackUsername(),
                            currentGame.gameName(),
                            currentGame.game()
                    );
                    games.put(gameID, newGame);
                }
                else {
                    throw new DataAccessException("Invalid username");
                }
            }
            else if (playerColor.equals("BLACK")){
                if (username != null) {
                    GameData newGame = new GameData(
                            gameID,
                            currentGame.whiteUsername(),
                            username,
                            currentGame.gameName(),
                            currentGame.game()
                    );
                    games.put(gameID, newGame);
                } else {
                    throw new DataAccessException("Invalid username");
                }
            }
            else {
                throw new DataAccessException("Invalid team color");
            }
        }
    }

    public Collection<GameData> gameList() throws DataAccessException {
        return games.values();
    }

    public void clear() {
        games.clear();
    }
}

