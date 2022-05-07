package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.model.Model;
import cz.cvut.fel.pjv.view.View;
import javafx.application.Application;

import java.util.ArrayList;

public class Controller {
    View view = null;
    Model model = null;
    public Controller()
    {
        view = new View();
        model = new Model(this);
        view.setController(this);

        GUIStart();


    }
    private void GUIStart(){
        View.launch(view.getClass());
    }
    public void startGameButtonPressed(){
        System.out.println("Start Button pressed!");
        model.startGame();
        view.changeBoardView(getBoardAsArrayList());
        view.setBoardWindow();


    }
    public void exitButtonPressed(){
        System.out.println("Exit Button pressed!");
    }

    public void boardSquareWasClicked(int indexX, int indexY){
        System.out.println(indexX + " " + indexY);
        model.squareWasClicked(indexX, indexY);
    }

    public void updateBoard(ArrayList list){
        view.changeBoardView(list);
    }
    public void selectPiece(int boardI, int boardJ ){
        view.selectPiece(boardI,boardJ);
    }


    public ArrayList getBoardAsArrayList(){
        return model.getBoardAsArrayList();
    }
}
