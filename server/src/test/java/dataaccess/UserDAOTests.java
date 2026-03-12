package dataaccess;

import model.UserData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTests {
    private static SQLUserDAO userDAO;
    private static UserData testData = new UserData("test", "pass", "email");
    private static UserData testData2 = new UserData("test2", "pass2", "email2");

    @BeforeAll
    static void setup() throws Exception {
        userDAO = new SQLUserDAO();
    }

    @BeforeEach
    void clear() throws DataAccessException {
        userDAO.clear();
    }

    @Test
    void insertUserSuccess() throws DataAccessException {
        userDAO.insertUser(testData);
        UserData result = userDAO.getUser(testData.username());
        assertEquals(testData.username(), result.username());
        assertEquals(testData.email(), result.email());

    }

    @Test
    void insertUserFail() throws DataAccessException {
        try {
            UserData wrongUser = new UserData(null, "WRONG", "WRONG");
            userDAO.insertUser(wrongUser);
            fail("Expected DAE to be thrown");
        } catch (DataAccessException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    void getUserSuccess() throws DataAccessException {
        userDAO.insertUser(testData);
        UserData result = userDAO.getUser(testData.username());
        assertNotNull(result);
        assertEquals(result.username(),testData.username());
        assertEquals(result.email(),testData.email());
    }



}
