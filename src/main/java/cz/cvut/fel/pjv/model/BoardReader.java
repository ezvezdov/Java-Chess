package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.pieces.Piece;
import cz.cvut.fel.pjv.model.pieces.PieceType;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


public class BoardReader {
    String filePath;
    Scanner scanner;

    public BoardReader() throws IOException {
        String fileName = "startBoard.txt";
        File file = null;
        try {
            file = getFileFromResource(fileName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(file);
    }
    /*
    Returns data about square at format [COLOR,PieceType,coordinateA-Z,coordinate1-8]
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
        else if(pieceRowData.charAt(0) == 'E'){
            list.add(PieceType.EMPTY);
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



    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("Error: " + fileName +"not found! ");
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            return new File(resource.toURI());
        }

    }

    public BoardReader(String filePath) {
    }

    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }
}
