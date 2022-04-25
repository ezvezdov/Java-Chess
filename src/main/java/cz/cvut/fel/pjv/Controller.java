package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.model.Model;
import cz.cvut.fel.pjv.view.View;
import javafx.application.Application;

public class Controller {
    public Controller() {
        GUIStart();
    }
    private void GUIStart(){
        Application.launch(View.class);
    }
    public static void startGameButtonPressed(){
        System.out.println("Start Button pressed!");
        Model.startGame();
    }
    public static void exitButtonpressed(){
        System.out.println("Exit Button pressed!");
    }
}
