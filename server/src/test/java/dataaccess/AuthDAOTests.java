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

//    @Test
//    void insertUserFail() throws DataAccessException {
//        try {
//            UserData wrongUser = new UserData(null, "WRONG", "WRONG");
//            userDAO.insertUser(wrongUser);
//            fail("Expected DAE to be thrown");
//        } catch (DataAccessException e) {
//            assertNotNull(e.getMessage());
//        }
//    }

//    @Test
//    void getUserSuccess() throws DataAccessException {
//        userDAO.insertUser(testData);
//        UserData result = userDAO.getUser(testData.username());
//        assertNotNull(result);
//        assertEquals(result.username(),testData.username());
//        assertEquals(result.email(),testData.email());
//    }
//
//    @Test
//    void getUserFail() throws DataAccessException {
//        try {
//            userDAO.insertUser(testData);
//            userDAO.getUser("WRONG");
//            fail("Expected DAE to be thrown");
//        } catch (DataAccessException e) {
//            assertNotNull(e.getMessage());
//        }
//    }
//
//    @Test
//    void clearUserSuccess() throws DataAccessException {
//        userDAO.insertUser(testData);
//        userDAO.clear();
//
//        try {
//            userDAO.getUser(testData.username());
//            fail("Expected DAE to be thrown");
//        } catch (DataAccessException e) {
//            assertNotNull(e.getMessage());
//        }
//    }
}
