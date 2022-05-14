package cz.cvut.fel.pjv.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Player {
    protected String name;
    protected Color playerColor;

    public Player(String name, Color playerColor) {
        this.name = name;
        this.playerColor = playerColor;
    }

    public Player() {
    }

    public String getPlayerColorAsString(){
        if(playerColor == Color.WHITE){
            return "White";
        }
        return "Black";
    }
    protected Color getPlayerColor(){
        return playerColor;
    }
    public String getPlayerName(){
        return name;
    }

    public ArrayList makeMove(int BOARD_SIZE, Square[][] board){return null;}
}
