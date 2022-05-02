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
    private int windowSizeX = 500;
    private int windowSizeY = 500;

    private final int BOARD_SIZE = 8;
    private final int SQUARE_SIZE_PX = 50;

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
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("JavaFX Chess");
        stage.show();
    }
    private  void initMenuScene(){
        MenuPane menuPane = new MenuPane(this,windowSizeX,windowSizeY);
        menuScene = new Scene(menuPane,windowSizeX,windowSizeY);

    }
    private void initBoardScene(){
        boardPane = new BoardPane(this,windowSizeX,windowSizeY,BOARD_SIZE,SQUARE_SIZE_PX);
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
    protected void boardSquareWasClicked(int indexX, int indexY ){
        ctrl.boardSquareWasClicked(indexX,indexY);
    }

    public void setBoardWindow(){
        setBoardScene();
    }

    public void changeBoardView(ArrayList list){
        boardPane.changeBoardViewByList(list);
    }



}