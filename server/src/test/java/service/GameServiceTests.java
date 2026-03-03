package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTests {
    static UserDAO userDAO;
    static AuthDAO authDAO;
    static GameDAO gameDAO;
    static UserService userService;
    static GameService gameService;
    static String validToken;

    @BeforeEach
    void setup() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        gameService = new GameService(userDAO,authDAO,gameDAO);

        try {
            userDAO.insertUser(new UserData("testUser", "password", "test@test.com"));
            validToken = "valid-token-123";
            authDAO.insertAuth(new AuthData(validToken, "testUser"));
        } catch (Exception ignored) {}

    }

    @Test
    void createGameSuccess() throws DataAccessException {
        var createGame = gameService.createGame(new CreateGameRequest(validToken,"testgame"));
        assertTrue(createGame.gameID()>0);
    }

    @Test
    void createGameFail() throws DataAccessException {
        try {
            var createGame = gameService.createGame(new CreateGameRequest("badAuthToken","testgame"));
            fail("Game creating should have thrown error with bad auth");
        } catch (DataAccessException e) {
            assertEquals(e.getMessage(), "Error: unauthorized");
        }
    }

    @Test
    void gameListSuccess() throws DataAccessException {
        var createGame1 = gameService.createGame(new CreateGameRequest(validToken,"testgame"));
        var createGame2 = gameService.createGame(new CreateGameRequest(validToken,"testgame"));

        var gameList = gameService.listGames(new ListGamesRequest(validToken));
        assertEquals(2, gameList.games().size());
    }

    @Test
    void gameListFail() throws DataAccessException {
        var createGame = gameService.createGame(new CreateGameRequest(validToken,"testgame"));
        try {
            var gameList = gameService.listGames(new ListGamesRequest("badToken"));
            fail("Should throw auth error");
        } catch (DataAccessException e) {
            assertEquals("Error: unauthorized", e.getMessage());
        }
    }

    @Test
    void joinGameSuccess() throws DataAccessException {
        var createGame = gameService.createGame(new CreateGameRequest(validToken,"testgame"));
        try {
            gameService.joinGame(new JoinGameRequest(validToken, "WHITE", createGame.gameID()));
        } catch (DataAccessException e) {
            fail("Should not throw error");
        }
    }

    @Test
    void joinGameFailed() throws DataAccessException {
        var createGame = gameService.createGame(new CreateGameRequest(validToken,"testgame"));
        try {
            gameService.joinGame(new JoinGameRequest("badToken", "WHITE", createGame.gameID()));
            fail("Should throw auth error");
        } catch (DataAccessException e) {
            assertEquals("Error: unauthorized", e.getMessage());
        }
    }
}
