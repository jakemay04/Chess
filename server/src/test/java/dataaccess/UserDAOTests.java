package dataaccess;

import model.UserData;
import org.junit.jupiter.api.*;

public class UserDAOTests {
    private static SQLUserDAO userDAO;
    private static UserData testData = new UserData("test", "pass", "email");
    private static UserData testData2 = new UserData("test2", "pass2", "email2");

    @BeforeAll
    static void setUp() throws Exception {
        userDAO = new SQLUserDAO();
    }

    @BeforeEach
    void clear() throws DataAccessException {
        userDAO.clear();
    }





}
