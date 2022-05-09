package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.Controller;
import cz.cvut.fel.pjv.model.Board;
import cz.cvut.fel.pjv.model.pieces.PieceType;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class View extends Application {
    private final int BOARD_SIZE = 8;
    private final int SQUARE_SIZE_PX = 50;

    // + 2 means add 2 squares of SQUARE_SIZE_PX for board coordinates
    private int windowSizeX = SQUARE_SIZE_PX * (BOARD_SIZE + 2);
    // + 2 means add 2 squares of SQUARE_SIZE_PX for board coordinates
    private int windowSizeY = SQUARE_SIZE_PX * (BOARD_SIZE + 2) + 30;

    private static Stage stage = null;
    private static Scene menuScene = null;
    private static Scene boardScene = null;
    private static Controller ctrl = null;
    private static BoardPane boardPane = null;
    private static MenuPane menuPane = null;


    public void setController(Controller ctrl){
        this.ctrl = ctrl;
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        initMenuScene();
        initBoardScene();
        setMenuScene();
        stage.setTitle("JavaFX Chess");
        stage.show();
    }
    private  void initMenuScene(){
        MenuPane menuPane = new MenuPane(this,windowSizeX,windowSizeY);
        menuScene = new Scene(menuPane,windowSizeX,windowSizeY);

    }
    private void initBoardScene(){
        boardPane = new BoardPane(this,BOARD_SIZE,SQUARE_SIZE_PX);
        boardScene = new Scene(boardPane,windowSizeX,windowSizeY);
    }
    private void setMenuScene(){
        stage.setScene(menuScene);
    }
    private void setBoardScene(){
        stage.setScene(boardScene);
    }

    protected void exitButtonPressed(){
        ctrl.exitButtonPressed();
    }
    protected void startButtonPressed(){
        ctrl.startGameButtonPressed();
    }
    protected void boardSquareWasClicked(int boardI, int boardJ ){
        ctrl.boardSquareWasClicked(boardI,boardJ);
    }
    protected void saveGameButtonWasPressed(){ctrl.saveGameButtonWasPressed();}
    protected void continueGameButtonWasPressed(){ctrl.continueGameButtonWasPressed();}
    protected void newGameButtonWasPressed(){ctrl.newGameButtonWasPressed();}

    public void setBoardWindow(){
        setBoardScene();
    }

    public void changeBoardView(ArrayList list){
        boardPane.changeBoardViewByList(list);
    }
    public void selectPiece(int boardI, int boardJ){
        boardPane.paintSelected(boardI,boardJ);
    }



}