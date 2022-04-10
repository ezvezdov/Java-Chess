package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class View extends Application {
    private int windowSizeX = 500;
    private int windowSizeY = 500;

    private Stage stage = null;
    private Scene menuScene = null;
    private Scene boardScene = null;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        menuScene = new MenuScene(windowSizeX,windowSizeY);
        boardScene = new BoardScene(windowSizeX,windowSizeY);

        setMenuScene();
        stage.setTitle("JavaFX Chess");
        stage.show();
    }
    private void setMenuScene(){
        stage.setScene(menuScene);
    }
    private void setBoardScene(){
        stage.setScene(boardScene);
    }



}