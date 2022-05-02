package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.model.pieces.PieceType;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.util.ArrayList;

public class BoardPane extends GridPane {
    View view = null;
    private int BOARD_SIZE;
    private int SQUARE_SIZE_PX;
    Rectangle[][] piecesArray;
    Rectangle[][] boardSquaresArray;
    Image[][] images = null;

    private boolean isSelectedSquare = false;
    private int selectedSquareX, selectedSquareY;
    private Color selectedSquareColor;

    private int windowSizeX,windowSizeY;
    public BoardPane(View view,int windowSizeX, int windowSizeY, int BOARD_SIZE, int SQUARE_SIZE_PX) {
        this.view = view;
        this.windowSizeX = windowSizeX;
        this.windowSizeY = windowSizeY;

        this.BOARD_SIZE = BOARD_SIZE;
        this.SQUARE_SIZE_PX = SQUARE_SIZE_PX;

        this.piecesArray = new Rectangle[BOARD_SIZE][BOARD_SIZE];
        this.boardSquaresArray = new Rectangle[BOARD_SIZE][BOARD_SIZE];
        this.setBackground(new Background(new BackgroundFill(Color.GRAY,new CornerRadii(0), new Insets(0))));

        loadImages();
        generateSquares();
//        setImage(Color.WHITE,PieceType.KNIGHT,4,7);
//        setImage(Color.WHITE,PieceType.KING,0,0);
//        setImage(Color.BLACK,PieceType.KNIGHT,1,0);
//        setImage(Color.BLACK,PieceType.KING,1,1);

    }

    private void loadImages(){
        images = new Image[2][6];

        try {
            images[0][0] = new Image(new FileInputStream(getClass().getResource("/piecesImages/White/bishop.png").getFile()));
            images[0][1] = new Image(new FileInputStream(getClass().getResource("/piecesImages/White/king.png").getFile()));
            images[0][2] = new Image(new FileInputStream(getClass().getResource("/piecesImages/White/knight.png").getFile()));
            images[0][3] = new Image(new FileInputStream(getClass().getResource("/piecesImages/White/pawn.png").getFile()));
            images[0][4] = new Image(new FileInputStream(getClass().getResource("/piecesImages/White/queen.png").getFile()));
            images[0][5] = new Image(new FileInputStream(getClass().getResource("/piecesImages/White/rook.png").getFile()));

            images[1][0] = new Image(new FileInputStream(getClass().getResource("/piecesImages/Black/bishop.png").getFile()));
            images[1][1] = new Image(new FileInputStream(getClass().getResource("/piecesImages/Black/king.png").getFile()));
            images[1][2] = new Image(new FileInputStream(getClass().getResource("/piecesImages/Black/knight.png").getFile()));
            images[1][3] = new Image(new FileInputStream(getClass().getResource("/piecesImages/Black/pawn.png").getFile()));
            images[1][4] = new Image(new FileInputStream(getClass().getResource("/piecesImages/Black/queen.png").getFile()));
            images[1][5] = new Image(new FileInputStream(getClass().getResource("/piecesImages/Black/rook.png").getFile()));
        } catch (Exception e) {
            System.out.println("Pieces Images not found!\n");
            e.printStackTrace();

        }
    }

    private void generateSquares() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                piecesArray[i][j] = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                Rectangle r = piecesArray[i][j];
                boardSquaresArray[i][j] = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                Rectangle backgroundRectangle = boardSquaresArray[i][j];

                r.setId(String.valueOf(i) + String.valueOf(j));
                r.setFill(Color.TRANSPARENT);
                r.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent t) {
                        int x = Integer.parseInt(r.getId()) / 10;
                        int y = Integer.parseInt(r.getId()) % 10;

                        paintSelected(x,y);
                        view.boardSquareWasClicked(x,y);
                    }
                });
                if ((i+j) % 2 == 0) {
                    backgroundRectangle.setFill(Color.BEIGE);
                }
                else{
                    backgroundRectangle.setFill(Color.SADDLEBROWN);
                }

                this.add(backgroundRectangle, j, i);
                this.add(r, j, i);

            }
        }
    }

    private void paintSelected(int x, int y){
        if(piecesArray[x][y].getFill() != Color.TRANSPARENT ){
            if(isSelectedSquare){
                boardSquaresArray[selectedSquareX][selectedSquareY].setFill(selectedSquareColor);
            }
            isSelectedSquare = true;
            selectedSquareColor = (Color) boardSquaresArray[x][y].getFill();
            selectedSquareX = x;
            selectedSquareY = y;
            boardSquaresArray[x][y].setFill(Color.GOLD);
        }
        else if(isSelectedSquare){
            boardSquaresArray[selectedSquareX][selectedSquareY].setFill(selectedSquareColor);
            isSelectedSquare = false;
        }
    }

    public void changeBoardViewByList(ArrayList changesList){
        //{ {Color, PieceType, x, y} ... }
        for(int i = 0; i < changesList.size(); i++){
            ArrayList squareToChange = (ArrayList) changesList.get(i);
            setImage((Color) squareToChange.get(0), (PieceType) squareToChange.get(1), (int) squareToChange.get(2),(int) squareToChange.get(3));
        }
    }

    public void setImage(Color pieceColor, PieceType pieceType, int x, int y){
        System.out.println(y + " " + x + " " +  pieceColor + " " + pieceType);
        if(pieceType == PieceType.EMPTY){

            piecesArray[y][x].setFill(Color.TRANSPARENT);
            return;
        }
        int colorIndex = (pieceColor == Color.WHITE) ? 0 : 1;
        int pieceIndex = -1;
        switch (pieceType){
            case BISHOP:
                pieceIndex = 0;
                break;
            case KING:
                pieceIndex = 1;
                break;
            case KNIGHT:
                pieceIndex = 2;
                break;
            case PAWN:
                pieceIndex = 3;
                break;
            case QUEEN:
                pieceIndex = 4;
                break;
            case ROOK:
                pieceIndex = 5;
                break;
        }
        piecesArray[y][x].setFill(new ImagePattern(images[colorIndex][pieceIndex]));
    }


}
