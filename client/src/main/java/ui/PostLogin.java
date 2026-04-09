package ui;

import chess.ChessBoard;
import chess.ChessGame;
import client.ServerFacade;
import model.GameData;
import records.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class PostLogin {

    private final ServerFacade facade;
    private final String authToken;
    private final Scanner scanner = new Scanner(System.in);
    private List<GameData> gamesList = new ArrayList<>();
    private final String url;

    public PostLogin(ServerFacade facade, String authToken, String url) {
        this.facade = facade;
        this.authToken = authToken;
        this.url = url;
    }

    public String eval(String line) {

        if (line.equals("Help") || line.equals("help")) {
            help();
        } else if (line.equalsIgnoreCase("Logout")) {
            //call logout from server facade
            try {
                facade.logout(new LogoutRequest(authToken), authToken);
                return "loggedout";
            } catch (Exception e) {
                return e.getMessage();
            }

        } else if (line.equalsIgnoreCase("Create")) {
            //call Create Game from server facade
            System.out.print("Enter game name: ");
            String gameName = scanner.nextLine();
            try {
                var result = facade.createGame(new CreateGameRequest(authToken, gameName), authToken);
                return "Game Created!";
            } catch (Exception e) {
                return e.getMessage();
            }

        } else if (line.equalsIgnoreCase("List")) {
            try {
                var result = facade.listGames(new ListGamesRequest(authToken),authToken);
                gamesList = new ArrayList<>(result.games());
                if (gamesList.isEmpty()) {
                    return "No games available.";
                }
                var sb = new StringBuilder();
                for (int i = 0; i < gamesList.size(); i++) {
                    String gameName = gamesList.get(i).gameName();
                    String whitePlayer = gamesList.get(i).whiteUsername();
                    String blackPlayer = gamesList.get(i).blackUsername();
                    if (whitePlayer == null) {
                        whitePlayer = "open";
                    }
                    if (blackPlayer == null) {
                        blackPlayer = "open";
                    }

                    sb.append(i + 1).append(". ").append(gameName).append(" | White: ").
                            append(whitePlayer).append(" | Black: ").append(blackPlayer).append("\n");

                }
                return sb.toString();
            } catch (Exception e) {
                return e.getMessage();
            }

        } else if (line.equalsIgnoreCase("Join")) {
            return join(gamesList, scanner);


        } else if (line.equalsIgnoreCase("Observe")) {
            //call Join Game from server facade
            System.out.print("Game number: ");
            int gameID;
            try {
                gameID = Integer.parseInt(scanner.nextLine());
                if (gameID < 1 || gameID > gamesList.size()) {
                    return "Invalid game number. Please run 'list' to see available games.";
                }
            } catch (NumberFormatException e) {
                return "Please enter a valid number.";
            }
            try {
                int gameNumber = gamesList.get(gameID - 1).gameID();
                GameUI gameUI = new GameUI(facade,authToken,gameNumber,"OBSERVER",url);
                String exitString = "";

                while (!exitString.equals("left") && !exitString.equals("quit")) {
                    System.out.print("Observing>>>");
                    String input = scanner.nextLine();
                    exitString = gameUI.evalInput(input);
                }
                return "Stopped Observing.";
            } catch (Exception e) {
                return e.getMessage();
            }

        }

        else if (line.equalsIgnoreCase("Quit")) {
            return "Quit";

        } else {
            System.out.println("Unknown command. Type 'Help' for options.");

        }
        return line;
    }

    private String join(List<GameData> gamesList, Scanner scanner) {
        //call Join Game from server facade
        if (gamesList.isEmpty()) {
            System.out.println("Please list games first");
        }
        else {
            System.out.print("Game number: ");
            int gameID;
            try {
                gameID = Integer.parseInt(scanner.nextLine());
                if (gameID < 1 || gameID > gamesList.size()) {
                    return "Invalid game number. Please run 'list' to see available games.";
                }
            } catch (NumberFormatException e) {
                return "Please enter a valid number.";
            }
            try {
                System.out.print("Color (WHITE/BLACK): ");
                String playerColor = scanner.nextLine().toUpperCase();
                int gameNumber = gamesList.get(gameID - 1).gameID();
                facade.joinGame(new JoinGameRequest(authToken, playerColor, gameNumber), authToken);

                GameUI gameUI = new GameUI(facade, authToken, gameNumber, playerColor, url);
                String exitString = "";
                while (!exitString.equals("left") && !exitString.equals("quit")) {
                    String input = scanner.nextLine();
                    exitString = gameUI.evalInput(input);
                    if (exitString.equals(input)) {
                        System.out.println(exitString);
                    }
                }

                return "Game Joined!";
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        return "";
    }

    private static void help() {
        System.out.println("Here is how to navigate the Chess Game: ");
        System.out.println("To Logout, simply type Logout");
        System.out.println("To start a new game, type Create");
        System.out.println("If you would like to see all games in session, type List");
        System.out.println("If you would like to Join a Game from the list of games,");
        System.out.println("Type Join. You will then be prompted with the game number and the color you wish to be.");
        System.out.println("If you would like to Observe a game from the list of games, type Observe");
        System.out.println("You will then be prompted for the game number");
        System.out.println("If you would like to quit the program, simply type Quit.");
    }

}
