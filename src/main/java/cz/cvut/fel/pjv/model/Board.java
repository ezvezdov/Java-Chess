package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.pieces.PieceType;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;

public class Board {
    private final int BOARD_SIZE = 8;
    Square[][] board;
    BoardReader br;

    public Board() {
        board = new Square[BOARD_SIZE][BOARD_SIZE];
        try {
            br = new BoardReader();
        } catch (IOException e) {
            System.out.println("Error while opening saved game!\n");
        }
        ArrayList piece_data = br.getData();
        System.out.println(piece_data.toString());
        for(int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++){
            ArrayList currentPiece = (ArrayList) piece_data.get(i);
            board[(int) currentPiece.get(2)][(int) currentPiece.get(3)] = new Square((Color)currentPiece.get(0),(PieceType)currentPiece.get(1));
        }
        Color nextMove = (Color) piece_data.get(BOARD_SIZE * BOARD_SIZE);
        System.out.println(nextMove);
    }
}
