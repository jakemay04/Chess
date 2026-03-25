package client;

import org.junit.jupiter.api.*;
import records.RegisterRequest;
import server.Server;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;
    private final RegisterRequest testUser = new RegisterRequest("user1", "password", "user1@email.com");

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
    public void registerTestSuccess() {
        var result = facade.register(new testuser)
    }

    @Test
    public void registerTestFail() {}

    @Test
    public void loginTestSuccess() {}

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
