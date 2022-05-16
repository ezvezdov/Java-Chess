package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.Controller;
import cz.cvut.fel.pjv.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class View extends Application {
    private final int BOARD_SIZE = 8;
    private final int SQUARE_SIZE_PX = 50;

    // + 2 means add 2 squares of SQUARE_SIZE_PX for board coordinates
    private int windowSizeX = SQUARE_SIZE_PX * 16; //
    // + 2 means add 2 squares of SQUARE_SIZE_PX for board coordinates
    private int windowSizeY = SQUARE_SIZE_PX * (BOARD_SIZE + 2) + 27; //

    private static Stage stage = null;
    private static Scene menuScene = null;
    private static Scene boardScene = null;
    private static Controller ctrl = null;
    private static Model model = null;
    private static BoardPane boardPane = null;


    public void setController(Controller ctrl){
        this.ctrl = ctrl;
    }
    public void setModel(Model model){
        this.model = model;
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        GUIinit();
        setMenuScene();
        stage.setTitle("JavaFX Chess");
        stage.setResizable(false);
        stage.show();
    }
    private void GUIinit(){
        initMenuScene();
        initBoardScene();
    }

    private void initMenuScene(){
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/scenesFXML/MenuScene.fxml"));
        try {
            menuScene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initBoardScene(){
//        initMenuBar();
        boardPane = new BoardPane(this,ctrl,model,BOARD_SIZE,SQUARE_SIZE_PX);
        boardScene = new Scene(boardPane,windowSizeX,windowSizeY);
    }
    public void setMenuScene(){
        stage.setScene(menuScene);
    }
    public void setBoardScene(){
        stage.setScene(boardScene);
    }

     public void exitButtonAction(){
        ctrl.exitAction();
    }
    public void saveAndExitAction(){
        ctrl.saveAndExitAction();
    }
     public void boardSquareWasClicked(int boardI, int boardJ){
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
     public void timerButtonAction(){
        ctrl.timerButtonAction();
    }


    public void changeBoardView(ArrayList list){
        boardPane.changeBoardViewByList(list);
    }
    public void selectPiece(int boardI, int boardJ){
        boardPane.paintSelected(boardI,boardJ);
    }

//    public void setTimer(int mins, int secs){
//        boardPane.setTimer(mins,secs);
//
//    }

    public void initTimer(){
        boardPane.setTimers();
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

    public void setPlayersNames(String name1, String name2){
        boardPane.setPlayersNames(name1,name2);
    }

    public void gameOver(String winnerName){
        Dialog dialog = new Dialog<>();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Game over!");
        dialog.setHeaderText(winnerName + " win!");


        ButtonType newGameButton = new ButtonType("Go to menu");
        ButtonType SavePGNButton = new ButtonType("Save as PGN");
        ButtonType ExitButton  = new ButtonType("Exit");

        dialog.getDialogPane().getButtonTypes().addAll(newGameButton,SavePGNButton,ExitButton);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.get() == newGameButton){
            setMenuScene();
        }
        if(result.get() == SavePGNButton){
            saveGameAsPGNAction();
            setMenuScene();
        }
        if(result.get() == ExitButton){
            exitButtonAction();
        }

    }

}