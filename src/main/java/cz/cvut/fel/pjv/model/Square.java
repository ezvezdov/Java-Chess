package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.pieces.*;
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
        if(pieceType == PieceType.BISHOP){
            piece = new BishopPiece(pieceColor);
        }
        else if(pieceType == PieceType.KING){
            piece = new KingPiece(pieceColor);
        }
        else if(pieceType == PieceType.KNIGHT){
            piece = new KnightPiece(pieceColor);
        }
        else if(pieceType == PieceType.PAWN){
            piece = new PawnPiece(pieceColor);
        }
        else if(pieceType == PieceType.QUEEN){
            piece = new QueenPiece(pieceColor);
        }
        else if(pieceType == PieceType.ROOK){
            piece = new RookPiece(pieceColor);
        }
        else{
            //empty square
            piece = null;
        }
    }

}
