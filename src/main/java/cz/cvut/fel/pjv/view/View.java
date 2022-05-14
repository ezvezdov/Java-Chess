package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class View extends Application {
    private final int BOARD_SIZE = 8;
    private final int SQUARE_SIZE_PX = 50;

    // + 2 means add 2 squares of SQUARE_SIZE_PX for board coordinates
    private int windowSizeX = SQUARE_SIZE_PX * (BOARD_SIZE + 2); //
    // + 2 means add 2 squares of SQUARE_SIZE_PX for board coordinates
    private int windowSizeY = SQUARE_SIZE_PX * (BOARD_SIZE + 2) + 30; //

    private static Stage stage = null;
    private static Scene menuScene = null;
    private static Scene boardScene = null;
    private static Controller ctrl = null;
    private static BoardPane boardPane = null;


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
    public void initMenuScene(){
        Parent root = null;
//        FXMLLoader fxmlLoader;


        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/scenesFXML/MenuScene.fxml"));
        try {
            menuScene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        fxmlLoader.setController(ctrl);
//            root = FXMLLoader.load(Objects.requireNonNull(Controller.class.getResource("test.fxml")));
//            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/test.fxml")));

//        try {
//            menuScene = new Scene(fxmlLoader.load());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        menuScene = new Scene(new MenuPane(this,1,2));

    }
    private void initBoardScene(){
//        content.getChildren().setAll(FXMLLoader.load("vista2.fxml"));
        boardPane = new BoardPane(this,BOARD_SIZE,SQUARE_SIZE_PX);
        boardScene = new Scene(boardPane,windowSizeX,windowSizeY);
    }
    private void setMenuScene(){
        stage.setScene(menuScene);
    }
    private void setBoardScene(){
        stage.setScene(boardScene);
    }

    public void exitButtonAction(){
        ctrl.exitButtonAction();
    }
    public void boardSquareWasClicked(int boardI, int boardJ ){
        ctrl.boardSquareWasClicked(boardI,boardJ);
    }
    public void saveGameAction(){ctrl.saveGameAction();}
    public void continueGameAction(){ctrl.continueGameAction();}
    public void newSingleplayerGameAction(){
        ctrl.newSingleplayerGameAction();
    }
    public void newMultiplayerGameAction(){
        ctrl.newMultiplayerGameAction();
    }
    public void saveGameAsPGNAction(){
        ctrl.saveGamePGNAction();
    }

    public void setBoardWindow(){
        setBoardScene();
    }

    public void changeBoardView(ArrayList list){
        boardPane.changeBoardViewByList(list);
    }
    public void selectPiece(int boardI, int boardJ){
        boardPane.paintSelected(boardI,boardJ);
    }

    public String getPlayerName(){
        TextInputDialog dialog = new TextInputDialog("Name");
        dialog.setTitle("Player's name");
        dialog.setHeaderText("Please enter your name");

        Optional<String> result = dialog.showAndWait();

        if(result.isEmpty()){
            return "Unknown Player";
        }

        return  result.get();
    }


//-fx-background-image  url('file:/home/ezvezdov/Downloads/_114180633_hi009891314.jpg')
//-fx-background-size  100% 100%

    public void newSingleplayerGameAction2(){
        System.out.println("KEK");
    }
}