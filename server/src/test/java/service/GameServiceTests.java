package service;

import dataaccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameServiceTests {
    static UserDAO userDAO;
    static AuthDAO authDAO;
    static GameDAO gameDAO;
    static UserService userService;
    static GameService gameService;

    @BeforeEach
    void Setup() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        gameService = new GameService(userDAO,authDAO,gameDAO);
    }
    @Test
    void createGameSuccess() throws DataAccessException {
        var result = userService.register(new RegisterRequest("test","pass","test@test.com"));
        var createGame = gameService.createGame(CreateGameRequest())
        assertEquals("test", result.username());
    }
}
