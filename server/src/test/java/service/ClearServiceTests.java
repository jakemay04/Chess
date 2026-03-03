package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTests {
    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;
    ClearService clearService;

    @BeforeEach
    void setUp() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        clearService = new ClearService(userDAO, gameDAO, authDAO);
        try {
            userDAO.insertUser(new UserData("testUser2", "password", "test@test.com"));
            gameDAO.insertGame(new GameData(0, null, null, "testGame", new ChessGame()));
            authDAO.insertAuth(new AuthData("some-token", "testUser2"));
        } catch (Exception ignore) {}
    }

    @Test
    void clearSuccess() {
        try {
            clearService.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        //make sure user does not exist
        try {
            userDAO.getUser("testUser2");
        } catch (DataAccessException e) {
            assertEquals("Error: unauthorized", e.getMessage());
        }

        try {
            authDAO.getAuth("randomToken");
        } catch (DataAccessException e) {
            assertEquals("Error: unauthorized", e.getMessage());
        }

        try {
            assertTrue(gameDAO.gameList().isEmpty());
        } catch (DataAccessException e) {
            fail("Should not throw error");
        }
    }



}
