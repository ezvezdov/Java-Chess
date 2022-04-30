
package cz.cvut.fel.pjv.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MenuPane extends BorderPane{
    View view = null;
    public MenuPane(View view,int sizeX, int sizeY) {
        this.view = view;

        Button[] buttons = new Button[2];

        var startButton = new Button("Start");
        startButton.setOnAction(e -> {
            this.view.startButtonPressed();
        });

        var exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            this.view.exitButtonPressed();
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
