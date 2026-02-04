package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    int moveTracker = 0;
    Boolean teamTurn;
    ChessBoard gameboard;

    public ChessGame() {
        gameboard = new ChessBoard();
        gameboard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        if (moveTracker % 2 == 0) {
            return TeamColor.WHITE;
        }
        else {
            return TeamColor.BLACK;
        }
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        if (team == TeamColor.BLACK) {
            teamTurn = true;
        }
        else {
            teamTurn = false;
        }
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = gameboard.getPiece(startPosition);
        return piece.pieceMoves(gameboard,startPosition);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition endPosition = move.getEndPosition();
        ChessPosition startPosition = move.getStartPosition();
        ChessPiece piece = gameboard.getPiece(startPosition);
        //check turn
        if (piece == null || getTeamTurn() != piece.getTeamColor()) {
            throw new InvalidMoveException();
        }

        //check valid moves
        Collection<ChessMove> movesThatAreValid = validMoves(startPosition);
        if (!movesThatAreValid.contains(move)) {
            throw new InvalidMoveException();
        }

        if (endPosition.getColumn() > 0 && endPosition.getColumn() <= 8 && endPosition.getRow() <= 8 && endPosition.getRow() > 0) {
            if (move.getPromotionPiece() != null) {
                //handle promotion pieces
                gameboard.addPiece(endPosition,new ChessPiece(piece.getTeamColor(),move.getPromotionPiece()));
            }
            else {
                gameboard.addPiece(endPosition,piece); //change piece position
            }
        }
        else {
            throw new InvalidMoveException();

        }
        //erase old piece
        gameboard.addPiece(startPosition,null);
        moveTracker++;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition opposingKing = null;
        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                ChessPiece piece = gameboard.getPiece(new ChessPosition(r,c)) ;
                //find opposing king on board, store king position
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                    opposingKing = new ChessPosition(r,c);
                }
            }
        }

        //determine if the king is in check
        //make list of all possible moves on board
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                ChessPiece piece = gameboard.getPiece(new ChessPosition(r,c)) ;
                if (piece != null) {
                    for (ChessMove move : piece.pieceMoves(gameboard,new ChessPosition(r,c))) {
                        if (piece.getTeamColor() != teamColor) {
                            possibleMoves.add(move); //add all possible moves from opposing team
                        }
                    }
                }
            }
        }
        for (ChessMove moves : possibleMoves) {
            if (moves.getEndPosition() == opposingKing) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */

    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        gameboard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return gameboard;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "moveTracker=" + moveTracker +
                ", teamTurn=" + teamTurn +
                ", gameboard=" + gameboard +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChessGame chessGame)) {
            return false;
        }
        return moveTracker == chessGame.moveTracker && Objects.equals(teamTurn, chessGame.teamTurn) && Objects.equals(gameboard, chessGame.gameboard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveTracker, teamTurn, gameboard);
    }
}
