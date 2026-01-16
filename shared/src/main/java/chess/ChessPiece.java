package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
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

    public Collection<ChessMove> pieceMovesCalulator(ChessBoard board, ChessPiece piece) {
        return null;
    }


    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        //implement pieceMovesCalulator funtion
        return pieceMovesCalulator(board,piece);

    }
}
