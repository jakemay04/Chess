package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class AuthDAOTests {
    private static SQLAuthDAO authDAO;
    private static AuthData testData = new AuthData("authToken", "test");

    @BeforeAll
    static void setup() throws Exception {
        authDAO = new SQLAuthDAO();
    }

    @BeforeEach
    void clear() throws DataAccessException {
        authDAO.clear();
    }

    @Test
    void insertAuthSuccess() throws DataAccessException {
        authDAO.insertAuth(testData);
        AuthData result = authDAO.getAuth(testData.authToken());
        assertEquals(testData.username(), result.username());
        assertEquals(testData.authToken(), result.authToken());

    }

    @Test
    void insertAuthFail() throws DataAccessException {
        try {
            AuthData wrongUser = new AuthData(null, "WRONG");
            authDAO.insertAuth(wrongUser);
            fail("Expected DAE to be thrown");
        } catch (DataAccessException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    void getAuthSuccess() throws DataAccessException {
        authDAO.insertAuth(testData);
        AuthData result = authDAO.getAuth(testData.authToken());
        assertEquals(testData.username(), result.username());
        assertEquals(testData.authToken(), result.authToken());

    }

    @Test
    void getAuthFail() throws DataAccessException {
        try {
            authDAO.insertAuth(testData);
            authDAO.getAuth("WRONG TOKEN");
            fail("Expected DAE to be thrown");
        } catch (DataAccessException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    void clearSuccess() throws DataAccessException {
        try {
            authDAO.insertAuth(testData);
            authDAO.clear();
            authDAO.getAuth(testData.authToken());
            fail("Expected DAE to be thrown");
        } catch (DataAccessException e) {
            assertNotNull(e.getMessage());
        }
    }


}
