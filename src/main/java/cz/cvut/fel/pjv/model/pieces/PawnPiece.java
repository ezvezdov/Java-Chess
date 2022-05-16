package cz.cvut.fel.pjv.model.pieces;

import cz.cvut.fel.pjv.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PawnPiece extends Piece{
    boolean isStartPosition = true;
    int startBoardI, startBoardJ;

    public PawnPiece(Color pieceColor, int boardI, int boardJ) {
        super(pieceColor);
        this.startBoardI = boardI;
        this.startBoardJ = boardJ;

        if(boardJ < BOARD_SIZE/2){
            isLeftSide = true;
        }
        else{
            isLeftSide = false;
        }
    }

    @Override
    public ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ){
        if(fromI != startBoardI || fromJ != startBoardJ){
            isStartPosition = false;
        }

        ArrayList<ArrayList> availableMovesList = new ArrayList<>();
        if(isLeftSide) {

            if (fromJ + 1 < BOARD_SIZE && board[fromI][fromJ + 1].isEmpty()) {
                availableMovesList.add(makePair(fromI,fromJ+1));
            }

            if (isStartPosition && fromJ + 2 < BOARD_SIZE && board[fromI][fromJ + 1].isEmpty()&& board[fromI][fromJ + 2].isEmpty()) {
                availableMovesList.add(makePair(fromI,fromJ+2));
            }

            if (fromI - 1 >= 0 && fromJ + 1 < BOARD_SIZE && !board[fromI - 1][fromJ + 1].isEmpty() && board[fromI-1][fromJ+1].isOpponent(pieceColor)){
                availableMovesList.add(makePair(fromI-1,fromJ+1));
            }

            if (fromI + 1 < BOARD_SIZE && fromJ + 1 < BOARD_SIZE && !board[fromI + 1][fromJ + 1].isEmpty() && board[fromI+1][fromJ+1].isOpponent(pieceColor)) {
                availableMovesList.add(makePair(fromI+1, fromJ+1));
            }

            //En passant check to left
            if (fromI - 1 >= 0 && fromJ + 1 < BOARD_SIZE && board[fromI - 1][fromJ + 1].isEmpty() &&
                    !board[fromI-1][fromJ].isEmpty() && board[fromI-1][fromJ].isOpponent(pieceColor) &&
                    board[fromI-1][fromJ].getTwoSquareMove()){
                availableMovesList.add(makePair(fromI-1,fromJ+1));
            }
            if (fromI + 1 < BOARD_SIZE && fromJ + 1 < BOARD_SIZE && board[fromI + 1][fromJ + 1].isEmpty() &&
                    !board[fromI+1][fromJ].isEmpty() && board[fromI+1][fromJ].isOpponent(pieceColor) &&
                    board[fromI+1][fromJ].getTwoSquareMove()) {
                availableMovesList.add(makePair(fromI+1, fromJ+1));
            }

        }
        else{
            if (fromJ - 1 >= 0 && board[fromI][fromJ - 1].isEmpty()) {
                availableMovesList.add(makePair(fromI,fromJ-1));
            }

            if (isStartPosition && fromJ - 2 >= 0 && board[fromI][fromJ - 1].isEmpty() && board[fromI][fromJ - 2].isEmpty()) {
                availableMovesList.add(makePair(fromI,fromJ-2));
            }

            if (fromI + 1 < BOARD_SIZE && fromJ - 1 >= 0 && !board[fromI + 1][fromJ - 1].isEmpty() && board[fromI+1][fromJ-1].isOpponent(pieceColor)) {
                availableMovesList.add(makePair(fromI+1,fromJ-1));
            }

            if (fromI - 1 >= 0 && fromJ - 1 >= 0 && !board[fromI -1][fromJ - 1].isEmpty() && board[fromI-1][fromJ-1].isOpponent(pieceColor)) {
                availableMovesList.add(makePair(fromI-1,fromJ-1));
            }

            //En passant check
            if (fromI + 1 < BOARD_SIZE && fromJ - 1 >= 0 && board[fromI + 1][fromJ - 1].isEmpty() &&
                    !board[fromI+1][fromJ-1].isEmpty() && board[fromI+1][fromJ-1].isOpponent(pieceColor) &&
                    board[fromI+1][fromJ-1].getTwoSquareMove()) {
                availableMovesList.add(makePair(fromI+1,fromJ-1));
            }

            if (fromI - 1 >= 0 && fromJ - 1 >= 0 && board[fromI -1][fromJ - 1].isEmpty() &&
                    !board[fromI -1][fromJ - 1].isEmpty() && board[fromI-1][fromJ-1].isOpponent(pieceColor) &&
                    board[fromI-1][fromJ-1].getTwoSquareMove()) {
                availableMovesList.add(makePair(fromI-1,fromJ-1));
            }
        }

        return availableMovesList;
    }

}
