package cz.cvut.fel.pjv.model.utils;

import cz.cvut.fel.pjv.model.pieces.PieceType;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BoardWriter extends FilesIO{

    private String filePath;


    /**
     *
     * @param filePath path of file for saving game
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Print data of current board status to file.
     *
     * @param boardData Representation of board as an ArrayList of ArrayLists
     *                  in format { {Color pieceColor, PieceType pieceType, int boardI, int boardJ} ...}
     * @param currentMove Color of next move player
     */
    public void setData(ArrayList boardData, Color currentMove, boolean isSinglePlayer){
        setPrintStream(filePath);

        for(int i = 0; i < boardData.size(); i++){
            ArrayList currentPieceData = (ArrayList) boardData.get(i);
            printPiece(currentPieceData);
        }

        String nextMoveString = currentMove == Color.BLACK ? "b" : "w";
        printWriter.println("@" + nextMoveString);

        String isSinglePlayerString = isSinglePlayer ? "s" : "m";
        printWriter.println("$" + isSinglePlayerString);

        closePrintStream();
    }

    /**
     * Transform piece data to string and print it to file.
     *
     * @param currentPieceData data of current piece in format
     *                         { {Color pieceColor, PieceType pieceType, int boardI, int boardJ} ...}
     */
    private void printPiece(ArrayList currentPieceData){
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


}
