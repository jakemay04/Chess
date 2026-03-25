package client;

import exception.ResponseException;
import org.junit.jupiter.api.*;
import records.*;
import server.Server;
import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;
    private final RegisterRequest testRegister =
            new RegisterRequest("user1", "password", "user1@email.com");
    private final LoginRequest testLogin =
            new LoginRequest("user1", "password");


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    void clearDatabase() throws Exception{
        facade.clear();
    }

    @Test
    public void registerTestSuccess() throws exception.ResponseException {
        var result = facade.register(testRegister);
        assertNotNull(result);
        assertNotNull(result.authToken());
    }

    @Test
    public void registerTestFail() throws exception.ResponseException {
        var result = facade.register(testUser);
        try {
            var resultFail = facade.register(testRegister);
            fail("Test should have failed here");
        } catch (ResponseException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void loginTestSuccess() throws ResponseException {
        var result = facade.register(testRegister);
        var login = facade.login()
        assertNotNull(result);
        assertNotNull(result.authToken());
    }

    @Test
    public void loginTestFail() {}

    @Test
    public void joinTestSuccess() {}

    @Test
    public void joinTestFail() {}

    @Test
    public void createTestSuccess() {}

    @Test
    public void createTestFail() {}

    @Test
    public void listTestSuccess() {}

    @Test
    public void listTestFail() {}

    @Test
    public void logoutTestSuccess() {}

    @Test
    public void logoutTestFail() {}


}
