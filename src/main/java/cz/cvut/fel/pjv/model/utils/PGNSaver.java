package cz.cvut.fel.pjv.model.utils;

import cz.cvut.fel.pjv.model.pieces.PieceType;

import java.util.ArrayList;

public class PGNSaver extends FilesIO{
    String PGN_DIRECTORY_PATH = "/Saved PGN games";
    ArrayList moves;

    /**
     *  Construct PGNSaver object to manage PGN files.
     *
     * @param filePath
     */
    public PGNSaver(String filePath){
        setPrintStreamForUser(System.getProperty("user.dir") + PGN_DIRECTORY_PATH);
    }

    /**
     * Save move to PGN
     *
     *@param from Status of Square of start move piece location, format {Color pieceColor, PieceType pieceType,
     *            int boardI, int boardJ}.
     *@param to Status of Square of end move piece location, format {Color pieceColor, PieceType pieceType,
     *          int boardI, int boardJ}.
     */
    public void addMove(ArrayList from, ArrayList to){

        PieceType fromPieceType = (PieceType) from.get(1);
        PieceType toPieceType = (PieceType) to.get(1);

        int toI = (int) to.get(2);
        int toJ = (int) to.get(3);
        boolean isCapture = false;
        if(toPieceType != PieceType.EMPTY){
            isCapture = true;
        }

        saveMoveAsString(fromPieceType,toI,toJ,isCapture);
    }


    /**
     * Save move as String to ArrayList moves
     *
     * @param pieceType type of moved piece
     * @param coordinateI coordinate boardI to which piece was moved
     * @param coordinateJ coordinate boardJ to which piece was moved
     * @param isCapture is piece capture another piece
     */
    public void saveMoveAsString(PieceType pieceType, int coordinateI, int coordinateJ, boolean isCapture){
        //String data to print
        String pieceTypeString = "B";
        char coordinateIChar = (char) ('a' + coordinateI);
        String captureString = isCapture ? "x" : "";
        coordinateJ++; //Coordinate starts from 1

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
                //pieceTypeString = "";
                break;
            case QUEEN:
                pieceTypeString = "Q";
                break;
            case ROOK:
                pieceTypeString = "R";
                break;
        }
        moves.add(pieceTypeString + captureString + coordinateIChar + coordinateJ);
    }

    public void savePGN(){
        int counter = 1;
        int lineSize = 0;
        int maxLineSize = 255;
        for(int i = 0; i < moves.size(); i++) {
            //print number of move
            if (i % 2 == 0) {
                printWriter.print(counter + ". ");
                counter++;
                lineSize+=3;
            }

            printWriter.print(moves.get(i));
            lineSize+= ((String) moves.get(i)).length();

            if(lineSize >= maxLineSize){
                printWriter.print("\n");
                lineSize = 0;
            }
        }
    }
}


