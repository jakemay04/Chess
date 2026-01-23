package chess;

import java.util.Arrays;
import java.util.Objects;

import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessGame.TeamColor.BLACK;

import static chess.ChessPiece.PieceType;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
                                        //  R  C
    ChessPiece[][] squares = new ChessPiece[8][8]; //Create a new 8x8 chess board
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece; //assign the given params to row/column of board
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */

    public ChessPiece getPiece(ChessPosition position) {
        if (squares[position.getRow() - 1][position.getColumn() - 1] == null) {
            return null;
        }
        else {
            return squares[position.getRow() - 1][position.getColumn() - 1];
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        squares = new ChessPiece[8][8];//Create a new 8x8 chess board
        //whiteside pieces
        addPiece(new ChessPosition(1,1), new ChessPiece(WHITE, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(1,2), new ChessPiece(WHITE, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(1,3), new ChessPiece(WHITE, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(1,4), new ChessPiece(WHITE, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(1,5), new ChessPiece(WHITE, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(1,6), new ChessPiece(WHITE, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(1,7), new ChessPiece(WHITE, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(1,8), new ChessPiece(WHITE, ChessPiece.PieceType.ROOK));

        //whiteside pawns
        for (int i=1;i<=8;i++) {
            addPiece(new ChessPosition(2,i), new ChessPiece(WHITE, ChessPiece.PieceType.PAWN));
            System.out.println(getPiece(new ChessPosition(2,i)));

        }

        //blackside pieces
        addPiece(new ChessPosition(8,1), new ChessPiece(BLACK, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(8,2), new ChessPiece(BLACK, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8,3), new ChessPiece(BLACK, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8,4), new ChessPiece(BLACK, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(8,5), new ChessPiece(BLACK, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(8,6), new ChessPiece(BLACK, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8,7), new ChessPiece(BLACK, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8,8), new ChessPiece(BLACK, ChessPiece.PieceType.ROOK));

        //blackside pawns
        for (int i=1;i<=8;i++) {
            addPiece(new ChessPosition(7,i), new ChessPiece(BLACK, ChessPiece.PieceType.PAWN));
            System.out.println(getPiece(new ChessPosition(2,i)));
        }
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "squares=" + Arrays.deepToString(squares) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
}
