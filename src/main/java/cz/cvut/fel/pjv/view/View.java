package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class View extends Application {
    int windowSizeX = 500;
    int windowSizeY = 500;

    Scene menuScene = null;
    Scene boardScene = null;

    @Override
    public void start(Stage stage) {
        menuScene = new MenuScene(windowSizeX,windowSizeY);
        boardScene = new BoardScene(windowSizeX,windowSizeY);

        stage.setScene(menuScene);
        stage.setTitle("JavaFX Chess");
        stage.show();
    }

    private void makeMenuScene(){
        var label = new Label("Hello, JavaFX.");

        var startButton = new Button("Start");
        startButton.setOnAction(e -> {
            Controller.startGameButton();
            System.out.println("Button pressed " + ((Button) e.getSource()).getText());
        });

        var exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            System.out.println("Button pressed " + ((Button) e.getSource()).getText());

        });


        BorderPane root = new BorderPane();
        menuScene = new Scene(root, 640, 480);
        root.setCenter(new HBox(startButton));
        root.setBottom(new HBox(exitButton));
    }


}