package cz.cvut.fel.pjv.view;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class BoardScene extends Scene {
    public BoardScene(int sizeX, int sizeY){
        super(new StackPane(), sizeX, sizeY);
    }
}
