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

}
