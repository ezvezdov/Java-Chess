package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.Controller;
import cz.cvut.fel.pjv.model.pieces.PieceType;
import cz.cvut.fel.pjv.model.utils.PGNSaver;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Model {
    private Controller ctrl;

    boolean isGame = true;
    Board board = null;

    private Color currentPlayerColor;
    private Boolean isSelectedPiece = false;
    private int selectedPieceI, selectedPieceJ;

    Player player1, player2;
    protected final int BOARD_SIZE = 8;

    boolean isSinglePlayer = false;


    PGNSaver pgnSaver = new PGNSaver();

    public Model(){
        resetBoard();
    }

    public void setController(Controller ctrl) {
        this.ctrl = ctrl;
    }

    public void setIsSinglePlayer(boolean singlePlayer) {
        isSinglePlayer = singlePlayer;
    }

    public boolean isSinglePlayer() {
        return isSinglePlayer;
    }

    public void setCurrentPlayerColor(Color currentPlayerColor) {
        this.currentPlayerColor = currentPlayerColor;
    }

    public Color getCurrentPlayerColor() {
        return currentPlayerColor;
    }

    public void initComputerPlayer(){
        player2 = new ComputerPlayer(Color.BLACK);
    }

    public void startMultiplayerGame(){
        resetBoard();
        String player1Name = ctrl.getPlayerName();
        String player2Name = ctrl.getPlayerName();
        player1 = new Player(player1Name,Color.WHITE);
        player2 = new Player(player2Name,Color.BLACK);
    }

    public void startSinglePlayerGame(){
        // TODO randomise color
        isSinglePlayer = true;
        resetBoard();
        String player1Name = ctrl.getPlayerName();
        player1 = new Player(player1Name,Color.WHITE);
        initComputerPlayer();
    }

    private boolean randomiseColors(){
        Random random = new Random();
        return random.nextBoolean();
    }

    private void resetBoard(){
        board = new Board(this);
    }

    public void squareWasClicked(int boardI, int boardJ){

        System.out.println(isCurrentPlayersPiece(boardI,boardJ));

        // make move
        if(isSelectedPiece && isEmptySquare(boardI,boardJ) || isSelectedPiece && !isCurrentPlayersPiece(boardI,boardJ)){
            if(board.isValidMove(selectedPieceI,selectedPieceJ,boardI,boardJ)){
                makeMove(boardI,boardJ);
                if(isSinglePlayer){
                    ArrayList move = player2.makeMove(BOARD_SIZE,board.board);
                    selectPiece((int) move.get(0), (int) move.get(1));
                    makeMove((int) move.get(2), (int) move.get(3));
                }
            }
        }
        //select piece
        else if(!isSelectedPiece && !isEmptySquare(boardI,boardJ) && isCurrentPlayersPiece(boardI,boardJ)){
            selectPiece(boardI, boardJ);
        }
        //reselect piece
        else if(isSelectedPiece && !isEmptySquare(boardI,boardJ) && isCurrentPlayersPiece(boardI, boardJ)){
            unselectPiece();
            selectPiece(boardI, boardJ);
        }
        board.printBoard();

    }

    private void makeMove(int boardI, int boardJ){
        addMoveToPGN(selectedPieceI,selectedPieceJ,boardI,boardJ);
        ArrayList changesList;
        changesList = board.makeMove(selectedPieceI,selectedPieceJ,boardI,boardJ);
        currentPlayerColor = currentPlayerColor == Color.BLACK ? Color.WHITE : Color.BLACK;
        unselectPiece();
        ctrl.updateBoard(changesList);

    }

    private void selectPiece(int boardI, int boardJ){
        System.out.println("Piece have selected!");
        isSelectedPiece = true;
        selectedPieceI = boardI;
        selectedPieceJ = boardJ;
        ctrl.selectPiece(boardI,boardJ);
        System.out.println("Move has done!");
    }

    private void unselectPiece(){
        System.out.println("Selecеeed piece is clear!");
        ctrl.selectPiece(selectedPieceI,selectedPieceJ);
        isSelectedPiece = false;
        selectedPieceI = -1;
        selectedPieceJ = -1;
    }

    private boolean isCurrentPlayersPiece(int boardI, int indexJ){
        ArrayList list = board.getSquareStatus(boardI,indexJ);
        Color pieceColor = (Color) list.get(0);
        PieceType pieceType = (PieceType) list.get(1);
        return pieceColor == currentPlayerColor && pieceType != PieceType.EMPTY;
    }

    private boolean isEmptySquare(int boardI, int boardJ){
        ArrayList list = board.getSquareStatus(boardI,boardJ);
        PieceType pieceType = (PieceType) list.get(1);
        return pieceType == PieceType.EMPTY;
    }

    public ArrayList getBoardAsArrayList(){
        return board.getBoardAsArrayList();
    }
    public void saveGame(){board.saveGame();}
    public void continueGame(){
        board.loadSavedGame();
        player1 = new Player("player1Name",Color.WHITE);
        if(isSinglePlayer){
            initComputerPlayer();
        }
        else{
            player2 = new Player("player2Name",Color.WHITE);
        }

    }

    public void saveGameAsPGN(){
        pgnSaver.savePGN(player1,player2);
    }
    private void addMoveToPGN(int fromI, int fromJ, int toI, int toJ){
        pgnSaver.addMove(board.getSquareStatus(fromI,fromJ),board.getSquareStatus(toI,toJ));
    }



}
