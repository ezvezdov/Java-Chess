package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.model.pieces.PieceType;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.util.ArrayList;

public class BoardPane extends GridPane {
    View view = null;
    private int BOARD_SIZE;
    private int SQUARE_SIZE_PX;
    Rectangle[][] piecesArray;
    Rectangle[][] boardSquaresArray;
    Image[][] images = null;


    private Color selectedSquareColor = Color.GOLD;
    private Color evenSquareColor = Color.BEIGE;
    private Color oddSquareColor = Color.SADDLEBROWN;
    private Color transparentColor = Color.TRANSPARENT;

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
        for (int i = 0; i <= BOARD_SIZE+1; i++) {
            for (int j = 0; j <= BOARD_SIZE+1; j++) {
                if(i == 0 || j == 0 || i == BOARD_SIZE+1 || j == BOARD_SIZE+1){
                    char coorOnBoard = 0;
                    if(j == 0 && i != 0 && i != BOARD_SIZE+1 || j == BOARD_SIZE+1 && i != 0 && i != BOARD_SIZE+1){
                        coorOnBoard = (char)('A' + (i-1) );
                    }

                    Rectangle backgroundRectangle = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                    backgroundRectangle.setFill(Color.WHITE);
                    Text text = new Text(String.valueOf(coorOnBoard));
                    text.setX(50);
                    text.setY(50);
                    text.setTextOrigin(VPos.BASELINE);
                    text.setFont(Font.font(null, FontWeight.BOLD, 25));

                    this.add(backgroundRectangle, i, j);
                    this.add(text, i, j);
                    continue;
                }
                int shiftedI = i-1;
                int shiftedJ = j-1;

                piecesArray[shiftedI][shiftedJ] = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                Rectangle r = piecesArray[shiftedI][shiftedJ];
                boardSquaresArray[shiftedI][shiftedJ] = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                Rectangle backgroundRectangle = boardSquaresArray[shiftedI][shiftedJ];

                r.setId(String.valueOf(shiftedI) + String.valueOf(shiftedJ));
                r.setFill(transparentColor);
                r.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent t) {
                        int viewI = Integer.parseInt(r.getId()) / 10;
                        int viewJ = Integer.parseInt(r.getId()) % 10;

                        ArrayList boardCoordinates = transformView2Board(viewI,viewJ);
                        int boardI = (int) boardCoordinates.get(0);
                        int boardJ = (int) boardCoordinates.get(1);

                        System.out.println("viewI: " + viewI + " " + "Y: " + viewJ);
                        System.out.println("boardI: " + boardI + " " + "boardJ: " + boardJ);

//                        paintSelected(viewI,viewJ);
                        view.boardSquareWasClicked(boardI,boardJ);
                    }
                });
                if ((i+j) % 2 == 0) {
                    backgroundRectangle.setFill(evenSquareColor);
                }
                else{
                    backgroundRectangle.setFill(oddSquareColor);
                }

                this.add(backgroundRectangle, i, j);
                this.add(r, i, j);

            }
        }
    }

    public void paintSelected(int boardI, int boardJ){
        ArrayList viewCoordinates = transformBoard2View(boardI,boardJ);
        int viewI = (int) viewCoordinates.get(0);
        int viewJ = (int) viewCoordinates.get(1);

        if(boardSquaresArray[viewI][viewJ].getFill() == selectedSquareColor){
            Color originalSquareColor = ((viewI + viewJ) % 2 == 0) ? evenSquareColor : oddSquareColor;
            boardSquaresArray[viewI][viewJ].setFill(originalSquareColor);
        }
        else if(piecesArray[viewI][viewJ].getFill() != transparentColor ){
            boardSquaresArray[viewI][viewJ].setFill(selectedSquareColor);
        }
    }

    private ArrayList transformBoard2View(int boardI, int boardJ){
        ArrayList coord = new ArrayList();
        coord.add(boardI);
        coord.add((BOARD_SIZE-1) - boardJ);
        return coord;
    }

    private ArrayList transformView2Board(int viewI, int viewJ){
        ArrayList coord = new ArrayList();
        coord.add(viewI);
        coord.add((BOARD_SIZE-1) - viewJ);
        return coord;
    }

    public void changeBoardViewByList(ArrayList changesList){
        //{ {Color, PieceType, boardI, boardJ} ... }
        for(int i = 0; i < changesList.size(); i++){
            ArrayList squareToChange = (ArrayList) changesList.get(i);
            ArrayList<Integer> coordinates = transformBoard2View((int)squareToChange.get(2),(int)squareToChange.get(3));
            setImage((Color) squareToChange.get(0), (PieceType) squareToChange.get(1), coordinates.get(0),coordinates.get(1));
        }
    }

    public void setImage(Color pieceColor, PieceType pieceType, int viewI, int viewJ){
        System.out.println(viewI + " " + viewJ + " " +  pieceColor + " " + pieceType);

        if(pieceType == PieceType.EMPTY){
            piecesArray[viewI][viewJ].setFill(transparentColor);
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
        piecesArray[viewI][viewJ].setFill(new ImagePattern(images[colorIndex][pieceIndex]));
    }


}
