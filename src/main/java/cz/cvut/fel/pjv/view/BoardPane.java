package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.model.pieces.PieceType;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;

public class BoardPane extends GridPane {
    View view = null;
    private int BOARD_SIZE;
    private int SQUARE_SIZE_PX;
    Rectangle[][] squaresArray;
    Image[][] images = null;

    private int windowSizeX,windowSizeY;
    public BoardPane(View view,int windowSizeX, int windowSizeY, int BOARD_SIZE, int SQUARE_SIZE_PX) {
        this.view = view;
        this.windowSizeX = windowSizeX;
        this.windowSizeY = windowSizeY;

        this.BOARD_SIZE = BOARD_SIZE;
        this.SQUARE_SIZE_PX = SQUARE_SIZE_PX;

        this.squaresArray = new Rectangle[BOARD_SIZE][BOARD_SIZE];

        loadImages();
        generateSquares();
        setImage(Color.WHITE,PieceType.EMPTY,0,1);
    }

    private void loadImages(){
        images = new Image[2][6];
        String path = getClass().getResource("/piecesImages/White/bishop.png").getFile();
        System.out.println(path);

        try {
            images[0][0] = new Image(path.toString());
//            images[0][1] = new Image("/piecesImages/White/bishop.png");
//            images[0][2] = new Image("/piecesImages/White/bishop.png");
//            images[0][3] = new Image("/piecesImages/White/bishop.png");
//            images[0][4] = new Image("/piecesImages/White/bishop.png");
//            images[0][5] = new Image("/piecesImages/White/bishop.png");
//            images[1][0] = new Image("/piecesImages/Black/bishop.png");
//            images[1][1] = new Image("/piecesImages/Black/bishop.png");
//            images[1][2] = new Image("/piecesImages/Black/bishop.png");
//            images[1][3] = new Image("/piecesImages/Black/bishop.png");
//            images[1][4] = new Image("/piecesImages/Black/bishop.png");
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("NOT FOUND");
        }



    }

    private void generateSquares() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                squaresArray[i][j] = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                Rectangle r = squaresArray[i][j];
                r.setId(String.valueOf(i) + String.valueOf(j));
                r.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent t) {
                        r.setFill(Color.RED);
                        int x = Integer.parseInt(r.getId()) / 10;
                        int y = Integer.parseInt(r.getId()) % 10;
                        view.boardSquareWasClicked(x,y);
                    }
                });
                if ((i+j) % 2 == 0)
                    r.setFill(Color.WHITE);
                this.add(r, j, i);
            }
        }
    }
    public void changeFiguresPositions(ArrayList changesList){
        //{Color, PieceType, x, y}
        for(int i = 0; i < changesList.size(); i++){
            ArrayList squareToChange = (ArrayList) changesList.get(i);

        }
    }
//    private void changeSquare(Color color, PieceType pieceType, int x, int y){
//
//    }

    private void setImage(Color pieceColor, PieceType pieceType, int x, int y){



//        URL url = getClass().getResource("/drawIcon.png");
//        Image image = new Image("bishop.png");
//        squaresArray[x][y].setFill(new ImagePattern(images[0][1]));





//        Image img = new Image(getClass().getResource("resources/piecesImages/Black/bishop.png"));
//        BufferedImage image = ImageIO.read(getClass().getResource("/resources/icon.gif"));





    }


}
