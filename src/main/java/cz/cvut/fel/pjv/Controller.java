package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.model.Model;
import cz.cvut.fel.pjv.view.View;
import javafx.application.Application;

public class Controller {
    View view = null;
    public Controller()
    {
        view = new View();
        view.setController(this);
        GUIStart();
    }
    private void GUIStart(){
        View.launch(view.getClass());
    }
    public void startGameButtonPressed(){
        System.out.println("Start Button pressed!");
        view.setBoardWindow();
        Model.startGame();

    }
    public void exitButtonPressed(){
        System.out.println("Exit Button pressed!");
    }
    public void boardSquareWasClicked(int indexX, int indexY){
        System.out.println(indexX + " " + indexY);
    }
}
