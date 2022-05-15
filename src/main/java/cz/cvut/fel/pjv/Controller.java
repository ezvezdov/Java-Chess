package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.model.Model;
import cz.cvut.fel.pjv.view.View;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Controller {
    View view = null;
    Model model = null;

    void startGame(){
        view = new View();
        model = new Model();
        view.setController(this);
        model.setController(this);
        GUIStart();
    }

    private void GUIStart(){
        View.launch(view.getClass());
    }

    private void setPlayersName(){
        view.setPlayersNames(model.getPlayer1Name(),model.getPlayer2Name());
    }


    public void newMultiplayerGameAction(){
        model.startMultiplayerGame();
        view.changeBoardView(getBoardAsArrayList());
        view.setBoardWindow();
        setPlayersName();
    }

    public void newSingleplayerGameAction(){
        model.startSinglePlayerGame();
        view.changeBoardView(getBoardAsArrayList());
        view.setBoardWindow();
        setPlayersName();
    }

    public void exitButtonAction(){
        exit(0);
    }
    public void boardSquareWasClicked(int indexX, int indexY){
        System.out.println(indexX + " " + indexY);
        model.squareWasClicked(indexX, indexY);
    }
    public void saveGameAction(){model.saveGame();}
    public void continueGameAction(){
        model.continueGame();
        view.changeBoardView(getBoardAsArrayList());
        view.setBoardWindow();
        setPlayersName();
    }
    public void saveGamePGNAction() {
        model.saveGameAsPGN();
    }

    public void timerButtonAction(){
        model.nextMove();
    }

    public void updateBoard(ArrayList list){
        view.changeBoardView(list);
    }
    public void selectPiece(int boardI, int boardJ ){
        view.selectPiece(boardI,boardJ);
    }

    public void gameOver(String winnerName){
        view.gameOver(winnerName);
    }


    public String getPlayerName(){
        return view.getPlayerName();
    }

    private ArrayList getBoardAsArrayList(){
        return model.getBoardAsArrayList();
    }
}
