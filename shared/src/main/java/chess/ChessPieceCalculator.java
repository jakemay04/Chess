package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChessPieceCalculator {

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
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1,currentCol+1), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+2,currentCol+2), null));

        return moves;
    }

}
