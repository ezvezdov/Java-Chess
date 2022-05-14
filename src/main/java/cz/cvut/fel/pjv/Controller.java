package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.model.Model;
import cz.cvut.fel.pjv.view.View;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Controller {
    View view = null;
    Model model = null;

    protected void startGame(){
        view = new View();
        model = new Model();
        view.setController(this);
        model.setController(this);
        GUIStart();
    }

    private void GUIStart(){
        View.launch(view.getClass());
    }


    public void newMultiplayerGameAction(){
        model.startMultiplayerGame();
        view.changeBoardView(getBoardAsArrayList());
        view.setBoardWindow();
    }


    public void newSingleplayerGameAction(){
        model.startSinglePlayerGame();
        view.changeBoardView(getBoardAsArrayList());
        view.setBoardWindow();
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
    }
    public void saveGamePGNAction(){
        model.saveGameAsPGN();
    }

    public void updateBoard(ArrayList list){
        view.changeBoardView(list);
    }
    public void selectPiece(int boardI, int boardJ ){
        view.selectPiece(boardI,boardJ);
    }

    public String getPlayerName(){
        return view.getPlayerName();
    }

    public ArrayList getBoardAsArrayList(){
        return model.getBoardAsArrayList();
    }
}
