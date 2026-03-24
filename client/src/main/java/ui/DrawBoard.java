package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

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

        //top border
        printBorder(columns,playerColor);

        if (playerColor.equals("WHITE")) {
            for (int row = 7; row >= 0; row--) {
                printRow(board, row, playerColor);
            }
        } else if (playerColor.equals("BLACK")) {
            for (int row = 0; row <= 7; row++) {
                printRow(board, row, playerColor);
            }
        }

        //bottom border
        printBorder(columns,playerColor);
    }

    private static void printRow(ChessBoard board, int row, String playerColor) {
        System.out.print(SET_BG_COLOR_BLUE + SET_TEXT_COLOR_WHITE + " " + (row + 1) + " " + RESET_BG_COLOR);

        if (playerColor.equals("WHITE")) {
            for (int column = 0; column <= 7; column++) {
                printSquare(board, row, column);
            }
        } else if (playerColor.equals("BLACK")) {
            for (int column = 7; column >= 0; column--) {
                printSquare(board, row, column);
            }
        }

        System.out.print(SET_BG_COLOR_BLUE + SET_TEXT_COLOR_WHITE + " " + (row + 1) + " " + RESET_BG_COLOR);
        System.out.println(RESET_TEXT_COLOR);
    }

    private static void printSquare(ChessBoard board, int row, int col) {
        boolean isLight = (row + col) % 2 == 1;
        String background;
        if (isLight) {
            background = SET_BG_COLOR_LIGHT_GREY;
        } else {
            background = SET_BG_COLOR_DARK_GREY;
        }

        ChessPiece piece = board.getPiece(new ChessPosition(row + 1, col + 1));
        String textColor;
        boolean isWhite = piece.getTeamColor() == ChessGame.TeamColor.WHITE;
        if (isWhite)
        String symbol = "";
        if (piece == null) {
            symbol = "   ";
        } else {
            switch(piece.getPieceType()) {
                case KING -> symbol = " K ";
                case QUEEN -> symbol = " Q ";
                case BISHOP -> symbol = " B ";
                case KNIGHT -> symbol = " N ";
                case ROOK -> symbol = " R ";
                case PAWN -> symbol = " P ";
            }
        }

        System.out.print(background + symbol);
    }

    private static void printBorder(String[] cols, String playerColor) {
        System.out.print(SET_BG_COLOR_BLUE + SET_TEXT_COLOR_WHITE + "   ");
        if (playerColor.equals("WHITE")) {
            for (String col : cols) {
                System.out.print(" " + col + " ");
            }
        } else {
            for (int i = cols.length - 1; i >= 0; i--) {
                System.out.print(" " + cols[i] + " ");
            }
        }
        System.out.println("   " + RESET_BG_COLOR + RESET_TEXT_COLOR);
    }
}