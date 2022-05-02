package cz.cvut.fel.pjv.model;

import java.util.ArrayList;

public class Model {
    boolean isGame = true;


    static Board board = null;
    public static void startGame(){
        resetBoard();
    }
    private static void resetBoard(){
        board = new Board();
    }

    public void squareWasClicked(){

    }
    public ArrayList getBoardAsArrayList(){
        return board.getBoardAsArrayList();
    }

}
