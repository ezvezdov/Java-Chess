package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.players.ComputerPlayer;
import cz.cvut.fel.pjv.model.players.Player;
import cz.cvut.fel.pjv.model.utils.PGNSaver;
import javafx.beans.property.LongProperty;
import javafx.scene.paint.Color;

import cz.cvut.fel.pjv.view.View;

import java.util.ArrayList;

public class Model {
    protected final int BOARD_SIZE = 8;
    private  View view;
    private Board board = null;
    private final PGNSaver pgnSaver = new PGNSaver();

    private Player currentPlayerMove;
    private final Player player1 = new Player("player1", Color.WHITE);
    private Player player2 = new Player("player2", Color.BLACK);

    private boolean isSelectedPiece = false;
    private GameType gameType;
    private int selectedPieceI, selectedPieceJ;

    private boolean moveHasDone = false;

    public Model(){
        initTimers();
    }

    public void setView(View view){
        this.view = view;
    }

    void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
    GameType getGameType(){
        return gameType;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    boolean isSinglePlayer() {
        return gameType == GameType.SINGLEPLAYER;
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
        ChessTimer chessTimer = player2.getPlayerTimer();
        player2 = new ComputerPlayer(Color.BLACK);
        player2.setPlayerTimer(chessTimer);

    }

    private void initTimers(){
        player1.setPlayerTimer(new ChessTimer(view));
        player2.setPlayerTimer(new ChessTimer(view));
    }


    public void setPlayers(String player1Name){
        player1.setPlayerName(player1Name);
        player1.setPlayerColor(Color.WHITE);

        Player tmpPlayer = player2;
        initComputerPlayer();
//        player2 = new ComputerPlayer(tmpPlayer.getPlayerColor());
    }

    public void setPlayers(String player1Name, String player2Name){
        player1.setPlayerName(player1Name); //Color.WHITE
        player2.setPlayerName(player2Name); //Color.BLACK
    }

    public void setPlayers(Player player1, Player player2){
        this.player1.setPlayerColor(player1.getPlayerColor());
        this.player1.setPlayerName(player1.getPlayerName());
        this.player2.setPlayerColor(player2.getPlayerColor());
        this.player2.setPlayerName(player2.getPlayerName());
    }

    public void setTimers(long timer1, long timer2){
        stopTimers();
        player1.setTimerValue(timer1);
        player2.setTimerValue(timer2);
        runCurrentPlayersTimer();
    }



    public LongProperty getPlayer1Timestamp(){
        return player1.getTimestamp();
    }

    public LongProperty getPlayer2Timestamp(){
        return player2.getTimestamp();
    }




    private void runCurrentPlayersTimer(){
        if(currentPlayerMove == player1){
            player1.startTimer();
        }
        else {
            player2.startTimer();
        }
    }




    private void stopTimers(){
        player1.stopTimer();
        player2.stopTimer();
    }


    public void startGame(GameType gameType){
        this.gameType = gameType;
        moveHasDone = false;
        resetBoard();

        setTimers(0,0);
        runCurrentPlayersTimer();
        view.changeBoardView(getBoardAsArrayList());

    }
    public void continueGame(){
        board.loadSavedGame();
        if(isSinglePlayer()){
            initComputerPlayer();
        }

        view.changeBoardView(getBoardAsArrayList());
    }

    private void resetBoard(){
        board = new Board(this);
    }

    public void squareWasClicked(int boardI, int boardJ){

        // make move
        if(isSelectedPiece && board.isEmptySquare(boardI,boardJ) || isSelectedPiece && board.isOpponentPiece(boardI,boardJ,currentPlayerMove.getPlayerColor())){
            if(board.isValidMove(selectedPieceI,selectedPieceJ,boardI,boardJ) && !moveHasDone){

                makeMove(boardI,boardJ);
                System.out.println("Move has done!");


            }
        }
        //select piece
        else if(!isSelectedPiece && !board.isEmptySquare(boardI,boardJ) && !board.isOpponentPiece(boardI,boardJ,currentPlayerMove.getPlayerColor()) && !moveHasDone){
            selectPiece(boardI, boardJ);
        }
        //reselect piece
        else if(isSelectedPiece && !board.isEmptySquare(boardI,boardJ) && !board.isOpponentPiece(boardI, boardJ,currentPlayerMove.getPlayerColor())){
            unselectPiece();
            selectPiece(boardI, boardJ);
        }
        board.printBoard();

    }

    private void makeComputerMove(){
        ArrayList move = player2.makeMove(BOARD_SIZE, board.getBoard());
        selectPiece((int) move.get(0), (int) move.get(1));
        makeMove((int) move.get(2), (int) move.get(3));
        nextMove();

    }

    public void nextMove(){
        if(moveHasDone){
            changeCurrentPlayerMove();
            moveHasDone = false;
        }
        else{
            return;
        }
        if(isSinglePlayer() && currentPlayerMove == player2){
            makeComputerMove();
        }
        unselectPiece();
    }
    private  void changeCurrentPlayerMove(){
        currentPlayerMove.stopTimer();
        currentPlayerMove = currentPlayerMove == player1 ? player2 : player1;
        currentPlayerMove.startTimer();
    }

    private void makeMove(int boardI, int boardJ){
        boolean isKingCaptured = board.isKing(boardI,boardJ);

        addMoveToPGN(selectedPieceI,selectedPieceJ,boardI,boardJ);
        ArrayList changesList = board.makeMove(selectedPieceI,selectedPieceJ,boardI,boardJ);
        moveHasDone = true;

        unselectPiece();
        view.changeBoardView(changesList);

        if(isKingCaptured){
            stopTimers();
            view.gameOver(currentPlayerMove.getPlayerName());
        }

    }

    private void selectPiece(int boardI, int boardJ){
        System.out.println("Piece have selected!");
        isSelectedPiece = true;
        selectedPieceI = boardI;
        selectedPieceJ = boardJ;
        view.selectPiece(boardI,boardJ);
    }

    private void unselectPiece(){
        System.out.println("Selecеeed piece is clear!");
        if(selectedPieceI != -1 && selectedPieceJ != -1) {
            view.selectPiece(selectedPieceI, selectedPieceJ);
            isSelectedPiece = false;
            selectedPieceI = -1;
            selectedPieceJ = -1;
        }
    }

    public ArrayList getBoardAsArrayList(){
        return board.getBoardAsArrayList();
    }
    public void saveGame(){board.saveGame();}


    public void saveGameAsPGN(){
        pgnSaver.savePGN(player1,player2);
    }
    private void addMoveToPGN(int fromI, int fromJ, int toI, int toJ){
        pgnSaver.addMove(board.getSquareStatus(fromI,fromJ),board.getSquareStatus(toI,toJ));
    }



}
