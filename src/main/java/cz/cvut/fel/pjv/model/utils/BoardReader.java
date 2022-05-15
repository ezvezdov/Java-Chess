package cz.cvut.fel.pjv.model.utils;

import cz.cvut.fel.pjv.model.Board;
import cz.cvut.fel.pjv.model.pieces.PieceType;
import javafx.scene.paint.Color;

public class BoardReader extends FilesIO{

    private Board board;
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

        while (scanner.hasNext()){
            String line = scanner.nextLine();

            //commented parts in boardStatusAnnotation
            if(line.length() >= 1  && line.charAt(0) == '#'){
                continue;
            }

            String[] rowPiecesData = line.split(" ");
            for(int i = 0; i < rowPiecesData.length; i++){

                String curPieceData = rowPiecesData[i];
                if(curPieceData.length() >= 1 && curPieceData.charAt(0) == '#'){
                    break;
                }
                else if(curPieceData.length() >= 2 && curPieceData.charAt(0) == '@' ){
                    setCurrentMoveColor(curPieceData.charAt(1));
                }
                else if(curPieceData.length() >= 2 && curPieceData.charAt(0) == '$' ){
                    setIsSinglePlayer(curPieceData.charAt(1));
                }
                else if(curPieceData.length() == 4){
                    setPiece(curPieceData);
                }
            }
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
     * Set isSinglePlayer variable
     *
     * @param isSinglePlayer
     */
    private void setIsSinglePlayer(char isSinglePlayer){
        board.setIsSinglePlayer(isSinglePlayer == 's');
    }

}

