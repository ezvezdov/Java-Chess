package cz.cvut.fel.pjv.model.utils;

import cz.cvut.fel.pjv.model.pieces.Piece;
import cz.cvut.fel.pjv.model.pieces.PieceType;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BoardWriter {
    String filePath;
    File file;
    PrintWriter printWriter;


    public BoardWriter(String filePath){
        /*
            Receive layout file path and ready to give new data from getData()
        */
        setPrintStream(filePath);
    }


    private void setPrintStream(String filePath){
        this.file = getFileFromResource(filePath);
        try {
            printWriter = new PrintWriter(this.file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void setData(ArrayList boardData, Color nextMove){
        /**
         * @param  Arraylist of ArrayLists data about square at format { {COLOR,PieceType,coordinateA-Z,coordinate1-8} ...}
         */

        for(int i = 0; i < boardData.size(); i++){
            ArrayList currentPieceData = (ArrayList) boardData.get(i);
            printPiece(currentPieceData);
        }

        String nextMoveString = nextMove == Color.BLACK ? "b" : "w";

        printWriter.print("@" + nextMoveString);
        printWriter.close();
    }

    public void printPiece(ArrayList currentPieceData){
        //Raw data
        Color pieceColor = (Color) currentPieceData.get(0);
        PieceType pieceType = (PieceType) currentPieceData.get(1);
        int coordinateI = (int) currentPieceData.get(2);
        int coordinateJ = (int) currentPieceData.get(3) + 1;

        //String data to print
        String pieceColorString = pieceColor == Color.BLACK ? "b" : "w";
        String pieceTypeString = "B";
        char coordinateIChar = (char) ('a' + coordinateI);

        switch (pieceType){
            case BISHOP:
                pieceTypeString = "B";
                break;
            case EMPTY:
                pieceTypeString = "E";
                break;
            case KING:
                pieceTypeString = "K";
                break;
            case KNIGHT:
                pieceTypeString = "N";
                break;
            case PAWN:
                pieceTypeString = "P";
                break;
            case QUEEN:
                pieceTypeString = "Q";
                break;
            case ROOK:
                pieceTypeString = "R";
                break;
        }
        printWriter.print(pieceColorString + pieceTypeString + coordinateIChar + coordinateJ + " ");

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
