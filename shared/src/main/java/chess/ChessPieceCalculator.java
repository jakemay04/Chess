package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class ChessPieceCalculator {

    public static void addStepMove(List<ChessMove> moves, ChessBoard board, ChessPosition from,
                                    int toRow, int toCol, ChessGame.TeamColor pieceColor) {
        if (validMove(board, toRow, toCol, pieceColor)) {
            moves.add(new ChessMove(from, new ChessPosition(toRow, toCol), null));
        }
    }

    public static void addSlidingMoves(List<ChessMove> moves, ChessBoard board, ChessPosition from,
                                       int toRow, int toCol, ChessGame.TeamColor pieceColor) {
        int row = from.getRow() + toRow;
        int col = from.getColumn() + toCol;
        while (row >= 1 && row <= 8 && col >= 1 && col <= 8) {
            if (validMove(board, row, col, pieceColor)) {
                moves.add(new ChessMove(from, new ChessPosition(row, col),null));
                if (board.getPiece(new ChessPosition(row, col)) != null) {
                    break;
                }
            } else {
                break;
            }
            row += toRow;
            col += toCol;
        }
    }


    public static boolean validMove(ChessBoard board, int currentRow, int currentCol, ChessGame.TeamColor pieceColor) {
        //check out-of-bounds first
        if (currentRow < 1 || currentRow > 8 || currentCol < 1 || currentCol > 8) {
            return false;
        }
        if (board.getPiece(new ChessPosition(currentRow,currentCol)) == null) { //if empty spot, take
            return true;
        }
        if (board.getPiece(new ChessPosition(currentRow,currentCol)).getTeamColor() != pieceColor) {
            return true;
        }
        return false;
    }

    public static Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        int[][] offsets = {{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1},{1,0}};
        for (int[] offset : offsets) {
            addStepMove(moves, board, myPosition, currentRow + offset[0], currentCol + offset[1], pieceColor);
        }

        return moves;
    }

    public static Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        List<ChessMove> moves = new ArrayList<>();
        int[][] directions = {{1,1},{1,-1},{-1,1},{-1,-1},{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] direction : directions) {
            addSlidingMoves(moves, board, myPosition, direction[0], direction[1], pieceColor);
        }
        return moves;
    }

    public static Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        int[][] offsets = {{2,1},{1,2},{-1,2},{-2,1},{-2,-1},{-1,-2},{1,-2},{2,-1}};
        for (int[] offset : offsets) {
            addStepMove(moves, board, myPosition, currentRow + offset[0], currentCol + offset[1], pieceColor);
        }

        return moves;
    }

    public static Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        List<ChessMove> moves = new ArrayList<>();
        int[][] directions = {{1,1},{1,-1},{-1,1},{-1,-1}};
        for (int [] direction : directions) {
            addSlidingMoves(moves, board, myPosition, direction[0], direction[1], pieceColor);
        }
        return moves;
    }

    public static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        List<ChessMove> moves = new ArrayList<>();
        int [][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] direction : directions) {
            addSlidingMoves(moves, board, myPosition, direction[0], direction[1], pieceColor);
        }
        return moves;
    }

    public static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition,ChessGame.TeamColor pieceColor) {
        //valid moves
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        //valid white moves
        if (pieceColor == WHITE) {

            if (validMove(board,currentRow+1,currentCol,pieceColor) && board.getPiece(new ChessPosition(currentRow+1,currentCol))==null) { //not on home row, moves once
                moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1,currentCol), null));
                if (validMove(board,currentRow+2,currentCol,pieceColor) && currentRow==2 && board.getPiece(new ChessPosition(currentRow+2,currentCol))==null) { //home row, moves twice
                    moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+2,currentCol), null));
                };
            }

            if (validMove(board,currentRow+1,currentCol+1,pieceColor)) {
                //check if up right is an opponent piece
                if (board.getPiece(new ChessPosition(currentRow+1,currentCol+1)) != null && board.getPiece(new ChessPosition(currentRow+1,currentCol+1)).getTeamColor() == BLACK) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1,currentCol+1), null));
                }
            }
            if (validMove(board,currentRow+1,currentCol-1,pieceColor)) {
                //check if up left is an opponent piece
                if (board.getPiece(new ChessPosition(currentRow+1,currentCol-1)) != null && board.getPiece(new ChessPosition(currentRow+1,currentCol-1)).getTeamColor() == BLACK) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1,currentCol-1), null));
                }
            }

        }

        //valid black moves
        if (pieceColor == BLACK) {
            if (validMove(board,currentRow-1,currentCol,pieceColor) && board.getPiece(new ChessPosition(currentRow-1,currentCol))==null) { //not on home row, moves once
                moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-1,currentCol), null));
                if (validMove(board,currentRow-2,currentCol,pieceColor) && currentRow==7 && board.getPiece(new ChessPosition(currentRow-2,currentCol))==null) { //home row, moves twice
                    moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-2,currentCol), null));
                };
            }

            if (validMove(board,currentRow-1,currentCol+1,pieceColor)) {
                //check if down right is an opponent piece
                if (board.getPiece(new ChessPosition(currentRow-1,currentCol+1)) != null && board.getPiece(new ChessPosition(currentRow-1,currentCol+1)).getTeamColor() == WHITE) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-1,currentCol+1), null));
                }
            }
            if (validMove(board,currentRow-1,currentCol-1,pieceColor)) {
                //check if down left is an opponent piece
                if (board.getPiece(new ChessPosition(currentRow-1,currentCol-1)) != null && board.getPiece(new ChessPosition(currentRow-1,currentCol-1)).getTeamColor() == WHITE) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-1,currentCol-1), null));
                }
            }
        }

        //promotion handler
        List<ChessMove> finalMoves = new ArrayList<>();
        for (ChessMove move : moves) {
            if (move.getEndPosition().getRow() == 1 || move.getEndPosition().getRow() == 8) {
                finalMoves.add(new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.QUEEN));
                finalMoves.add(new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.KNIGHT));
                finalMoves.add(new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.ROOK));
                finalMoves.add(new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.BISHOP));
            }
            else {
                finalMoves.add(move);
            }
        }
        return finalMoves;
    }

}
