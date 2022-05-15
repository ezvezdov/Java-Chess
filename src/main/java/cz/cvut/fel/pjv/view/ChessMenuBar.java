package cz.cvut.fel.pjv.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class ChessMenuBar extends MenuBar {
    private View view;

     ChessMenuBar(View view){
        this.view = view;
        initMenuBar();
    }

    private void initMenuBar(){
        Menu gameMenu = new Menu("Game");
        Menu exitMenu = new Menu("Exit");

        MenuItem continueGameMenuItem = new MenuItem("Continue game");
        continueGameMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                view.continueGameAction();
            }
        });
        MenuItem newSingleplayerGame = new MenuItem("New Singleplayer game");
        newSingleplayerGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
//                view.newGameAction();
                view.newSingleplayerGameAction();
            }
        });
        MenuItem newMultiplayerGame = new MenuItem("New Multiplayer game");
        newMultiplayerGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                view.newMultiplayerGameAction();
            }
        });
        MenuItem saveGameMenuItem = new MenuItem("Save game");
        saveGameMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                view.saveGameAction();
            }
        });
        MenuItem saveGameAsPGN = new MenuItem("Save game as PGN");
        saveGameAsPGN.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                view.saveGameAsPGNAction();
            }
        });

        MenuItem exitWithoutSaving = new MenuItem("Exit without saving");
        exitWithoutSaving.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                view.exitButtonAction();
            }
        });

        MenuItem saveAndExit = new MenuItem("Save and exit");
        saveAndExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                view.saveGameAction();
                view.exitButtonAction();
            }
        });




        // add menu items to menu
        gameMenu.getItems().addAll(continueGameMenuItem,newSingleplayerGame,saveGameMenuItem,saveGameAsPGN);
//        gameMenu.getItems().add();
//        gameMenu.getItems().add();
//        gameMenu.getItems().add();

        exitMenu.getItems().addAll(saveAndExit,exitWithoutSaving);

        this.getMenus().addAll(gameMenu,exitMenu);

    }

}
