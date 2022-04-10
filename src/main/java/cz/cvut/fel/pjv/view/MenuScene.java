
package cz.cvut.fel.pjv.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MenuScene extends Scene{

    public MenuScene(int sizeX, int sizeY) {
        super(new MenuBorderPane(),sizeX,sizeY);
        MenuBorderPane mp = new MenuBorderPane();
    }

    private static class MenuBorderPane extends BorderPane{
        public MenuBorderPane() {
            //getChildren().add(new Label("Hello World"));

            var startButton = new Button("Start");
            startButton.setOnAction(e -> {
                System.out.println("Button pressed " + ((Button) e.getSource()).getText());
            });

            var exitButton = new Button("Exit");
            exitButton.setOnAction(e -> {
                System.out.println("Button pressed " + ((Button) e.getSource()).getText());

            });

            this.setCenter(new HBox(startButton));
            this.setBottom(new HBox(exitButton));

        }
    }

}
