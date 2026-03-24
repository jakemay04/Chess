package ui;

import chess.ChessBoard;
import chess.ChessGame;

import static ui.EscapeSequences.*;

public class DrawBoard {
    ChessGame game;
    String playerColor;

    public DrawBoard(ChessGame game, String playerColor) {
        this.game = game;
        this.playerColor = playerColor;
    }

    public static void printOutBoard(ChessGame game, String playerColor) {

        ChessBoard board = game.getBoard();
        String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h"};

        if (playerColor.equals("WHITE")) {
            for (int row = 7; row >= 0; row--) {
                printRow(board, row, playerColor);
            }
        } else if (playerColor.equals("BLACK")) {
            for (int row = 0; row <= 7; row++) {
                printRow(board, row, playerColor);
            }
        }


    }

    private static void printRow(ChessBoard board, int row, String playerColor) {
        System.out.print(SET_BG_COLOR_BLUE + SET_TEXT_COLOR_WHITE + " " + (row + 1) + " " + RESET_BG_COLOR);

        if (playerColor.equals("WHITE")) {
            for (int column = 0; column <= 7; column--) {
                printSquare(board, row, column);
            }
        } else if (playerColor.equals("BLACK")) {
            for (int column = 7; column >= 0; column++) {
                printSquare(board, row, column);
            }
        }

        System.out.print(SET_BG_COLOR_BLUE + SET_TEXT_COLOR_WHITE + " " + (row + 1) + " " + RESET_BG_COLOR);
        System.out.println(RESET_TEXT_COLOR);
    }

    private static void printSquare(ChessBoard board, int row, int col) {


    }
}