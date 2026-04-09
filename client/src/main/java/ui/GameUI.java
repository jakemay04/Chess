package ui;

import client.ServerFacade;
import client.WebSocketFacade;
import model.GameData;

import java.util.Scanner;

public class GameUI {

    private final ServerFacade facade;
    private final String authToken;
    private final int gameID;
    private final String playerColor;
    private final Scanner scanner = new Scanner(System.in);
    private WebSocketFacade wsFacade;
    private GameData gameData;

    public GameUI(ServerFacade facade, String authToken, int gameID, String playerColor,
                  String url)
            throws exception.ResponseException {

        this.facade = facade;
        this.authToken = authToken;
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.wsFacade = new WebSocketFacade(url, this);
        //immedietly join game when entering
        wsFacade.joinGame(authToken, gameID);
    }

    public String evalInput(String line) {
        if (line.equalsIgnoreCase("help")) {
            help();
        }
        else if (line.equalsIgnoreCase("redraw")) {
            if (gameData != null) {
                DrawBoard.printOutBoard(gameData.game(),playerColor);
            }
        }
    }

    private void help() {
        System.out.println("Possible options: ");
        System.out.println("Redraw Board - draw the current board in the terminal");
        System.out.println("Leave - leave the current game");
        System.out.println("Make Move - declare a move against your opponent");
        System.out.println("Resign - forfeit the current game let your opponent win");
        System.out.println("Highlight Legal Moves - redraw the board with the highlighted possible moves");
    }
}
