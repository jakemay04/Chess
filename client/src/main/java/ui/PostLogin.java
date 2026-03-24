package ui;

import client.ServerFacade;
import records.*;

import java.util.Scanner;

public class PostLogin {

    private final ServerFacade facade;
    private final String authToken;
    private final Scanner scanner = new Scanner(System.in);

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

        } else if (line.equalsIgnoreCase("Create")) {
            //call Create Game from server facade
            System.out.print("Enter game name: ");
            String gameName = scanner.nextLine();
            try {
                var result = facade.createGame(new CreateGameRequest(authToken, gameName), authToken);
                return "Game Created!";
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }

        } else if (line.equalsIgnoreCase("List")) {
            //call List Game from server facade
            try {
                var result = facade.listGames(new ListGamesRequest(authToken), authToken);
                return result;
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }

        } else if (line.equalsIgnoreCase("Join")) {
            //call Join Game from server facade
            System.out.print("Game number: ");
            int gameNumber = Integer.parseInt(scanner.nextLine());
            System.out.print("Color (WHITE/BLACK): ");
            String color = scanner.nextLine().toUpperCase();
            try {
                facade.joinGame(new JoinGameRequest(authToken, playerColor, gameID), authToken);
                return "Game Joined!";
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }

        } else if (line.equalsIgnoreCase("Quit")) {
            return "Quit";

        } else {
            System.out.println("Unknown command. Type 'Help' for options.");

        }
        return line;
    }

}
