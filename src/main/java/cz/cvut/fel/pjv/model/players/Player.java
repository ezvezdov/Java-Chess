package cz.cvut.fel.pjv.model.players;

import cz.cvut.fel.pjv.model.ChessTimer;
import cz.cvut.fel.pjv.model.Square;
import javafx.beans.property.LongProperty;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Player {
    String name;
    Color playerColor;

    private ChessTimer playerTimer;

    public Player(String name, Color playerColor) {
        this.name = name;
        this.playerColor = playerColor;
    }


       Player() {
    }

    public void setPlayerTimer(ChessTimer chessTimer){
        this.playerTimer = chessTimer;
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

    public void initTimer(){
        playerTimer.start();
    }

    public void stopTimer(){
        playerTimer.stopClock();
    }
    public void startTimer(){
        playerTimer.startTimer();
//        playerTimer.run();
    }

    public LongProperty getTimestamp(){
        return playerTimer.getTimestamp();
    }
}
