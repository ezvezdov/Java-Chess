package cz.cvut.fel.pjv.model.utils;

import cz.cvut.fel.pjv.model.pieces.PieceType;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BoardReader extends FilesIO{

    /**
     * Receive layout file path and ready to give new data from getData()
     *
     * @param filePath path of file with board annotation
     */
    public BoardReader(String filePath){

        setScanner(filePath);
    }

    /**
     * Receive new layout file path and ready to give new data from getData()
     *
     * @param filePath path of file with board annotation
     */
    public void changeBoardLayout(String filePath){
        setScanner(filePath);
    }

    /**
     * Get information of pieces placed in board from board annotation file.
     *
     @return  Arraylist of ArrayLists data about squares at format { {Color pieceColor, PieceType pieceType,
        char coordinateA-Z, char coordinate1-8} ...}
    */
    public ArrayList getData(){
        Color currentPlayerMove = null;

        ArrayList globalList = new ArrayList();
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
                    currentPlayerMove = curPieceData.charAt(1) == 'w' ? Color.WHITE : Color.BLACK;
                }
                else if(curPieceData.length() == 4){
                    ArrayList list = getPieceData(curPieceData);
                    if(list == null){
                        continue;
                    }
                    globalList.add(list);
                }
            }
        }
        if(currentPlayerMove == null){
            return null;
        }
        globalList.add(currentPlayerMove);
        return globalList;
    }

    /**
     * Transform string (row) piece data to ArrayList
     *
     * @param pieceRowData String in format cTaC, c - piece color {w, b}, T - piece type {B, K, N, P, Q, R, E}
     *                     a - coordinate in letter coordination system {a,b,c,d,e,f,g,h}
     *                     C - int coordinate {1,2,3,4,5,6,7,8}
     * @return ArrayList in format { {Color pieceColor, PieceType pieceType, int boardI, int boardJ} ...}
     */
    private ArrayList getPieceData(String pieceRowData){
        ArrayList list = new ArrayList();
        if(pieceRowData.charAt(0) == 'w'){
            list.add(Color.WHITE);
        }
        else if(pieceRowData.charAt(0) == 'b'){
            list.add(Color.BLACK);
        }
        else {
            return null;
        }

        if(pieceRowData.charAt(1) == 'B'){
            list.add(PieceType.BISHOP);
        }
        else if(pieceRowData.charAt(1) == 'K') {
            list.add(PieceType.KING);
        }
        else if(pieceRowData.charAt(1) == 'N') {
            list.add(PieceType.KNIGHT);
        }
        else if(pieceRowData.charAt(1) == 'P') {
            list.add(PieceType.PAWN);
        }
        else if(pieceRowData.charAt(1) == 'Q') {
            list.add(PieceType.QUEEN);
        }
        else if(pieceRowData.charAt(1) == 'R') {
            list.add(PieceType.ROOK);
        }
        else if(pieceRowData.charAt(1) == 'E'){
            list.add(PieceType.EMPTY);
        }
        else {
            return null;
        }

        if (pieceRowData.charAt(2) >= 'a' && pieceRowData.charAt(2) <= 'h'){
            list.add(pieceRowData.charAt(2) - 'a');

        }
        else{
            return null;
        }

        if(pieceRowData.charAt(3) >= '1' && pieceRowData.charAt(3) <= '8'){
            list.add(pieceRowData.charAt(3) - '1');
        }
        else{
            return null;
        }

        return list;

    }
}

