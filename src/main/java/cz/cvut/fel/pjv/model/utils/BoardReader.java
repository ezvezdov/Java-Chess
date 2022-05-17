package cz.cvut.fel.pjv.model.utils;

import cz.cvut.fel.pjv.model.Board;
import cz.cvut.fel.pjv.model.GameType;
import cz.cvut.fel.pjv.model.pieces.PieceType;
import cz.cvut.fel.pjv.model.players.Player;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BoardReader extends FilesIO{

    private final Board board;
    private String filePath;

    public BoardReader(Board board){
        this.board = board;
    }



    /**
     * Receive new layout file path and ready to set new layout using setData()
     *
     * @param filePath path of file with board annotation
     */
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    /**
     * Get information of pieces placed in board from board annotation file and set it on board.
     *
    */
    public void setData(){
        setScanner(filePath);

        ArrayList<String> playersData = new ArrayList<>();
        ArrayList<String> timersData = new ArrayList<>();

        while (scanner.hasNext()){
            String line = scanner.nextLine();

            //commented parts in boardStatusAnnotation
            if(line.length() >= 1  && line.charAt(0) == '#'){
                continue;
            }

            String[] rowPiecesData = line.split(" ");
            for (String curPieceData : rowPiecesData) {

                if (curPieceData.length() >= 1 && curPieceData.charAt(0) == '#') {
                    break;
                } else if (curPieceData.length() >= 2 && curPieceData.charAt(0) == '@') {
                    setCurrentMoveColor(curPieceData.charAt(1));
                } else if (curPieceData.length() >= 2 && curPieceData.charAt(0) == '$') {
                    setGameType(curPieceData.charAt(1));
                } else if (curPieceData.length() >= 4 && curPieceData.charAt(0) == '%') {
                    playersData.add(curPieceData);
                } else if (curPieceData.length() >= 4 && curPieceData.charAt(0) == '&') {
                    timersData.add(curPieceData);
                } else if (curPieceData.length() == 4) {
                    setPiece(curPieceData);
                }
            }
        }
        if(playersData.size() >= 2){
            setPlayers(playersData.get(0),playersData.get(1));
        }
        if(timersData.size() >= 2){
            setTimers(timersData.get(0), timersData.get(1));
        }

        scanner.close();
    }

    /**
     * Transform string (row) piece data to ArrayList and set it on board
     *
     * @param pieceRowData String in format cTaC, c - piece color {w, b}, T - piece type {B, K, N, P, Q, R, E}
     *                     a - coordinate in letter coordination system {a,b,c,d,e,f,g,h}
     *                     C - int coordinate {1,2,3,4,5,6,7,8}
     */
    private void setPiece(String pieceRowData){

        Color pieceColor;
        PieceType pieceType;
        int boardI, boardJ;

        if(pieceRowData.charAt(0) == 'w'){
            pieceColor = Color.WHITE;
        }
        else if(pieceRowData.charAt(0) == 'b'){
            pieceColor = Color.BLACK;
        }
        else {return;}

        if(pieceRowData.charAt(1) == 'B'){
            pieceType = PieceType.BISHOP;
        }
        else if(pieceRowData.charAt(1) == 'K') {
            pieceType = PieceType.KING;
        }
        else if(pieceRowData.charAt(1) == 'N') {
            pieceType = PieceType.KNIGHT;
        }
        else if(pieceRowData.charAt(1) == 'P') {
            pieceType = PieceType.PAWN;
        }
        else if(pieceRowData.charAt(1) == 'Q') {
            pieceType = PieceType.QUEEN;
        }
        else if(pieceRowData.charAt(1) == 'R') {
            pieceType = PieceType.ROOK;
        }
        else if(pieceRowData.charAt(1) == 'E'){
            pieceType = PieceType.EMPTY;
        }
        else {return;}

        if (pieceRowData.charAt(2) >= 'a' && pieceRowData.charAt(2) <= 'h'){
            boardI = pieceRowData.charAt(2) - 'a';
        }
        else{return;}

        if(pieceRowData.charAt(3) >= '1' && pieceRowData.charAt(3) <= '8'){
            boardJ = pieceRowData.charAt(3) - '1';
        }
        else{return;}

        board.setSquare(pieceColor,pieceType,boardI,boardJ);
    }

    /**
     * Set current move player
     *
     * @param color current move player color
     */
    private void setCurrentMoveColor(char color){
        if(color == 'w'){
            board.setCurrentPlayerColor(Color.WHITE);
        }
        else{
            board.setCurrentPlayerColor(Color.BLACK);
        }

    }

    /**
     * Set Game type if
     *
     * @param gameType game type char 'm' or 's'
     */
    private void setGameType(char gameType){
        if(gameType == 's'){
            board.setGameType(GameType.SINGLEPLAYER);
        }
        else{
            board.setGameType(GameType.MULTIPLAYER);
        }
    }

    /**
     *
     * Player's data decoder.
     *
     * @param playerData player data
     * @return player instance with setted player data
     */
    private Player getPlayerFromData(String playerData){
        Color playerColor;
        String playerName = playerData.substring(3);
        if(playerData.charAt(1) == 'w'){
            playerColor = Color.WHITE;
        }
        else if(playerData.charAt(1) == 'b'){
            playerColor = Color.BLACK;
        }
        else{
            return null;
        }
        return new Player(playerName,playerColor);
    }

    /**
     * Set saved players infromation
     *
     * @param player1SData player1 data
     * @param player2Data player2 data
     */
    private void setPlayers(String player1SData, String player2Data){

        board.setPlayer(getPlayerFromData(player1SData),getPlayerFromData(player2Data));

    }

    /**
     * Set saved timers value to timers
     *
     * @param RowTimer1Data timer1 value
     * @param RowTimer2Data timer2 value
     */
    private void setTimers(String RowTimer1Data, String RowTimer2Data){
        long timer1Data = Long.parseLong(RowTimer1Data.substring(3));
        long timer2Data = Long.parseLong(RowTimer2Data.substring(3));
        board.setTimers(timer1Data,timer2Data);
    }

}

