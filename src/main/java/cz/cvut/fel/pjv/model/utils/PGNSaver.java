package cz.cvut.fel.pjv.model.utils;

import cz.cvut.fel.pjv.model.players.Player;
import cz.cvut.fel.pjv.model.pieces.PieceType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PGNSaver extends FilesIO{
    private final String PGN_DIRECTORY_PATH = "/Saved PGN games/";
    private ArrayList moves = new ArrayList();


    public PGNSaver() {
        createDirectory(System.getProperty("user.dir") + PGN_DIRECTORY_PATH);
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

        int fromI = (int) from.get(2);
        int fromJ = (int) from.get(3);
        int toI = (int) to.get(2);
        int toJ = (int) to.get(3);
        boolean isCapture = toPieceType != PieceType.EMPTY;

        saveMoveAsString(fromPieceType,fromI,fromJ,toI,toJ,isCapture);
    }


    /**
     * Save move as String to ArrayList moves
     *
     * @param pieceType type of moved piece
     * @param toI coordinate boardI to which piece was moved
     * @param toJ coordinate boardJ to which piece was moved
     * @param isCapture is piece capture another piece
     */
    private void saveMoveAsString(PieceType pieceType, int fromI, int fromJ, int toI, int toJ, boolean isCapture){
        //String data to print
        String pieceTypeString = "B";
        char toIChar = (char) ('a' + toI);
        char fromIChar = (char) ('a' + fromI);
        toJ++; //Coordinate starts from 1
        fromJ++;

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
        if(!isCapture){
            moves.add(pieceTypeString + toIChar + toJ);
        }
        else{
            moves.add(fromIChar + "x" + pieceTypeString + toIChar + toJ);
        }

    }


    /**
     * Print tag(event, site, date and player data)
     *
     * @param player1 player1, Player instance
     * @param player2 player2, Player instance
     */
    private void printTags(Player player1, Player player2){
        printWriter.println("[Event \"Javafx Chess Game\"]");
        printWriter.println("[Site \"Prague, Czech Republic\"]");
        printWriter.println("[Date \"" + new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) + "\"]");
        printWriter.println("[" + player1.getPlayerColorAsString() + " \"" + player1.getPlayerName() + "\"]");
        printWriter.println("[" + player2.getPlayerColorAsString() + " \"" + player2.getPlayerName() + "\"]");

    }

    /**
     *
     * Print all saved moves.
     *
     */
    private void printMovesData(){
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
            printWriter.print(" ");

            lineSize+= ((String) moves.get(i)).length();

            if(lineSize >= maxLineSize){
                printWriter.print("\n");
                lineSize = 0;
            }
        }
    }

    /**
     * Generate PGN file name with current date and time.
     *
     * @return new PGN filename
     */
    private String generatePGNFileName(){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(Calendar.getInstance().getTime());
        return System.getProperty("user.dir") + PGN_DIRECTORY_PATH + timeStamp + ".pgn";
    }

    /**
     * Save all data to file .pgn
     *
     * @param player1 player1, Player instance
     * @param player2 player2, Player instance
     */
    public void savePGN(Player player1, Player player2){
        //Generate file and directory
        String fileName = generatePGNFileName();
        setPrintStreamForUser(fileName);

        printTags(player1, player2);
        printMovesData();

        closePrintStream();
    }
}


