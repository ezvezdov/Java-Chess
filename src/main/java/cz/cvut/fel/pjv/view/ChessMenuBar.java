package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.Controller;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class ChessMenuBar extends MenuBar {
    private final Controller ctrl;

     ChessMenuBar(Controller ctrl){
         this.ctrl = ctrl;
        initMenuBar();

    }

    /**
     * Initialise menuBar
     */
    private void initMenuBar(){
        Menu gameMenu = new Menu("Game");
        Menu exitMenu = new Menu("Exit");

        MenuItem continueGameMenuItem = new MenuItem("Continue game");
        continueGameMenuItem.setOnAction(e -> ctrl.continueGameAction());

        MenuItem newSingleplayerGame = new MenuItem("New Singleplayer game");
        newSingleplayerGame.setOnAction(e -> ctrl.newSingleplayerGameAction());

        MenuItem newMultiplayerGame = new MenuItem("New Multiplayer game");
        newMultiplayerGame.setOnAction(e -> ctrl.newMultiplayerGameAction());

        MenuItem saveGameMenuItem = new MenuItem("Save game");
        saveGameMenuItem.setOnAction(e -> ctrl.saveGameAction());

        MenuItem saveGameAsPGN = new MenuItem("Save game as PGN");
        saveGameAsPGN.setOnAction(e -> ctrl.saveGamePGNAction());

        MenuItem saveAndGoToMenu = new MenuItem("Save and go to menu");
        saveAndGoToMenu.setOnAction(e -> ctrl.saveAndGoToMenu());

        MenuItem goToMenuWithoutSaving = new MenuItem("Go to menu without saving");
        goToMenuWithoutSaving.setOnAction(e -> ctrl.goToMenu());

        MenuItem exitWithoutSaving = new MenuItem("Exit without saving");
        exitWithoutSaving.setOnAction(e -> ctrl.exitAction());

        MenuItem saveAndExit = new MenuItem("Save and exit");
        saveAndExit.setOnAction(e -> ctrl.saveAndExitAction());


        gameMenu.getItems().addAll(continueGameMenuItem,newSingleplayerGame,newMultiplayerGame,saveGameMenuItem,saveGameAsPGN);
        exitMenu.getItems().addAll(saveAndGoToMenu,goToMenuWithoutSaving,saveAndExit,exitWithoutSaving);

        this.getMenus().addAll(gameMenu,exitMenu);

    }

}
