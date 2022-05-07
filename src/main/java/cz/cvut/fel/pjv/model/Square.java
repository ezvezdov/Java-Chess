package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.pieces.*;
import javafx.scene.paint.Color;

public class Square {
    private int boardI;
    private int boardJ;
    protected Piece piece = null;
    protected PieceType pieceType;
    protected Color pieceColor;

    public Square() {}

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

}
