package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.Controller;
import cz.cvut.fel.pjv.model.pieces.PieceType;
import cz.cvut.fel.pjv.model.players.ComputerPlayer;
import cz.cvut.fel.pjv.model.players.Player;
import cz.cvut.fel.pjv.model.utils.PGNSaver;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Model {
    protected final int BOARD_SIZE = 8;
    private Controller ctrl;
    private Board board = null;
    private PGNSaver pgnSaver = new PGNSaver();



    private boolean isGame = true;

    private Player currentPlayerMove;
    private Player player1, player2;
    private boolean isSelectedPiece = false;
    private int selectedPieceI, selectedPieceJ;

    private boolean isSinglePlayer = false;

    private boolean moveHasDone = false;


    public void setController(Controller ctrl) {
        this.ctrl = ctrl;
    }

    void setIsSinglePlayer(boolean singlePlayer) {
        isSinglePlayer = singlePlayer;
    }

    boolean isSinglePlayer() {
        return isSinglePlayer;
    }

    void setCurrentPlayerColor(Color currentPlayerColor) {
        if(currentPlayerColor == player1.getPlayerColor()){
            currentPlayerMove = player1;
        }
        else{
            currentPlayerMove = player2;
        }
    }

    Color getCurrentPlayerColor() {
        return currentPlayerMove.getPlayerColor();
    }

    public String getPlayer1Name(){
        return player1.getPlayerName();
    }

    public String getPlayer2Name(){
        return player2.getPlayerName();
    }


    public void initComputerPlayer(){
        player2 = new ComputerPlayer(Color.BLACK);
    }

    public void startMultiplayerGame(){
        String player1Name = ctrl.getPlayerName();
        String player2Name = ctrl.getPlayerName();
        player1 = new Player(player1Name,Color.WHITE);
        player2 = new Player(player2Name,Color.BLACK);
        resetBoard();
    }

    public void startSinglePlayerGame(){
        // TODO randomise color
        isSinglePlayer = true;
        String player1Name = ctrl.getPlayerName();
        player1 = new Player(player1Name,Color.WHITE);
        initComputerPlayer();
        resetBoard();

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
            if(board.isValidMove(selectedPieceI,selectedPieceJ,boardI,boardJ) && !moveHasDone){
                boolean isKingCaptured = board.isKing(boardI,boardJ);
                makeMove(boardI,boardJ);

                if(isKingCaptured){
                    ctrl.gameOver(currentPlayerMove.getPlayerName());
                }

                moveHasDone = true;

            }
        }
        //select piece
        else if(!isSelectedPiece && !isEmptySquare(boardI,boardJ) && isCurrentPlayersPiece(boardI,boardJ) && !moveHasDone){
            selectPiece(boardI, boardJ);
        }
        //reselect piece
        else if(isSelectedPiece && !isEmptySquare(boardI,boardJ) && isCurrentPlayersPiece(boardI, boardJ)){
            unselectPiece();
            selectPiece(boardI, boardJ);
        }
        board.printBoard();

    }

    private void makeComputerMove(){
        ArrayList move = player2.makeMove(BOARD_SIZE, board.getBoard());
        selectPiece((int) move.get(0), (int) move.get(1));
        makeMove((int) move.get(2), (int) move.get(3));
        changeCurrentPlayerMove();
    }

    public void nextMove(){
        if(moveHasDone){
            changeCurrentPlayerMove();
            moveHasDone = false;
        }
        else{
            return;
        }
        if(isSinglePlayer){
            makeComputerMove();
        }
        unselectPiece();
    }
    private  void changeCurrentPlayerMove(){
        currentPlayerMove = currentPlayerMove == player1 ? player2 : player1;
    }

    private void makeMove(int boardI, int boardJ){
        addMoveToPGN(selectedPieceI,selectedPieceJ,boardI,boardJ);
        ArrayList changesList = board.makeMove(selectedPieceI,selectedPieceJ,boardI,boardJ);;

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
        if(selectedPieceI != -1 && selectedPieceJ != -1) {
            ctrl.selectPiece(selectedPieceI, selectedPieceJ);
            isSelectedPiece = false;
            selectedPieceI = -1;
            selectedPieceJ = -1;
        }
    }

    private boolean isCurrentPlayersPiece(int boardI, int indexJ){
        ArrayList list = board.getSquareStatus(boardI,indexJ);
        Color pieceColor = (Color) list.get(0);
        PieceType pieceType = (PieceType) list.get(1);
        return pieceColor == currentPlayerMove.getPlayerColor() && pieceType != PieceType.EMPTY;
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
        //tmp players
        player1 = new Player("player1Name",Color.WHITE);
        player2 = new Player("player2Name",Color.BLACK);

        resetBoard();
        board.loadSavedGame();
        if(isSinglePlayer){
            initComputerPlayer();
        }
    }

    public void saveGameAsPGN(){
        pgnSaver.savePGN(player1,player2);
    }
    private void addMoveToPGN(int fromI, int fromJ, int toI, int toJ){
        pgnSaver.addMove(board.getSquareStatus(fromI,fromJ),board.getSquareStatus(toI,toJ));
    }



}
