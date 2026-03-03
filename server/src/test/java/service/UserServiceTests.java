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

    @Test
    void loginWrongPass() throws DataAccessException {
        var result = userService.register(new RegisterRequest("test","pass","test@test.com"));
        try {
            var loginResult = userService.login(new LoginRequest("test", "wrong"));
            fail("Login should have throw error");
        } catch (DataAccessException e) {
            assertEquals("Error: unauthorized", e.getMessage());
        }
    }

    @Test
    void logoutSuccess() throws DataAccessException {
        var result = userService.register(new RegisterRequest("test","pass","test@test.com"));
        userService.logout(new LogoutRequest(result.authToken()));
        try {
            authDAO.getAuth(result.authToken());
            fail("Should not be able to get authToken after logout");
        } catch (DataAccessException e) {
            assertEquals("Error: unauthorized", e.getMessage());
        }
    }

    @Test
    void logoutBadAuth() throws DataAccessException {
        var result = userService.register(new RegisterRequest("test","pass","test@test.com"));
        try {
            userService.logout(new LogoutRequest("WRONG_AUTH_TOKEN"));
            fail("Should not be able to logout with bad authToken");
        } catch (DataAccessException e) {
            assertEquals("Error: unauthorized", e.getMessage());
        }
    }

}
