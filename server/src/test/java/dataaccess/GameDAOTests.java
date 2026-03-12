package dataaccess;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameDAOTests {

    private static SQLGameDAO gameDAO;

    private static String white ="white";
    private static String black ="black";

    @BeforeAll
    static void setup() throws Exception{
        gameDAO = new SQLGameDAO();
    }

    @BeforeEach
    void clear() throws DataAccessException {
        gameDAO.clear();
    }

    @Test
    void insertGameSuccess() throws DataAccessException {
        GameData game = new GameData(0, null, null, "Test", new ChessGame());
        int gameID = gameDAO.insertGame(game);

        GameData result = gameDAO.getGame(gameID);
        assertEquals("Test", result.gameName());
    }

    @Test
    void insertGameFail() throws DataAccessException {
        try {
            gameDAO.insertGame(null);
            fail("Expected DAE to be thrown");
        } catch (DataAccessException e){
            assertNotNull(e.getMessage());
        }
    }

    @Test
    void getGameSuccess() throws DataAccessException {
        GameData game = new GameData(0, null, null, "Test", new ChessGame());
        int gameID = gameDAO.insertGame(game);

        GameData result = gameDAO.getGame(gameID);
        assertEquals("Test", result.gameName());
    }

    @Test
    void getGameFail() throws DataAccessException {
        try {
            GameData game = new GameData(0, null, null, "Test", new ChessGame());
            gameDAO.getGame(100000000);
            fail("Expected DAE to be thrown");
        } catch (DataAccessException e){
            assertNotNull(e.getMessage());
        }
    }

    @Test
    void updateGameSuccess() throws DataAccessException {
        GameData game = new GameData(0, null, null, "Test", new ChessGame());
        int gameID = gameDAO.insertGame(game);
        gameDAO.updateGame("WHITE", gameID, "testW");
        gameDAO.updateGame("BLACK", gameID, "testB");

        GameData updatedGame = gameDAO.getGame(gameID);

        assertEquals("testW", updatedGame.whiteUsername());
        assertEquals("testB", updatedGame.blackUsername());

    }

    @Test
    void updateGameFail() throws DataAccessException {
        try {
            GameData game = new GameData(0, null, null, "Test", new ChessGame());
            int gameID = gameDAO.insertGame(game);
            gameDAO.updateGame("GREEN", gameID, "testW");
            fail("Expected DAE to be thrown");
        } catch (DataAccessException e){
            assertNotNull(e.getMessage());
        }
    }


}
