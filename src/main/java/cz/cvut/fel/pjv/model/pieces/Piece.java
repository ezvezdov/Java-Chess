package cz.cvut.fel.pjv.model.pieces;

import cz.cvut.fel.pjv.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class Piece {
    Color pieceColor;
    int BOARD_SIZE = 8;
     boolean isLeftSide = true;

     boolean isMoved = false;
     boolean isTwoSquareMove = false;

    Piece(Color pieceColor, int boardI, int boardJ){
        this.pieceColor = pieceColor;
    }

    Piece(){}


    public void setPieceMoved(){
        isMoved = true;
    }
    public boolean getPieceMoved(){
        return isMoved;
    }

    public boolean getTwoSquareMove() {
        return isTwoSquareMove;
    }

    public void setTwoSquareMove() {
        isTwoSquareMove = true;
    }

    public boolean getLeftSide(){
        return isLeftSide;
    }

    ArrayList makePair(int boardI, int boardJ){
        ArrayList list = new ArrayList();
        list.add(boardI);
        list.add(boardJ);
        return list;
    }

    public boolean isValidMove(Square[][] board, int fromI, int fromJ, int toI, int toJ){
        ArrayList availableMovesList = makeAvailableMovesList(board,fromI,fromJ);
        System.out.println(availableMovesList);
        for(int i = 0; i < availableMovesList.size(); i++){
            int indexI = (int)((ArrayList)availableMovesList.get(i)).get(0);
            int indexJ = (int)((ArrayList)availableMovesList.get(i)).get(1);
            if(toI == indexI && toJ == indexJ){
                return true;
            }
        }
        return false;
    }


    public abstract ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ);

}
