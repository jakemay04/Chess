package service;

import dataaccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTests {
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
    void registerSuccess() throws DataAccessException {
        var result = userService.register(new RegisterRequest("test","pass","test@test.com"));
        assertEquals("test", result.username());
    }

    @Test
    void registerFailed() throws DataAccessException {
        var result = userService.register(new RegisterRequest("test","pass","test@test.com"));
        var duplicate = new RegisterRequest("test", "wrongpass", "wrong@test.com");
        try {
            userService.register(duplicate);
            fail("Expected exception to throw");
        } catch (DataAccessException e) {
            assertEquals("Error: already taken", e.getMessage());
        }
    }

    @Test
    void loginSuccess() throws DataAccessException {
        var result = userService.register(new RegisterRequest("test","pass","test@test.com"));
        var loginResult = userService.login(new LoginRequest("test", "pass"));
        assertNotNull(loginResult.authToken());
    }

    void loginWrongPass() throws DataAccessException {
        var result = userService.register(new RegisterRequest("test","pass","test@test.com"));
    }
}
