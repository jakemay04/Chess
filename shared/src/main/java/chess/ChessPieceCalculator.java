package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChessPieceCalculator {

    public static Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        ChessMove[] moves = new ChessMove[10];
        moves[0] = new ChessMove(myPosition, new ChessPosition(2,2), null);
        return List.of(moves);
    }

    public static Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        ChessMove[] moves = new ChessMove[10];
        moves[0] = new ChessMove(myPosition, new ChessPosition(2,2), null);
        return List.of(moves);
    }

    public static Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        ChessMove[] moves = new ChessMove[10];
        moves[0] = new ChessMove(myPosition, new ChessPosition(2,2), null);
        return List.of(moves);
    }

    public static Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        ChessMove[] moves = new ChessMove[10];
        moves[0] = new ChessMove(myPosition, new ChessPosition(2,2), null);
        return List.of(moves);    }

    public static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        ChessMove[] moves = new ChessMove[10];
        moves[0] = new ChessMove(myPosition, new ChessPosition(2,2), null);
        return List.of(moves);    }

    public static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        ChessMove[] moves = new ChessMove[10];
        moves[0] = new ChessMove(myPosition, new ChessPosition(2,2), null);
        return List.of(moves);    }

}
