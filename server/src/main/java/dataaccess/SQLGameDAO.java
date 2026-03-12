package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import com.mysql.cj.xdevapi.PreparableStatement;
import model.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        String statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game WHERE gameID = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(statement)) {
             ps.setInt(1, gameID);

             try (ResultSet rs = ps.executeQuery()) {
                 if (rs.next()) {
                     return gameHelper(rs);

                 } else {
                     throw new DataAccessException("Error: bad request");
                 }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }

    public void updateGame(String playerColor, int gameID, String username) throws DataAccessException {
        getGame(gameID); //verify game exists

        String statement;

        if (playerColor.equals("WHITE")) {
            statement = "UPDATE game SET whiteUsername = ? WHERE gameID = ?";
        } else if (playerColor.equals("black")) {
            statement = "UPDATE game SET blackUsername = ? WHERE gameID = ?";
        } else {
            throw new DataAccessException("Error: bad request");
        }

        executeUpdate(statement, username, gameID);
        //create statement based on which player joins;
    }

    public Collection<GameData> gameList() throws DataAccessException {
        String statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game";
        Collection<GameData> games = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(statement);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                games.add(gameHelper(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
        return games;
    }

    //helper function that takes sql data and turns into GameData POJO
    //Also converts json back to chess game object
    private GameData gameHelper(ResultSet rs) throws SQLException {
        int gameID = rs.getInt("gameID");
        String whiteUsername = rs.getString("whiteUsername");
        String blackUsername = rs.getString("blackUsername");
        String gameName = rs.getString("gameName");
        ChessGame game = gson.fromJson(rs.getString("game"), ChessGame.class);
        return new GameData(gameID, whiteUsername, blackUsername, gameName, game);

    }


}
