package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.view.View;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.Date;

public class ChessTimer extends Thread{


    Date lastTimeStamp = new Date(System.currentTimeMillis());
    Date currentTimeStamp;
    long timerTime = 0;
    long curTime = 0;

    boolean isStopped = true;
    boolean isWorking = false;

    View view;
    private final LongProperty longProperty;

    ChessTimer(View view){
        this.view = view;
        longProperty =  new SimpleLongProperty(this, "long", 0);
        longProperty.set(0);
        setDaemon(true);
    }


    /**
     * Thread run method.
     */
    public void run(){
        isWorking = true;
        while(true){
            if(!isStopped){
                changeTimer();
            }
        }
    }

    /**
     * change timer value.
     */
    private void changeTimer(){
        lastTimeStamp = new Date(System.currentTimeMillis());
        while (!isStopped){
            currentTimeStamp = new Date(System.currentTimeMillis());
            curTime = timerTime + (currentTimeStamp.getTime() - lastTimeStamp.getTime());
            longProperty.set(curTime);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Temporary stop timer.
     */
    public void stopTimer(){
        timerTime = curTime;
        isStopped = true;
    }

    /**
     * Continue run timer.
     */
    public void startTimer(){
        isStopped = false;
        lastTimeStamp = new Date(System.currentTimeMillis());
    }

    /**
     * Get time stamp for view listeners.
     */
    public LongProperty getTimestamp() {
        return longProperty;
    }

    /**
     * Get time as a long type (for saving timer value to file)
     * @return
     */
    public long getTimeAsLong(){
        return timerTime;
    }

    /**
     * Set timer value using long time type.
     * @param timerValue timer value in long
     */
    public void setTimerValue(long timerValue){
        longProperty.set(timerValue);
        timerTime = timerValue;

    }

}
