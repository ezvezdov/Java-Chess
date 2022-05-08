package cz.cvut.fel.pjv.model.utils;

import cz.cvut.fel.pjv.model.pieces.PieceType;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BoardReader {
//    String filePath;
    Scanner scanner;

    public BoardReader(String filePath){
        /*
            Receive layout file path and ready to give new data from getData()
        */
        setScanner(filePath);
    }

    public void changeBoardLayout(String filePath){
        /*
            Receive new layout file path and ready to give new data from getData()
        */
        setScanner(filePath);
    }

    private void setScanner(String filePath){
        File file = getFileFromResource(filePath);
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     @return  Arraylist of ArrayLists data about square at format { {COLOR,PieceType,coordinateA-Z,coordinate1-8} ...}
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



    private File getFileFromResource(String fileName) throws NullPointerException {
        try {
            String str = getClass().getResource(fileName).getFile();
            File file = new File(str);
            return file;
        } catch (NullPointerException e) {
            System.out.println("Board annotation isn't available!");
            e.printStackTrace();
        }
        return null;
    }
}
