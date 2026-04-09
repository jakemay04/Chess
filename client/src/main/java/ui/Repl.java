package ui;

import client.ServerFacade;

import java.util.Scanner;

public class Repl {

    private static final String url = "http://localhost:8080";
    private final ServerFacade facade = new ServerFacade("http://localhost:8080");
    private final PreLogin preLogin = new PreLogin(facade);
    private PostLogin postLogin = null;
    private final Scanner scanner = new Scanner(System.in);
    private boolean loggedIn = false;
    private String authToken = null;

    public void run() {
        System.out.print("Welcome to the Chess Game! Type Help, Login, Register or Quit to continue");

        var result = "";

        while (!result.equalsIgnoreCase("Quit")) {
            System.out.print(">>>");

            String line = scanner.nextLine();

            try {
                if (loggedIn) {
                    result = postLogin.eval(line);
                    if (result.equalsIgnoreCase("loggedout")) {
                        loggedIn = false;
                        postLogin = null;
                        authToken = null;
                        result = "Logged out! Type Help, Login, Register or Quit to continue";
                    }
                } else {
                    result = preLogin.eval(line);
                    if (result.startsWith("loggedin:")) {
                        authToken = result.split(":")[1];
                        loggedIn = true;
                        postLogin = new PostLogin(facade, authToken, url);
                        result = "You are now Logged in! Type Help, List, Join, Create, Observe or Quit to continue";
                    }
                }
                System.out.println(result);
            } catch (Throwable e) {
                System.out.print("Oops!" + e.getMessage());
            }
        }
        System.out.println("Quitting...");
    }


}
