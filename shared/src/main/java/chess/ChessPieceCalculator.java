package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChessPieceCalculator {

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        ChessMove[] moves = new ChessMove[10];
        moves[0] = new ChessMove(myPosition, new ChessPosition());
        return List.of(moves);
    }

    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }

    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }

    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }

    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }

}
