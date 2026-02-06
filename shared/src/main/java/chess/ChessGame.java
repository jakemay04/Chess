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
    TeamColor teamTurn = TeamColor.WHITE;
    ChessBoard gameboard;

    public ChessGame() {
        gameboard = new ChessBoard();
        gameboard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
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
        //check for valid move and if move results in check
        ChessPiece piece = gameboard.getPiece(startPosition);
        Collection<ChessMove> possibleSafeMoves = piece.pieceMoves(gameboard,startPosition);
        Collection<ChessMove> safeMoves = new ArrayList<>();

        for (ChessMove move : possibleSafeMoves) {
            //simulate move
            ChessPosition endPosition = move.getEndPosition();
            ChessPiece capturedPiece = gameboard.getPiece(endPosition);
            gameboard.addPiece(startPosition,null);
            gameboard.addPiece(endPosition,piece);

            if (!isInCheck(piece.getTeamColor())) {
                safeMoves.add(move);
            }

            //reset move
            gameboard.addPiece(startPosition,piece);
            gameboard.addPiece(endPosition,capturedPiece);

        }
        return safeMoves;
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
        TeamColor teamColor = piece.getTeamColor();

        //check turn
        if (getTeamTurn() != teamColor) {
            throw new InvalidMoveException();
        }

        //check valid moves
        Collection<ChessMove> movesThatAreValid = validMoves(startPosition);
        if (!movesThatAreValid.contains(move)) {
            throw new InvalidMoveException();
        }

        if (MockMoves.moveOutOfBounds(endPosition)) {
            if (move.getPromotionPiece() != null) {
                //handle promotion pieces
                gameboard.addPiece(endPosition,new ChessPiece(teamColor,move.getPromotionPiece()));
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
        if (teamColor == TeamColor.WHITE) {
            teamTurn = TeamColor.BLACK;
        }
        else {
            teamTurn = TeamColor.WHITE;
        }
        moveTracker++;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition myKing = MockMoves.findKing(gameboard,teamColor);

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
            if (moves.getEndPosition().equals(myKing)) {
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
        if(!isInCheck(teamColor)) {
            return false;
        }

        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                ChessPiece piece = gameboard.getPiece(new ChessPosition(r,c)) ;
                if (piece != null && piece.getTeamColor() == teamColor) {
                    if (!validMoves(new ChessPosition(r,c)).isEmpty()) {
                        return false; //if team has any possible moves, not checkmate
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */

    public boolean isInStalemate(TeamColor teamColor) {
        if(isInCheck(teamColor)) {
            return false;
        }

        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                ChessPiece piece = gameboard.getPiece(new ChessPosition(r,c)) ;
                if (piece != null && piece.getTeamColor() == teamColor) {
                    if (!validMoves(new ChessPosition(r,c)).isEmpty()) {
                        return false; //if team has any possible moves, not checkmate
                    }
                }
            }
        }
        return true;
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
