package client;

import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port")
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerTestSuccess() {}

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
