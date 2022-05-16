package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.Controller;
import cz.cvut.fel.pjv.model.Model;
import cz.cvut.fel.pjv.model.pieces.PieceType;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class BoardPane extends GridPane {
    private final View view;
    private final Model model;
    private final Controller ctrl;
    private final int BOARD_SIZE;
    private final int SQUARE_SIZE_PX;
    private final Rectangle[][] piecesArray;
    private final Rectangle[][] boardSquaresArray;
    private Image[][] images = null;

    private final Label player1Name = new Label(), player2Name = new Label();

    Label player1Timer = new Label("00:00"), player2Timer = new Label("00:00");
    ChangeListener<Number> timer1Listener, timer2Listener;
    boolean isTimersSet = false;

    private final Color selectedSquareColor = Color.GOLD;
    private final Color evenSquareColor = Color.BEIGE;
    private final Color oddSquareColor = Color.SADDLEBROWN;
    private final Color transparentColor = Color.TRANSPARENT;

    BoardPane(View view, Controller ctrl, Model model, int BOARD_SIZE, int SQUARE_SIZE_PX) {
        this.view = view;
        this.ctrl = ctrl;
        this.model = model;

        this.BOARD_SIZE = BOARD_SIZE;
        this.SQUARE_SIZE_PX = SQUARE_SIZE_PX;


        this.piecesArray = new Rectangle[BOARD_SIZE][BOARD_SIZE];
        this.boardSquaresArray = new Rectangle[BOARD_SIZE][BOARD_SIZE];
        this.setBackground(new Background(new BackgroundFill(Color.RED,new CornerRadii(0), new Insets(0))));

        loadImages();

        setChessMenuBar();

        generateSquares();

        setTimerButton();

        initPlayerNameLabels();

        initTimersLabels();
        initTimersListeners();
    }



    private void loadImages(){
        images = new Image[2][6];

        try {
            images[0][0] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/White/bishop.png")).getFile()));
            images[0][1] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/White/king.png")).getFile()));
            images[0][2] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/White/knight.png")).getFile()));
            images[0][3] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/White/pawn.png")).getFile()));
            images[0][4] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/White/queen.png")).getFile()));
            images[0][5] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/White/rook.png")).getFile()));

            images[1][0] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/Black/bishop.png")).getFile()));
            images[1][1] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/Black/king.png")).getFile()));
            images[1][2] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/Black/knight.png")).getFile()));
            images[1][3] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/Black/pawn.png")).getFile()));
            images[1][4] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/Black/queen.png")).getFile()));
            images[1][5] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/Black/rook.png")).getFile()));
        } catch (Exception e) {
            System.out.println("Pieces Images not found!\n");
            e.printStackTrace();

        }
    }

    private void setChessMenuBar(){
        ChessMenuBar chessMenuBar = new ChessMenuBar(view);
        this.add(chessMenuBar,0,0,20,1);
    }


    private void initPlayerNameLabels(){
        player1Name.setAlignment(Pos.CENTER);
        player1Name.setFont(Font.font(null, FontWeight.BOLD, 25));
        player1Name.setTextAlignment(TextAlignment.CENTER);
        this.add(player1Name, 10,10,14, 10);
        GridPane.setHalignment(player1Name, HPos.CENTER);

        player2Name.setMaxWidth(4 * SQUARE_SIZE_PX);
        player2Name.setMaxHeight(SQUARE_SIZE_PX);
        player2Name.setAlignment(Pos.CENTER);
        player2Name.setFont(Font.font(null, FontWeight.BOLD, 25));
        player2Name.setTextAlignment(TextAlignment.CENTER);
        this.add(player2Name, 10,1,14, 1);
        GridPane.setHalignment(player2Name, HPos.CENTER);

    }
    void setPlayersNames(String name1, String name2){
        player1Name.setText(name1);
        player2Name.setText(name2);

    }

    private void setTimerButton(){
        Button timerButton = new Button("Finish move!");
        timerButton.setMaxHeight(SQUARE_SIZE_PX);
        timerButton.setMaxWidth(SQUARE_SIZE_PX * 4);
        timerButton.setOnMouseClicked(e -> ctrl.timerButtonAction());
        this.add(timerButton,11,4,14,4);
    }

    public void initTimersLabels(){
        player1Timer.setMaxWidth(4 * SQUARE_SIZE_PX);
        player1Timer.setMaxHeight(SQUARE_SIZE_PX);
        player1Timer.setAlignment(Pos.CENTER);
        player1Timer.setFont(Font.font(null, FontWeight.BOLD, 25));
        player1Timer.setTextAlignment(TextAlignment.CENTER);
        this.add(player1Timer,11,5,14,5);
        GridPane.setHalignment(player1Timer, HPos.CENTER);

        player2Timer.setFont(player1Timer.getFont());
        this.add(player2Timer,11,3,14,3);
        GridPane.setHalignment(player2Timer, HPos.CENTER);

    }

    public void initTimersListeners(){
        timer1Listener = (observable, oldValue, newValue) -> Platform.runLater(() -> {
            long value = (long) newValue;
            Date dateTime = new Date(value);
            String timeString = String.format("%02d",dateTime.getMinutes()) + ":" + String.format("%02d",dateTime.getSeconds());
            player1Timer.setText(timeString);
        });


        timer2Listener = (observable, oldValue, newValue) -> Platform.runLater(() -> {
            long value = (long) newValue;
            Date dateTime = new Date(value);
            String timeString = String.format("%02d",dateTime.getMinutes()) + ":" + String.format("%02d",dateTime.getSeconds());
            player2Timer.setText(timeString);
        });

    }


    public void setTimers(){
        if(!isTimersSet){
            model.getPlayer1Timestamp().addListener(timer1Listener);
            model.getPlayer2Timestamp().addListener(timer2Listener);
            isTimersSet = true;
        }

    }


    private void generateSquares() {
        for (int i = 0; i <= BOARD_SIZE+1; i++) {
            for (int j = 0; j <= BOARD_SIZE+1; j++) {
                if(i == 0 || j == 0 || i == BOARD_SIZE+1 || j == BOARD_SIZE+1){
                    char coordinateOnBoard = 0;
                    if(j == 0 && i != 0 && i != BOARD_SIZE+1 || j == BOARD_SIZE+1 && i != 0 && i != BOARD_SIZE+1){
                        coordinateOnBoard = (char)('A' + (i-1) );
                    }
                    else if(i == 0 && j != 0 && j != BOARD_SIZE+1 || i == BOARD_SIZE+1 && j != 0 && j != BOARD_SIZE+1){
                        coordinateOnBoard = (char) ('9' - j);
                    }

                    Rectangle backgroundRectangle = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                    backgroundRectangle.setFill(Color.WHITE);
                    Text text = new Text(String.valueOf(coordinateOnBoard));
                    text.setFont(Font.font(null, FontWeight.BOLD, 25));

                    this.add(backgroundRectangle, i, j+1);
                    this.add(text, i, j+1);
                    GridPane.setHalignment(text, HPos.CENTER);
                    continue;
                }
                int shiftedI = i-1;
                int shiftedJ = j-1;

                piecesArray[shiftedI][shiftedJ] = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                Rectangle r = piecesArray[shiftedI][shiftedJ];
                boardSquaresArray[shiftedI][shiftedJ] = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                Rectangle backgroundRectangle = boardSquaresArray[shiftedI][shiftedJ];

                r.setId(shiftedI + String.valueOf(shiftedJ));
                r.setFill(transparentColor);
                r.setOnMouseClicked(t -> {
                    int viewI = Integer.parseInt(r.getId()) / 10;
                    int viewJ = Integer.parseInt(r.getId()) % 10;

                    ArrayList boardCoordinates = transformView2Board(viewI,viewJ);
                    int boardI = (int) boardCoordinates.get(0);
                    int boardJ = (int) boardCoordinates.get(1);

                    System.out.println("viewI: " + viewI + " " + "Y: " + viewJ);
                    System.out.println("boardI: " + boardI + " " + "boardJ: " + boardJ);

                    ctrl.boardSquareWasClicked(boardI,boardJ);
//                        view.boardSquareWasClicked(boardI,boardJ);
                });
                if ((i+j) % 2 == 0) {
                    backgroundRectangle.setFill(evenSquareColor);
                }
                else{
                    backgroundRectangle.setFill(oddSquareColor);
                }

                this.add(backgroundRectangle, i, j+1);
                this.add(r, i, j+1);

            }
        }
        for(int i = BOARD_SIZE+2; i <  BOARD_SIZE+2+6; i++){
            for (int j = 0; j <= BOARD_SIZE+1; j++) {
                Rectangle backgroundRectangle = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                backgroundRectangle.setFill(Color.LIGHTGOLDENRODYELLOW);
//                backgroundRectangle.setStroke(Color.BLACK);
                this.add(backgroundRectangle, i, j+1);
            }
        }
    }

    void paintSelected(int boardI, int boardJ){
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
        ArrayList coordinates = new ArrayList();
        coordinates.add(boardI);
        coordinates.add((BOARD_SIZE-1) - boardJ);
        return coordinates;
    }

    private ArrayList transformView2Board(int viewI, int viewJ){
        ArrayList coordinates = new ArrayList();
        coordinates.add(viewI);
        coordinates.add((BOARD_SIZE-1) - viewJ);
        return coordinates;
    }

    void changeBoardViewByList(ArrayList changesList){
        //{ {Color, PieceType, boardI, boardJ} ... }
        for (Object o : changesList) {
            ArrayList squareToChange = (ArrayList) o;
            ArrayList coordinates = transformBoard2View((int) squareToChange.get(2), (int) squareToChange.get(3));
            setImage((Color) squareToChange.get(0), (PieceType) squareToChange.get(1), (Integer) coordinates.get(0), (Integer) coordinates.get(1));
        }
    }

    private void setImage(Color pieceColor, PieceType pieceType, int viewI, int viewJ){
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
