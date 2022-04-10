package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.View;
import javafx.application.Application;

public class Controller {
    public Controller() {
        GUIStart();
    }
    private void GUIStart(){
        Application.launch(View.class);
    }
    public static void startGameButtonPressed(){}
}
