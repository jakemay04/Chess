package ui;

import chess.*;
import model.GameData;

import java.util.Collection;

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
        } else {
            for (int row = 0; row <= 7; row++) {
                printRow(board, row, "BLACK");
            }
        }

        //bottom border
        printBorder(columns,playerColor);
    }

    private static void printRow(ChessBoard board, int row, String playerColor) {
        System.out.print(SET_BG_COLOR_BLUE + SET_TEXT_COLOR_WHITE + " " + (row + 1) + " " + RESET_BG_COLOR);

        if (playerColor.equals("WHITE")) {
            for (int column = 0; column <= 7; column++) {
                String bg = (row + column) % 2 == 1 ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_DARK_GREY;
                printSquare(board, row, column, bg);
            }
        } else if (playerColor.equals("BLACK")) {
            for (int column = 7; column >= 0; column--) {
                String bg = (row + column) % 2 == 1 ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_DARK_GREY;
                printSquare(board, row, column, bg);
            }
        }

        System.out.print(SET_BG_COLOR_BLUE + SET_TEXT_COLOR_WHITE + " " + (row + 1) + " " + RESET_BG_COLOR);
        System.out.println(RESET_TEXT_COLOR);
    }

    private static void printSquare(ChessBoard board, int row, int col, String backg) {
        ChessPiece piece = board.getPiece(new ChessPosition(row + 1, col + 1));
        String textColor = "";
        String symbol = "";
        if (piece == null) {
            symbol = "   ";
            textColor = "";
        } else {
            if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                textColor = SET_TEXT_COLOR_RED;
            } else {
                textColor = SET_TEXT_COLOR_BLUE;
            }
            switch(piece.getPieceType()) {
                case KING -> symbol = " K ";
                case QUEEN -> symbol = " Q ";
                case BISHOP -> symbol = " B ";
                case KNIGHT -> symbol = " N ";
                case ROOK -> symbol = " R ";
                case PAWN -> symbol = " P ";
            }
        }

        System.out.print(backg + textColor + symbol + RESET_TEXT_COLOR);
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

    public static void printOutBoardHighlighted(
            ChessGame game, String playerColor,
            ChessPosition position, Collection<ChessMove> validMoves) {

        ChessBoard board = game.getBoard();
        String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h"};
        printBorder(columns, playerColor);

        int start = playerColor.equals("WHITE") ? 7 : 0;
        int end = playerColor.equals("WHITE") ? -1 : 8;
        int step = playerColor.equals("WHITE") ? -1 : 1;

        for (int row = start; row != end; row += step) {
            System.out.print(SET_BG_COLOR_BLUE + SET_TEXT_COLOR_WHITE + " " + (row + 1) + " " + RESET_BG_COLOR);
            int colStart = playerColor.equals("WHITE") ? 0 : 7;
            int colEnd = playerColor.equals("WHITE") ? 8 : -1;
            int colStep = playerColor.equals("WHITE") ? 1 : -1;
            for (int col = colStart; col != colEnd; col += colStep) {
                ChessPosition cur = new ChessPosition(row + 1, col + 1);
                boolean isLight = (row + col) % 2 == 1;
                String bg = cur.equals(position) ? SET_BG_COLOR_YELLOW
                        : validMoves.stream().anyMatch(m -> m.getEndPosition().equals(cur)) ? SET_BG_COLOR_GREEN
                        : isLight ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_DARK_GREY;
                printSquare(board, row, col, bg);
            }
            System.out.print(SET_BG_COLOR_BLUE + SET_TEXT_COLOR_WHITE + " " + (row + 1) + " " + RESET_BG_COLOR);
            System.out.println(RESET_TEXT_COLOR);
        }
        printBorder(columns, playerColor);



    }
}