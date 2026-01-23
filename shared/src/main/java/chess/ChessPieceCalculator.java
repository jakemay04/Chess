package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chess.ChessGame.TeamColor.WHITE;

public class ChessPieceCalculator {


    public static boolean validMove(ChessBoard board, int currentRow, int currentCol) {
        if (board.squares[currentRow][currentCol] != null) {
            return false;
        } else if (currentRow>8 || currentCol > 8) {
            return false;
        } else return currentRow != 0 && currentCol != 0;
    }

    public static Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        return moves;
    }

    public static Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        //for loop for all straight paths (up and down)
        for (int i=currentRow; i < (8-currentRow); i++) {
            moves.add(new ChessMove(myPosition, new ChessPosition(i,currentCol), null));
        }

        //for loop for all horizontal moves(left and right)
        for (int i=currentCol; i < (8-currentCol); i++) {
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow,i), null));
        }

        //for all diagonal moves up+right
        for (int i =currentCol; i < (8-currentCol); i++) {
            for (int j = currentRow; j < (8 - currentRow); j++) {
                moves.add(new ChessMove(myPosition, new ChessPosition(j, i), null));
            }
        }

        //for all diagonal moves up+left
        for (int i =currentCol; i != 1; i--) {
            for (int j = currentRow; j < (8 - currentRow); j++) {
                moves.add(new ChessMove(myPosition, new ChessPosition(j, i), null));
            }
        }

        //for all diagonal moves down+right
        for (int i =currentCol; i < (8-currentCol); i++) {
            for (int j = currentRow; j != 1; j--) {
                moves.add(new ChessMove(myPosition, new ChessPosition(j, i), null));
            }
        }

        //for all diagonal moves down+left
        for (int i =currentCol; i != 1; i--) {
            for (int j = currentRow; j != 1; j--) {
                moves.add(new ChessMove(myPosition, new ChessPosition(j, i), null));
            }
        }

        return moves;
    }

    public static Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+2,currentCol+1), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1,currentCol+2), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-2,currentCol+1), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-1,currentCol+2), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+2,currentCol-1), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1,currentCol-2), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-2,currentCol-1), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-1,currentCol-2), null));
        return moves;
    }

    public static Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        //for all diagonal moves up+right
        for (int i =currentCol; i < (8-currentCol); i++) {
            for (int j = currentRow; j < (8 - currentRow); j++) {
                moves.add(new ChessMove(myPosition, new ChessPosition(j, i), null));
            }
        }

        //for all diagonal moves up+left
        for (int i =currentCol; i != 1; i--) {
            for (int j = currentRow; j < (8 - currentRow); j++) {
                moves.add(new ChessMove(myPosition, new ChessPosition(j, i), null));
            }
        }

        //for all diagonal moves down+right
        for (int i =currentCol; i < (8-currentCol); i++) {
            for (int j = currentRow; j != 1; j--) {
                moves.add(new ChessMove(myPosition, new ChessPosition(j, i), null));
            }
        }

        //for all diagonal moves down+left
        for (int i =currentCol; i != 1; i--) {
            for (int j = currentRow; j != 1; j--) {
                moves.add(new ChessMove(myPosition, new ChessPosition(j, i), null));
            }
        }
        return moves;
    }

    public static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        //for loop for all straight paths (up and down)
        for (int i=currentRow; i < (8-currentRow); i++) {
            moves.add(new ChessMove(myPosition, new ChessPosition(i,currentCol), null));
        }

        //for loop for all horizontal moves(left and right)
        for (int i=currentCol; i < (8-currentCol); i++) {
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow,i), null));
        }
        return moves;
    }

    public static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        //valid moves
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        //valid white moves
        if (ChessPiece.PieceType == WHITE)
        if (validMove(board,currentRow+1,currentCol)) {
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1,currentCol), null));
        }
        if (validMove(board,currentRow+2,currentCol) && currentRow!=2) {
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+2,currentCol), null));
        }
        return moves;
    }

}
