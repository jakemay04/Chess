package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.*;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class MemoryGameDAO implements GameDAO {
    private Map<Integer, GameData> game = new HashMap<Integer, GameData>();
    private int id = 1;

    public int insertGame(GameData g) throws DataAccessException {
        if (g != null) {
            int nid = id++;
            GameData withID = new GameData(nid, g.whiteUsername(), g.blackUsername(), g.gameName(), g.game());
            game.put(nid, withID);
            return nid;
        }
        else {
            throw new DataAccessException("Invalid");
        }
    }

    public void getGame(UserData u, GameData g) throws DataAccessException {

    }

    public void updateGame(String playerColor, int gameID, String username) throws DataAccessException {
        if (playerColor != null) {
            GameData currentGame = game.get(gameID);
            if (playerColor.equals("WHITE")) {
                if (username != null) {
                    GameData newGame = new GameData(
                            gameID,
                            username,
                            currentGame.blackUsername(),
                            currentGame.gameName(),
                            currentGame.game()
                    );
                    game.put(gameID, newGame);
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
                    game.put(gameID, newGame);
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
        return game.values();
    }

    public void clear() {
        game = null;
    }
}

