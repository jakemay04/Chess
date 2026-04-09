package ui;

import chess.ChessMove;
import chess.ChessPosition;
import client.MsgHandler;
import client.ServerFacade;
import client.WebSocketFacade;
import exception.ResponseException;
import model.GameData;
import websocket.messages.ServerMessage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class GameUI implements MsgHandler {

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
        } else if (line.equalsIgnoreCase("leave")) {
            try {
                wsFacade.leaveGame(authToken, gameID);
                return "left";
            } catch (Exception e) {
                return e.getMessage();
            }

        } else if (line.equalsIgnoreCase("move")) {
            try {
                System.out.println("From (Ex. a2): ");
                String from = scanner.nextLine();

                System.out.println("To (Ex. a4): ");
                String to = scanner.nextLine();
                ChessPosition start = chessPosition(from);
                ChessPosition end = chessPosition(to);
                ChessMove move = new ChessMove(start, end, null);

                wsFacade.makeMove(authToken, gameID, move);
            } catch (Exception e) {
                return e.getMessage();
            }

        } else if (line.equalsIgnoreCase("resign")) {
            try {
                wsFacade.resignGame(authToken, gameID);
            } catch (Exception e) {
                return e.getMessage();
            }

        } else if (line.equalsIgnoreCase("highlight")) {
            System.out.println("Which piece position would you like to highlight legal moves for?");
                String pos = scanner.nextLine();

        } else if (line.equalsIgnoreCase("quit")) {
            return "quit";
        } else {
            System.out.println("Invalid command, please type help for options.");
        }

        return line;
    }

    private void help() {
        System.out.println("Possible options: ");
        System.out.println("Redraw Board - draw the current board in the terminal");
        System.out.println("Leave - leave the current game");
        System.out.println("Make Move - declare a move against your opponent");
        System.out.println("Resign - forfeit the current game let your opponent win");
        System.out.println("Highlight Legal Moves - redraw the board with the highlighted possible moves");
    }

    private ChessPosition chessPosition(String pos) {
        Map<Character, Integer> columns = Map.of(
                'a', 1, 'b', 2, 'c', 3, 'd', 4,
                'e',5,'f',6,'g',7,'h', 8
        );
        int row = Integer.parseInt(String.valueOf(pos.charAt(1)));
        int col = columns.get(pos.charAt(0));
        return new ChessPosition(row, col);
    }

    @Override
    public void notify(ServerMessage message) {
        switch (message.getServerMessageType()) {
            case NOTIFICATION -> System.out.println("\n" + message.getMessage());
            case ERROR -> System.out.println("\nError: " + message.getErrorMessage());
            case LOAD_GAME -> {
                gameData = message.getGame();
                DrawBoard.printOutBoard(gameData.game(), playerColor);
            }
        }
    }
}
