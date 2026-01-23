package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class ChessPieceCalculator {


    public static boolean validMove(ChessBoard board, int currentRow, int currentCol, ChessGame.TeamColor pieceColor) {
        //check out-of-bounds first
        if (currentRow < 1 || currentRow > 8 || currentCol < 1 || currentCol > 8) {
            return false;
        }
        if (board.getPiece(new ChessPosition(currentRow,currentCol)) == null) { //if empty spot, take
            return true;
        }
        if (board.getPiece(new ChessPosition(currentRow,currentCol)).getTeamColor() != pieceColor) {
            return true;
        }
        return false;
    }

    public static Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        if (validMove(board,currentRow+1,currentCol+1, pieceColor)) { //up right
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1, currentCol+1), null));
        }
        if (validMove(board,currentRow,currentCol+1, pieceColor)) { //right
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow, currentCol+1), null));
        }
        if (validMove(board,currentRow-1,currentCol+1, pieceColor)) { //down right
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-1, currentCol+1), null));
        }
        if (validMove(board,currentRow-1,currentCol, pieceColor)) { //down
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-1, currentCol), null));
        }
        if (validMove(board,currentRow-1,currentCol-1, pieceColor)) { //down left
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-1, currentCol-1), null));
        }
        if (validMove(board,currentRow,currentCol-1, pieceColor)) { //left
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow, currentCol-1), null));
        }
        if (validMove(board,currentRow+1,currentCol-1, pieceColor)) { //up left
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1, currentCol-1), null));
        }
        if (validMove(board,currentRow+1,currentCol, pieceColor)) { //up
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1, currentCol), null));
        }
        return moves;
    }

    public static Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        int c = currentCol+1;
        int r = currentRow+1;
        //for all diagonal moves up+right
        while (r <= 8 && c <= 8) {
            if (validMove(board,r,c, pieceColor)) {
                moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                if (board.getPiece(new ChessPosition(r,c))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
            r++; c++;
        }

        //for all diagonal moves up+left
        c = currentCol-1;
        r = currentRow+1;
        while (r <= 8 && c >= 1) {
            if (validMove(board,r,c, pieceColor)) {
                moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                if (board.getPiece(new ChessPosition(r,c))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
            r++; c--;
        }

        //for all diagonal moves down+right
        c = currentCol+1;
        r = currentRow-1;
        while (r >= 1 && c <= 8) {
            if (validMove(board,r,c, pieceColor)) {
                moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                if (board.getPiece(new ChessPosition(r,c))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
            r--; c++;
        }

        //for all diagonal moves down+left
        c = currentCol-1;
        r = currentRow-1;
        while (r >= 1 && c >= 1) {
            if (validMove(board,r,c, pieceColor)) {
                moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                if (board.getPiece(new ChessPosition(r,c))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }

            r--; c--;
        }

        for (int row = currentRow+1; row <= 8; row++){
            if (validMove(board,row,currentCol, pieceColor)){
                moves.add(new ChessMove(myPosition, new ChessPosition(row,currentCol), null));

                if (board.getPiece(new ChessPosition(row,currentCol))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
        }
        for (int row = currentRow-1; row >= 1; row--){
            if (validMove(board,row,currentCol, pieceColor)){
                moves.add(new ChessMove(myPosition, new ChessPosition(row,currentCol), null));

                if (board.getPiece(new ChessPosition(row,currentCol))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
        }
        for (int col = currentCol+1; col <= 8; col++){
            if (validMove(board,currentRow,col, pieceColor)){
                moves.add(new ChessMove(myPosition, new ChessPosition(currentRow,col), null));

                if (board.getPiece(new ChessPosition(currentRow,col))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
        }
        for (int col = currentCol-1; col >= 1; col--){
            if (validMove(board,currentRow,col, pieceColor)){
                moves.add(new ChessMove(myPosition, new ChessPosition(currentRow,col), null));
                if (board.getPiece(new ChessPosition(currentRow,col))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
        }

        return moves;
    }

    public static Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        if (validMove(board,currentRow+2,currentCol+1, pieceColor)) {
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+2,currentCol+1), null));
        }
        if (validMove(board,currentRow+1,currentCol+2, pieceColor)) {
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1,currentCol+2), null));
        }
        if (validMove(board,currentRow-2,currentCol+1, pieceColor)) {
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-2,currentCol+1), null));
        }
        if (validMove(board,currentRow-1,currentCol+2, pieceColor)) {
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-1,currentCol+2), null));
        }
        if (validMove(board,currentRow+2,currentCol-1, pieceColor)) {
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+2,currentCol-1), null));
        }
        if (validMove(board,currentRow+1,currentCol-2, pieceColor)) {
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1,currentCol-2), null));
        }
        if (validMove(board,currentRow-2,currentCol-1, pieceColor)) {
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-2,currentCol-1), null));
        }
        if (validMove(board,currentRow-1,currentCol-2, pieceColor)) {
            moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-1,currentCol-2), null));
        }
        return moves;
    }

    public static Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        int c = currentCol+1;
        int r = currentRow+1;
        //for all diagonal moves up+right
        while (r <= 8 && c <= 8) {
            if (validMove(board,r,c, pieceColor)) {
                moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                if (board.getPiece(new ChessPosition(r,c))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
            r++; c++;
        }

        //for all diagonal moves up+left
        c = currentCol-1;
        r = currentRow+1;
        while (r <= 8 && c >= 1) {
            if (validMove(board,r,c, pieceColor)) {
                moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                if (board.getPiece(new ChessPosition(r,c))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
            r++; c--;
        }

        //for all diagonal moves down+right
        c = currentCol+1;
        r = currentRow-1;
        while (r >= 1 && c <= 8) {
            if (validMove(board,r,c, pieceColor)) {
                moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                if (board.getPiece(new ChessPosition(r,c))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
            r--; c++;
        }

        //for all diagonal moves down+left
        c = currentCol-1;
        r = currentRow-1;
        while (r >= 1 && c >= 1) {
            if (validMove(board,r,c, pieceColor)) {
                moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                if (board.getPiece(new ChessPosition(r,c))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
            r--; c--;
        }
        return moves;
    }

    public static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        //for loop for all straight paths (up)
        for (int r = currentRow+1; r <= 8; r++){
            if (validMove(board,r,currentCol, pieceColor)){
                moves.add(new ChessMove(myPosition, new ChessPosition(r,currentCol), null));

                if (board.getPiece(new ChessPosition(r,currentCol))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
        }
        for (int r = currentRow-1; r >= 1; r--){
            if (validMove(board,r,currentCol, pieceColor)){
                moves.add(new ChessMove(myPosition, new ChessPosition(r,currentCol), null));

                if (board.getPiece(new ChessPosition(r,currentCol))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
        }
        for (int c = currentCol+1; c <= 8; c++){
            if (validMove(board,currentRow,c, pieceColor)){
                moves.add(new ChessMove(myPosition, new ChessPosition(currentRow,c), null));

                if (board.getPiece(new ChessPosition(currentRow,c))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
        }
        for (int c = currentCol-1; c >= 1; c--){
            if (validMove(board,currentRow,c, pieceColor)){
                moves.add(new ChessMove(myPosition, new ChessPosition(currentRow,c), null));
                if (board.getPiece(new ChessPosition(currentRow,c))!=null) {
                    break; //break after adding to prevent jumping
                }
            }
            else {
                break;
            }
        }
        return moves;
    }

    public static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition,ChessGame.TeamColor pieceColor) {
        //valid moves
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        //valid white moves
        if (pieceColor == WHITE) {

            if (validMove(board,currentRow+1,currentCol,pieceColor) && board.getPiece(new ChessPosition(currentRow+1,currentCol))==null) { //not on home row, moves once
                moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1,currentCol), null));
                if (validMove(board,currentRow+2,currentCol,pieceColor) && currentRow==2 && board.getPiece(new ChessPosition(currentRow+2,currentCol))==null) { //home row, moves twice
                    moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+2,currentCol), null));
                };
            }

            if (validMove(board,currentRow+1,currentCol+1,pieceColor)) {
                //check if up right is an opponent piece
                if (board.getPiece(new ChessPosition(currentRow+1,currentCol+1)) != null && board.getPiece(new ChessPosition(currentRow+1,currentCol+1)).getTeamColor() == BLACK) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1,currentCol+1), null));
                }
            }
            if (validMove(board,currentRow+1,currentCol-1,pieceColor)) {
                //check if up left is an opponent piece
                if (board.getPiece(new ChessPosition(currentRow+1,currentCol-1)) != null && board.getPiece(new ChessPosition(currentRow+1,currentCol-1)).getTeamColor() == BLACK) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(currentRow+1,currentCol-1), null));
                }
            }

        }

        //valid black moves
        if (pieceColor == BLACK) {
            if (validMove(board,currentRow-1,currentCol,pieceColor) && board.getPiece(new ChessPosition(currentRow-1,currentCol))==null) { //not on home row, moves once
                moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-1,currentCol), null));
                if (validMove(board,currentRow-2,currentCol,pieceColor) && currentRow==7 && board.getPiece(new ChessPosition(currentRow-2,currentCol))==null) { //home row, moves twice
                    moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-2,currentCol), null));
                };
            }

            if (validMove(board,currentRow-1,currentCol+1,pieceColor)) {
                //check if down right is an opponent piece
                if (board.getPiece(new ChessPosition(currentRow-1,currentCol+1)) != null && board.getPiece(new ChessPosition(currentRow-1,currentCol+1)).getTeamColor() == WHITE) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-1,currentCol+1), null));
                }
            }
            if (validMove(board,currentRow-1,currentCol-1,pieceColor)) {
                //check if down left is an opponent piece
                if (board.getPiece(new ChessPosition(currentRow-1,currentCol-1)) != null && board.getPiece(new ChessPosition(currentRow-1,currentCol-1)).getTeamColor() == WHITE) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(currentRow-1,currentCol-1), null));
                }
            }
        }

        //promotion handler
        List<ChessMove> finalMoves = new ArrayList<>();
        for (ChessMove move : moves) {
            if (move.getEndPosition().getRow() == 1 || move.getEndPosition().getRow() == 8) {
                finalMoves.add(new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.QUEEN));
                finalMoves.add(new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.KNIGHT));
                finalMoves.add(new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.ROOK));
                finalMoves.add(new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.BISHOP));
            }
            else {
                finalMoves.add(move);
            }
        }
        return finalMoves;
    }

}
