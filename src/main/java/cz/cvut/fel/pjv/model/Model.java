package cz.cvut.fel.pjv.model;

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
}
