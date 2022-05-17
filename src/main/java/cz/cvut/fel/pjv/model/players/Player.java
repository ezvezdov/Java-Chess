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

       Player() {}

    /**
     * Set chess Timer.
     */
    public void setPlayerTimer(ChessTimer chessTimer){
        this.playerTimer = chessTimer;
        if(!playerTimer.isAlive()){
            this.playerTimer.start();
        }

    }

    /**
     *  Return player color as String
     * @return String colorString
     */
    public String getPlayerColorAsString(){
        if(playerColor == Color.WHITE){
            return "White";
        }
        return "Black";
    }

    /**
     * Return color of player's pieces
     * @return Color playerColor
     */
    public Color getPlayerColor(){
        return playerColor;
    }

    /**
     * Return player name
     * @return String player name
     */
    public String getPlayerName(){
        return name;
    }

    /**
     * Set new name to player.
     * @param newName new name
     */
    public void setPlayerName(String newName) {
        this.name = newName;
    }

    /**
     * Return ChessTimer playerTimer instance.
     *
     */
    public ChessTimer getPlayerTimer() {
        return playerTimer;
    }

    /**
     * Set new color to player
     *
     * @param playerColor new color
     */
    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * Make move (useless for usual player, just for ComputerPlayer)
     *
     * @param BOARD_SIZE size of board (8)
     * @param board board representation as Square[][]
     * @return null
     */
    public ArrayList makeMove(int BOARD_SIZE, Square[][] board){return null;}

    /**
     * Stop player's timer.
     */
    public void stopTimer(){
        playerTimer.stopTimer();
    }

    /**
     * Run player's timer.
     */
    public void startTimer(){
        playerTimer.startTimer();
    }

    /**
     *  Get timestamp (for view listaners)
     *
     * @return LongProperty timestamp
     */
    public LongProperty getTimestamp(){
        if(playerTimer != null){
            return playerTimer.getTimestamp();
        }
        return new SimpleLongProperty(this,"",0);

    }

    /**
     * Get time as long type.
     *
     * @return longTime
     */
    public long getTimeAsLong(){
        return playerTimer.getTimeAsLong();
    }

    /**
     * Set new value to timer
     *
     * @param timerValue long value
     */
    public void setTimerValue(long timerValue){
        playerTimer.setTimerValue(timerValue);
    }
}
