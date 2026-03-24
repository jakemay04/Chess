package ui;

import client.ServerFacade;
import records.*;

public class PostLogin {

    private final ServerFacade facade;
    private final String authToken;

    public PostLogin(ServerFacade facade, String authToken) {
        this.facade = facade;
        this.authToken = authToken;
    }

    public String eval(String line) {
        if (line.equals("Help") || line.equals("help")) {
            System.out.println("Here is how to navigate the Chess Game: ");
            System.out.println("To Logout, simply type Logout");
            System.out.println("To start a new game, type Create");
            System.out.println("If you would like to see all games in session, type List");
            System.out.println("If you would like to Join a Game from the list of games,");
            System.out.println("Type Join. You will then be prompted with the game number and the color you wish to be.");
            System.out.println("If you would like to Observe a game from the list of games, type Observe");
            System.out.println("You will then be prompted for the game number");
            System.out.println("If you would like to quit the program, simply type Quit.");


        } else if (line.equalsIgnoreCase("Logout")) {
            //call logout from server facade
            try {
                var result = facade.logout(new LogoutRequest(authToken), authToken);
                return "Logged out!";
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
            return line;
        } else if (line.equalsIgnoreCase("Create")) {
            //call Create Game from server facade
            String gameName;
            try {
                var result = facade.createGame(new CreateGameRequest(authToken, gameName), authToken);
                return "Logged out!";
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        } else if (line.equalsIgnoreCase("List")) {
            //call List Game from server facade
        } else if (line.equalsIgnoreCase("Join")) {
            //call Join Game from server facade
        } else if (line.equalsIgnoreCase("Quit")) {
            return "Quit";
        } else {
            System.out.println("Unknown command. Type 'Help' for options.");
        }
        return line;
    }

}
