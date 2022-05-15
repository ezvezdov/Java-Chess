package cz.cvut.fel.pjv.model.players;

import cz.cvut.fel.pjv.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Player {
    String name;
    Color playerColor;

    public Player(String name, Color playerColor) {
        this.name = name;
        this.playerColor = playerColor;
    }

       Player() {
    }

    public String getPlayerColorAsString(){
        if(playerColor == Color.WHITE){
            return "White";
        }
        return "Black";
    }
    public Color getPlayerColor(){
        return playerColor;
    }
    public String getPlayerName(){
        return name;
    }

    public ArrayList makeMove(int BOARD_SIZE, Square[][] board){return null;}
}
