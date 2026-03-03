package service;

import dataaccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameServiceTests {
    static UserDAO userDAO;
    static AuthDAO authDAO;
    static UserService userService;

    @BeforeEach
    void Setup() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        userService = new UserService(userDAO, authDAO);
    }
    @Test
    void createGameSuccess() throws DataAccessException {
        var result = userService.register(new RegisterRequest("test","pass","test@test.com"));
        assertEquals("test", result.username());
    }
}
