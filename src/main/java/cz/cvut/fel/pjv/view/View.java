package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.Controller;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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

        initMenuScene();
        setMenuScene();
        stage.setTitle("JavaFX Chess");
        stage.show();
    }
    private  void initMenuScene(){
        Group group = new Group();
        menuScene = new Scene(group);
        MenuPane menuPane = new MenuPane(windowSizeX,windowSizeY);
        menuScene = new Scene(menuPane,windowSizeX,windowSizeY);

    }
    private void setMenuScene(){
        stage.setScene(menuScene);
    }
    private void setBoardScene(){
        stage.setScene(boardScene);
    }
    protected static void exitButtonPressed(){
        Controller.exitButtonpressed();
    }
    protected static void startButtonPressed(){
        Controller.startGameButtonPressed();
    }
    public static void changeMenuSceneToBoard(){

    }



}