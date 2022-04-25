package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.pieces.Piece;
import cz.cvut.fel.pjv.model.pieces.PieceType;
import javafx.scene.paint.Color;

public class Square {
    private int x;
    private int y;
    boolean isEmpty = true;
    Piece piece;
    PieceType pieceType;
    Color pieceColor;

    public Square() {}

    public Square(Color pieceColor,PieceType pieceType) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
    }
    private void initPiece(){
    }

}
