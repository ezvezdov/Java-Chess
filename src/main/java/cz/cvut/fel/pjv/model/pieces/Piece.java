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

    Piece(Color pieceColor){
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

    /**
     * Make a pair of coordinates
     * @param boardI I piece coordinate
     * @param boardJ J piece coordinate
     * @return  coordinates pair in format
     *          {boardI, boardJ}
     */
    ArrayList<Integer> makePair(int boardI, int boardJ){
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(boardI);
        list.add(boardJ);
        return list;
    }

    /**
     * Check if move possible
     *
     * @param board board representation as Square[][]
     * @param fromI I piece start coordinate
     * @param fromJ J piece start coordinate
     * @param toI I piece destination coordinate
     * @param toJ J piece destination coordinate
     *
     * @return true if move possible
     */
    public boolean isValidMove(Square[][] board, int fromI, int fromJ, int toI, int toJ){
        ArrayList availableMovesList = makeAvailableMovesList(board,fromI,fromJ);
        System.out.println(availableMovesList);
        for (Object o : availableMovesList) {
            int indexI = (int) ((ArrayList<?>) o).get(0);
            int indexJ = (int) ((ArrayList<?>) o).get(1);
            if (toI == indexI && toJ == indexJ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Make arraylist of possible moves.
     *
     * @param board board representation as Square[][]
     * @param fromI I piece start coordinate
     * @param fromJ J piece start coordinate
     * @return Available moves ArrayList in format
     *          { {fromI, fromJ, toI, toJ} ...}
     */
    public abstract ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ);

}
