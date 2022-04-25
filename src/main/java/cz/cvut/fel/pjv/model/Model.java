package cz.cvut.fel.pjv.model;

public class Model {
    static Board board = null;
    public static void startGame(){
        resetBoard();
    }
    private static void resetBoard(){
        board = new Board();
    }
}
