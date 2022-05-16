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



    //start timer
    public void run(){
        isWorking = true;
        while(true){
            if(!isStopped){
                changeTimer();
            }
        }
    }

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

    public void stopClock(){
        timerTime = curTime;
        isStopped = true;
    }

    public void startTimer(){
        isStopped = false;
        lastTimeStamp = new Date(System.currentTimeMillis());
    }
    
    public LongProperty getTimestamp() {
        return longProperty;
    }

    public long getTimeAsLong(){
        return timerTime;
    }

    public void setTimerValue(long timerValue){
        longProperty.set(timerValue);
        timerTime = timerValue;

    }

}
