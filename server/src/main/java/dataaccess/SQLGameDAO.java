package dataaccess;

import com.google.gson.Gson;
import model.GameData;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static dataaccess.SQLFunctions.executeUpdate;

public class SQLGameDAO implements GameDAO{
    private final Gson gson =  new Gson();

    public SQLGameDAO() {
        try {
            DatabaseManager.createDatabase();
            DatabaseManager.createTable("game");
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException("Failed to initialize DB:" + e.getMessage());
        }

    }

    public void clear() {
        try {
            String statement = "TRUNCATE TABLE game";
            executeUpdate(statement);

        } catch (DataAccessException ignored) {
        }
    }

    public int insertGame(GameData g) throws DataAccessException {
        if (g == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        else {
            String game = gson.toJson(g.game());
            String statement = "INSERT INTO game (whiteUsername, blackUsername, gameName, game) VALUE (?,?,?,?)";
            return executeUpdate(statement, g.whiteUsername(), g.blackUsername(), g.gameName(), game);
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


}
