package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.pieces.*;
import javafx.scene.paint.Color;

public class Square {
    private int indexI;
    private int indexJ;
    protected Piece piece = null;
    protected PieceType pieceType;
    protected Color pieceColor;

    public Square() {}

    public Square(Color pieceColor,PieceType pieceType, int indexI, int indexJ) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
        this.indexI = indexI;
        this.indexJ = indexJ;

        initPiece();
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isEmpty(){
        return pieceType == PieceType.EMPTY;
    }

    public void setEmpty(){
        pieceType = PieceType.EMPTY;
        piece = null;
    }

    public void setPiece(Square sq){
        pieceColor = sq.pieceColor;
        pieceType = sq.pieceType;
        piece = sq.piece;
    }

    private void initPiece(){
        switch (pieceType){
            case BISHOP:
                piece = new BishopPiece(pieceColor);
                break;
            case KING:
                piece = new KingPiece(pieceColor);
                break;
            case KNIGHT:
                piece = new KnightPiece(pieceColor);
                break;
            case PAWN:
                piece = new PawnPiece(pieceColor);
                break;
            case QUEEN:
                piece = new QueenPiece(pieceColor);
                break;
            case ROOK:
                piece = new RookPiece(pieceColor);
                break;
        }

    }

}
