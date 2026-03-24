package ui;
import java.util.Scanner;
import client.ServerFacade;
import exception.ResponseException;
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
            System.out.println("Here is how to navigate the Chess Game: ");
            System.out.println("If you already have an account, simply type Login. " +
                    "Then, you will be prompted for your username and password");
            System.out.println("If you would like to register, type Register");
            System.out.println("You will then be prompted for your Username, Email and Password");
            System.out.println("If you would like to quit the program, simply type Quit.");

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
                return "Registered as" + username;
            } catch (Exception e) {
                return "Something went wrong...";
            }

        } else if (line.equals("login")) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            //call login from server facade

            try {
                var result = facade.login(new RegisterRequest(username, password, email));
                return "Logged in as" + username;
            } catch (Exception e) {
                return "Something went wrong...";
            }

        } else if (line.equals("quit")) {
            return "Quit";
        } else {
            System.out.println("Unknown command. Type 'Help' for options.");
        }
        return line;
    }

}
