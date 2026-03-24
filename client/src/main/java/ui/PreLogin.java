package ui;
import java.util.Scanner;
import client.ServerFacade;
import exception.ResponseException;
import records.LoginRequest;
import records.RegisterRequest;

public class PreLogin {

    private final ServerFacade facade;
    private final Scanner scanner = new Scanner(System.in);

    public PreLogin(ServerFacade facade) {
        this.facade = facade;
    }

    public String eval(String line) {
        line = line.trim().toLowerCase();
        if (line.equals("help")) {
            return """
            Available commands:
              register - create a new account
              login    - login to existing account
              quit     - exit the program
              help     - show this message
            """;

        } else if (line.equals("register")) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            //call register from server facade

            try {
                var result = facade.register(new RegisterRequest(username, password, email));
                return "loggedin:" + result.authToken();
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }

        } else if (line.equals("login")) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            //call login from server facade

            try {
                var result = facade.login(new LoginRequest(username, password));
                return "loggedin:" + result.authToken();
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }

        } else if (line.equals("quit")) {
            return "Quit";
        } else {
            System.out.println("Unknown command. Type 'Help' for options.");
        }
        return line;
    }

}
