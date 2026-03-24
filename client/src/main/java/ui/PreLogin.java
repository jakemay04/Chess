package ui;

public class PreLogin {

    private final ServerFacade facade;

    public PreLogin(ServerFacade facade) {
        this.facade = facade;
    }

    static String eval(String line) {
        if (line.equals("Help") || line.equals("help")) {
            System.out.println("Here is how to navigate the Chess Game: ");
            System.out.println("If you already have an account, simply type Login. " +
                    "Then, you will be prompted for your username and password");
            System.out.println("If you would like to register, type Register");
            System.out.println("You will then be prompted for your Username, Email and Password");
            System.out.println("If you would like to quit the program, simply type Quit.");

        } else if (line.equals("Register") || line.equals("register")) {
            //call register from server facade
        } else if (line.equals("Login") || line.equals("login")) {
            //call login from server facade
        } else if (line.equals("Quit") || line.equals("quit")) {
            return "Quit";
        } else {
            System.out.println("Unknown command. Type 'Help' for options.");
        }
        return line;
    }

}
