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
    private final CreateGameRequest testCreate = new CreateGameRequest("TEST", "TESTGAME");


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
        var result = facade.register(testRegister);
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
        var login = facade.login(testLogin);
        assertNotNull(result);
        assertNotNull(result.authToken());
    }

    @Test
    public void loginTestFail() throws ResponseException {
        var result = facade.register(testRegister);
        try {
            var resultFail = facade.login(new LoginRequest("WRONG", "WRONG"));
            fail("Test should have failed here");
        } catch (ResponseException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void joinTestSuccess() throws ResponseException {
        var result = facade.register(testRegister);
        String authToken = result.authToken();
        int gameID = facade.createGame(testCreate, authToken).gameID();
        facade.joinGame(new JoinGameRequest(authToken, "WHITE", gameID), authToken);
        assertNotNull(result);
    }

    @Test
    public void joinTestFail() throws ResponseException {
        var result = facade.register(testRegister);
        String authToken = result.authToken();
        int gameID = facade.createGame(testCreate, authToken).gameID();
        try {
            facade.joinGame(new JoinGameRequest(authToken, "WHITE", -256), authToken);
            fail("Test should have failed here");
        } catch (ResponseException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void createTestSuccess() throws ResponseException {
        var result = facade.register(testRegister);
        String authToken = result.authToken();
        int gameID = facade.createGame(testCreate, authToken).gameID();
        facade.joinGame(new JoinGameRequest(authToken, "WHITE", gameID), authToken);
        assertNotNull(result);
        assertNotNull(result.authToken());
    }

    @Test
    public void createTestFail() throws ResponseException {
        var result = facade.register(testRegister);
        String authToken = result.authToken();
        try {
            int gameID = facade.createGame(testCreate, "WRONG").gameID();
            fail("Test should have failed here");
        } catch (ResponseException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void listTestSuccess() throws ResponseException {
        var result = facade.register(testRegister);
        String authToken = result.authToken();
        int gameID = facade.createGame(testCreate, authToken).gameID();
        var gameList = facade.listGames(new ListGamesRequest(authToken), authToken);
        assertNotNull(result);
        assertNotNull(result.authToken());
    }

    @Test
    public void listTestFail() throws ResponseException {
        var result = facade.register(testRegister);
        String authToken = result.authToken();
        facade.createGame(testCreate, authToken);
        try {
            var gameList = facade.listGames(new ListGamesRequest("WRONG"), "WRONG");
            fail("Test should have failed here");
        } catch (ResponseException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void logoutTestSuccess() throws ResponseException {
        var result = facade.register(testRegister);
        String authToken = result.authToken();
        facade.logout(new LogoutRequest(authToken), authToken);
    }

    @Test
    public void logoutTestFail() throws ResponseException {
        var result = facade.register(testRegister);
        String authToken = result.authToken();
        try {
            facade.logout(new LogoutRequest("WRONG"), "WRONG");
            fail("Test should have failed here");
        } catch (ResponseException e) {
            assertNotNull(e.getMessage());
        }
    }

    }
