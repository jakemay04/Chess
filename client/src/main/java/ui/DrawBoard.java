package ui;

import chess.ChessBoard;
import chess.ChessGame;

public class DrawBoard {
    ChessGame game;
    String playerColor;

    public DrawBoard(ChessGame game, String playerColor) {
        this.game = game;
        this.playerColor = playerColor;
    }

    public static void printOutBoard(ChessGame game, String playerColor) {
        ChessBoard board = game.getBoard();
        String[] columns = {"a","b","c","d","e","f","g","h"};

        if (playerColor.equals("WHITE")) {
            for (int row = 7; row >= 0; row--) {

            }
        } else if (playerColor.equals("BLACK")) {
            for (int row = 0; row <= 7; row++) {

            }
        }
    }

    private static void printRow (ChessBoard board, int row, String playerColor) {

    }

    private static void printSquare (ChessBoard board, int row, int col) {

    }
