package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.model.GameType;
import cz.cvut.fel.pjv.model.Model;
import cz.cvut.fel.pjv.view.View;

import static java.lang.System.exit;

public class Controller {
    static View view = null;
    static Model model = null;

    void startGame(){
        view = new View();
        model = new Model();

        view.setController(this);
        view.setModel(model);
        model.setView(view);
        GUIStart();

    }

    private void GUIStart(){
        View.launch(view.getClass());
    }

    private void setPlayersNameView(){
        view.setPlayersNames(model.getPlayer1Name(),model.getPlayer2Name());
    }


    public void newMultiplayerGameAction(){
        model.setPlayers(view.getPlayerName(),view.getPlayerName());
        model.startGame(GameType.MULTIPLAYER);

        view.setBoardScene();
        setPlayersNameView();
        view.initTimer();
    }

    public void newSingleplayerGameAction(){
        model.setPlayers(view.getPlayerName());
        model.startGame(GameType.SINGLEPLAYER);

        view.setBoardScene();
        setPlayersNameView();
        view.initTimer();
    }

    public void exitAction(){
        exit(0);
    }

    public void saveAndExitAction(){
        saveGameAction();
        exitAction();
    }

    public void boardSquareWasClicked(int indexX, int indexY){
        System.out.println(indexX + " " + indexY);
        model.squareWasClicked(indexX, indexY);
    }
    public void saveGameAction(){model.saveGame();}
    public void continueGameAction(){
        model.setPlayers("player1Name","player2Name");
        model.startGame(GameType.MULTIPLAYER);

        view.setBoardScene();
        view.initTimer();

        model.continueGame();
        setPlayersNameView();
    }
    public void saveGamePGNAction() {
        model.saveGameAsPGN();
    }

    public void timerButtonAction(){
        model.nextMove();
    }

    public void GoToMenuAction(){
        view.setMenuScene();
    }
}
