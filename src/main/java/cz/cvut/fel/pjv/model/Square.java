package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.pieces.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Square {
    private int boardI;
    private int boardJ;
    private Piece piece = null;
    private PieceType pieceType;
    private Color pieceColor;

    public Square(Color pieceColor, PieceType pieceType, int boardI, int boardJ) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
        this.boardI = boardI;
        this.boardJ = boardJ;

        initPiece();
    }


    public PieceType getPieceType() {
        return pieceType;
    }
    public Color getPieceColor() {
        return pieceColor;
    }

    public boolean isEmpty(){
        return pieceType == PieceType.EMPTY;
    }

    public boolean isOpponent(Color color){
        return color != getPieceColor();
    }
    public boolean isEmptyOrOpponent(Color color){
        if(isEmpty()){
            return true;
        }
        return isOpponent(color);
    }
    boolean isKing(){
        return pieceType == PieceType.KING;
    }

    public boolean isRook(){
        return pieceType == PieceType.ROOK;
    }

    public boolean isPawn(){
        return pieceType == PieceType.PAWN;
    }

    void setEmpty(){
        pieceType = PieceType.EMPTY;
        piece = null;
    }
    public void pieceHasMoved(){
        piece.setPieceMoved();
    }

    public boolean getPieceMoved(){
        return piece.getPieceMoved();
    }

    void setPieceFromSquare(Square sq){
        pieceColor = sq.pieceColor;
        pieceType = sq.pieceType;
        piece = sq.piece;
    }

     void setQuinInsteadOfPawn(){
        piece = new QueenPiece(pieceColor,boardI,boardJ);
        pieceType = PieceType.QUEEN;
    }

    private void initPiece(){
        switch (pieceType){
            case BISHOP:
                piece = new BishopPiece(pieceColor,boardI,boardJ);
                break;
            case KING:
                piece = new KingPiece(pieceColor,boardI,boardJ);
                break;
            case KNIGHT:
                piece = new KnightPiece(pieceColor,boardI,boardJ);
                break;
            case PAWN:
                piece = new PawnPiece(pieceColor,boardI,boardJ);
                break;
            case QUEEN:
                piece = new QueenPiece(pieceColor,boardI,boardJ);
                break;
            case ROOK:
                piece = new RookPiece(pieceColor,boardI,boardJ);
                break;
        }

    }
    public boolean isValidMove(Square[][] board, int toI, int toJ){
        return this.piece.isValidMove(board, this.boardI, this.boardJ, toI, toJ);
    }

    public ArrayList getPieceAvailableMoves(Square[][] board, int fromI, int fromJ){
        if(board[fromI][fromJ].isEmpty()){
            return new ArrayList();
        }
        return this.piece.makeAvailableMovesList(board,fromI, fromJ);
    }

}
