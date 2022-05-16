package cz.cvut.fel.pjv.model.players;

import cz.cvut.fel.pjv.model.ChessTimer;
import cz.cvut.fel.pjv.model.Square;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
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
        if(!playerTimer.isAlive()){
            this.playerTimer.start();
        }

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

    public void setPlayerName(String name) {
        this.name = name;
    }

    public ChessTimer getPlayerTimer() {
        return playerTimer;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
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
    }

    public LongProperty getTimestamp(){
        if(playerTimer != null){
            return playerTimer.getTimestamp();
        }
        return new SimpleLongProperty(this,"",0);

    }

    public long getTimeAsLong(){
        return playerTimer.getTimeAsLong();
    }

    public void setTimerValue(long timerValue){
        playerTimer.setTimerValue(timerValue);
    }
}
