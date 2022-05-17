package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.players.ComputerPlayer;
import cz.cvut.fel.pjv.model.players.Player;
import cz.cvut.fel.pjv.model.utils.PGNSaver;
import javafx.beans.property.LongProperty;
import javafx.scene.paint.Color;

import cz.cvut.fel.pjv.view.View;

import java.util.ArrayList;

public class Model {
    private final int BOARD_SIZE = 8;
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

    /**
     * Initialise computer player (get information from player2 if it's possible)
     */
    private void initComputerPlayer(){
        ChessTimer chessTimer = player2.getPlayerTimer();
        player2 = new ComputerPlayer(Color.BLACK);
        player2.setPlayerTimer(chessTimer);

    }

    /**
     * Initialise timers and set it to Player player1, player2
     */
    private void initTimers(){
        player1.setPlayerTimer(new ChessTimer(view));
        player2.setPlayerTimer(new ChessTimer(view));
    }

    /**
     * Set time on timers
     * @param timer1 1st timer time
     * @param timer2 1st timer time
     */
    void setTimers(long timer1, long timer2){
        stopTimers();
        player1.setTimerValue(timer1);
        player2.setTimerValue(timer2);
    }

    /**
     * Stop all player's timers
     */
    private void stopTimers(){
        player1.stopTimer();
        player2.stopTimer();
    }

    /**
     * Is used for update timer1 value in view
     */
    public LongProperty getPlayer1Timestamp(){
        return player1.getTimestamp();
    }

    /**
     * Is used for update timer2 value in view
     */
    public LongProperty getPlayer2Timestamp(){
        return player2.getTimestamp();
    }

    /**
     *  Set view to acces view change methods
     * @param view instance of View
     */
    public void setView(View view){
        this.view = view;
    }

    /**
     *  Set game type
     *
     * @param gameType GameType.SINGLEPLAYER or GameType.MULTIPLAYER
     */
     void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    /**
     *  Get game type
     * @return GameType.SINGLEPLAYER or GameType.MULTIPLAYER
     */
    GameType getGameType(){
        return gameType;
    }

    /**
     *  Indicate type of game.
     *
     * @return true - singleplayer game. false - multiplayer game.
     */
    private boolean isSinglePlayer() {
        return gameType == GameType.SINGLEPLAYER;
    }

    /**
     * Get player1 Player innstance
     *
     * @return  Player instance
     */
    Player getPlayer1() {
        return player1;
    }

    /**
     * Get player2 Player instance
     * @return Player instance
     */
    Player getPlayer2() {
        return player2;
    }

    /**
     * Get pieces color of player who should make move
     *
     * @return color of pieces
     */
    Color getCurrentPlayerColor() {
        return currentPlayerMove.getPlayerColor();
    }

    /**
     * Get player1 name.
     *
     * @return  player1 name
     */
    public String getPlayer1Name(){
        return player1.getPlayerName();
    }

    /**
     * Get player2 name
     *
     * @return player2 name
     */
    public String getPlayer2Name(){
        return player2.getPlayerName();
    }

    /**
     *
     * Set players data in singleplayer mode
     *
     * @param player1Name name of player1
     */
    public void setPlayers(String player1Name){
        player1.setPlayerName(player1Name);
        player1.setPlayerColor(Color.WHITE);
        initComputerPlayer();
    }

    /**
     * Set players data in multiplayer mode
     *
     * @param player1Name name of player1
     * @param player2Name name of player2
     */
    public void setPlayers(String player1Name, String player2Name){
        player1.setPlayerName(player1Name); //Color.WHITE
        player2.setPlayerName(player2Name); //Color.BLACK
    }

    /**
     *
     * Reset another players data to player1, player2
     *
     * @param player1 new player1 name
     * @param player2 new player2 name
     */
    public void setPlayers(Player player1, Player player2){
        this.player1.setPlayerColor(player1.getPlayerColor());
        this.player1.setPlayerName(player1.getPlayerName());
        this.player2.setPlayerColor(player2.getPlayerColor());
        this.player2.setPlayerName(player2.getPlayerName());
    }

    /**
     *
     * Set player, who should make next move using color of his pieces.
     *
     * @param currentPlayerColor color of piece, that player play
     */
     void setCurrentPlayerColor(Color currentPlayerColor) {
        if(currentPlayerColor == player1.getPlayerColor()){
            currentPlayerMove = player1;
        }
        else{
            currentPlayerMove = player2;
        }
    }

    /**
     * Start timer of player who should make move
     */
    private void runCurrentPlayersTimer(){
        if(currentPlayerMove == player1){
            player1.startTimer();
        }
        else {
            player2.startTimer();
        }
    }


    /**
     * Chane right to move to another player.
     * If game is singleplayer, make computer move.
     */
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
    /**
     * Change currentPlayerMove. Stop previous player timer and start currentPlayer timer.
     */
    private  void changeCurrentPlayerMove(){
        currentPlayerMove.stopTimer();
        currentPlayerMove = currentPlayerMove == player1 ? player2 : player1;
        currentPlayerMove.startTimer();
    }


    /**
     * Make new instance of board
     */
    private void resetBoard(){
        board = new Board(this);
    }

    /**
     *
     * Player has clicked on board square.
     *
     * Method has 4 options.
     *  1. Make move if it's possible
     *  2. Select piece if it is CurrentPlayer piece
     *  3. Reselect piece or unselect if it's not CurrentPlayer piece
     *  4. Ignore
     *
     * @param boardI I coordinate of clicked square in Board system
     * @param boardJ J coordinate of clicked square in Board system
     */
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
//        board.printBoard();

    }

    /**
     * FOR SINGLEPLAYER GAME ONLY. Make computer move when game is singleplayer.
     */
    private void makeComputerMove(){
        ArrayList move = player2.makeMove(BOARD_SIZE, board.getBoard());
        selectPiece((int) move.get(0), (int) move.get(1));
        makeMove((int) move.get(2), (int) move.get(3));
        nextMove();

    }


    /**
     * Make move from selectedI selectedJ to toI toJ, change the board and update board in view.
     * @param toI piece I destination coordinate in Board system
     * @param toJ piece J destination coordinate in Board system
     */
    private void makeMove(int toI, int toJ){
        boolean isKingCaptured = board.isKing(toI,toJ);

        addMoveToPGN(selectedPieceI,selectedPieceJ,toI,toJ);
        ArrayList changesList = board.makeMove(selectedPieceI,selectedPieceJ,toI,toJ);
        moveHasDone = true;

        unselectPiece();
        view.changeBoardView(changesList);

        if(isKingCaptured){
            stopTimers();
            view.gameOver(currentPlayerMove.getPlayerName());
        }

    }

    /**
     *
     * Select piece and paint selection in view.
     *
     * @param boardI I coordinate to select
     * @param boardJ J coordinate to select
     */
    private void selectPiece(int boardI, int boardJ){
        System.out.println("Piece have selected!");
        isSelectedPiece = true;
        selectedPieceI = boardI;
        selectedPieceJ = boardJ;
        view.selectPiece(boardI,boardJ);
    }

    /**
     * Unselect selectedI selectedJ coordinate and delete selection in view.
     */
    private void unselectPiece(){
        System.out.println("Selecеeed piece is clear!");
        if(selectedPieceI != -1 && selectedPieceJ != -1) {
            view.selectPiece(selectedPieceI, selectedPieceJ);
            isSelectedPiece = false;
            selectedPieceI = -1;
            selectedPieceJ = -1;
        }
    }

    /**
     *  Get ArrayList of ArrayLists which represents board in format
     *  { { Color pieceColor, PieceType pieceType, boardI, boardJ } ...}
     * @return  ArrayList board representation
     */
    private ArrayList getBoardAsArrayList(){
        return board.getBoardAsArrayList();
    }

    /**
     * Prepare all for starrting game and start it, update board
     *
     * @param gameType type of started game
     */
    public void startGame(GameType gameType){
        this.gameType = gameType;
        moveHasDone = false;
        resetBoard();

        setTimers(0,0);
        runCurrentPlayersTimer();
        view.changeBoardView(getBoardAsArrayList());

    }

    /**
     * load board from saved game file and init Computer player if game type is singleplayer.
     */
    public void continueGame(){
        board.loadSavedGame();
        runCurrentPlayersTimer();
        if(isSinglePlayer()){
            initComputerPlayer();
        }

        view.changeBoardView(getBoardAsArrayList());
    }

    /**
     * Save game to file in BoardWriter/BoardReader annotation.
     */
    public void saveGame(){board.saveGame();}


    /**
     * Save game in PGN format.
     */
    public void saveGameAsPGN(){
        pgnSaver.savePGN(player1,player2);
    }

    /**
     * Add move to PGN file.
     *
     * @param fromI piece I start coordinate
     * @param fromJ piece J start coordinate
     * @param toI piece I destination coordinate
     * @param toJ piece J destination coordinate
     */
    private void addMoveToPGN(int fromI, int fromJ, int toI, int toJ){
        pgnSaver.addMove(board.getSquareStatus(fromI,fromJ),board.getSquareStatus(toI,toJ));
    }

}
