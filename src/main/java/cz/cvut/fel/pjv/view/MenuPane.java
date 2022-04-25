
package cz.cvut.fel.pjv.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MenuPane extends BorderPane{
    public MenuPane(int sizeX, int sizeY) {

        Button[] buttons = new Button[2];

        var startButton = new Button("Start");
        startButton.setOnAction(e -> {
            View.startButtonPressed();
        });

        var exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            View.exitButtonPressed();
        });

        this.setHeight(sizeY);
        this.setWidth(sizeX);

        buttons[0] = startButton;
        buttons[1] = exitButton;
        var centerVbox = new VBox();
        centerVbox.getChildren().add(buttons[0]);
        centerVbox.getChildren().add(buttons[1]);
//        centerVbox.setPrefSize(50,50);
        //centerVbox.setPrefSize(,sizeY-100);
        centerVbox.setAlignment(Pos.CENTER);

        BackgroundFill bf = new BackgroundFill(Color.BLUEVIOLET, new CornerRadii(1), null);
        centerVbox.setBackground(new Background(bf));
        //this.setCenter(startButton);

        //centerVbox.setPadding(new Insets(100));
        centerVbox.setSpacing(8);
        this.setLeft(centerVbox);
    }
}
