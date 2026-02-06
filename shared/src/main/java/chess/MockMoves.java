package chess;

import java.util.Collection;

public class MockMoves {
    //Mock out moves given ChessMove object, return

    public MockMoves() {
        ChessMove move;
        ChessBoard gameboard = null;
        ChessBoard mockGameboard = null;
    }

    public MockMove(ChessMove move, ChessBoard game) {


        //return mock game board with simulated move
    }

    public Collection<ChessMove> filterChessMoves(ChessBoard board) {

        //return collection of all possible moves of one team filtered
    }

    public boolean moveOutOfBounds(ChessMove move) {
        //check if move is out of bounds
    }
}
