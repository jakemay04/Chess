package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;

public class ClearServiceTests {
    static UserDAO userDAO;
    static AuthDAO authDAO;
    static GameDAO gameDAO;
    static UserService userService;
    static GameService gameService;
    static String validToken;

    void setUp() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        gameService = new GameService(userDAO,authDAO,gameDAO);
        try {
            userDAO.insertUser(new UserData("testUser2", "password", "test@test.com"));
            gameDAO.insertGame(new GameData(0, null, null, "testGame", new ChessGame()));
            authDAO.insertAuth(new AuthData("some-token", "testUser2"));
        } catch (Exception ignore) {}
    }

    @Test
    void clearSuccess() {

    }



}
