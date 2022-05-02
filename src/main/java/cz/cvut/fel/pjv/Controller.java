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
        model = new Model();
        view.setController(this);

        GUIStart();
//        model = new Model();


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
    }
    public ArrayList getBoardAsArrayList(){
        return model.getBoardAsArrayList();
    }
}
