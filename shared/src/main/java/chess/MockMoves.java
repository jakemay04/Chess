package chess;

import java.util.Collection;
import java.util.function.Predicate;

public class MockMoves {

    //Mock out moves given ChessMove object, return
    public static ChessPosition findKing(ChessBoard gameboard, ChessGame.TeamColor teamColor) {
        //return collection of all possible moves of one team filtered
        ChessPosition king = null;
        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                ChessPiece piece = gameboard.getPiece(new ChessPosition(r,c)) ;
                //find king on board, store king position
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                    king = new ChessPosition(r, c);
                }
            }
        }
        return king;
    }

    public static boolean moveOutOfBounds(ChessPosition endPosition) {
        //check if move is out of bounds
        if (endPosition.getColumn() > 0 && endPosition.getColumn() <= 8 && endPosition.getRow() <= 8 && endPosition.getRow() > 0) {
            return true;
        }
        else{
            return false;
        }
    }
}
