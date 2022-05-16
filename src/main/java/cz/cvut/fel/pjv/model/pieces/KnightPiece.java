package cz.cvut.fel.pjv.model.pieces;

import cz.cvut.fel.pjv.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class KnightPiece extends Piece{
    public KnightPiece(Color pieceColor) {
        super(pieceColor);
    }

    @Override
    public ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ) {
        ArrayList<ArrayList> availableMovesList = new ArrayList<>();
        if(fromI - 2 >= 0 && fromJ + 1 < BOARD_SIZE && board[fromI-2][fromJ+1].isEmptyOrOpponent(pieceColor)){
            availableMovesList.add(makePair(fromI-2,fromJ+1));
        }
        if(fromI - 2 >= 0 && fromJ - 1 >= 0 && board[fromI-2][fromJ-1].isEmptyOrOpponent(pieceColor)){
            availableMovesList.add(makePair(fromI-2,fromJ-1));
        }
        if(fromI - 1 >= 0 && fromJ + 2 < BOARD_SIZE && board[fromI-1][fromJ+2].isEmptyOrOpponent(pieceColor)){
            availableMovesList.add(makePair(fromI-1,fromJ+2));
        }
        if(fromI - 1 >= 0 && fromJ - 2 >= 0 && board[fromI-1][fromJ-2].isEmptyOrOpponent(pieceColor)){
            availableMovesList.add(makePair(fromI-1,fromJ-2));
        }

        if(fromI + 2 < BOARD_SIZE && fromJ + 1 < BOARD_SIZE && board[fromI+2][fromJ+1].isEmptyOrOpponent(pieceColor)){
            availableMovesList.add(makePair(fromI+2,fromJ+1));
        }
        if(fromI + 2 < BOARD_SIZE && fromJ - 1 >= 0 && board[fromI+2][fromJ-1].isEmptyOrOpponent(pieceColor)){
            availableMovesList.add(makePair(fromI+2,fromJ-1));
        }
        if(fromI + 1 < BOARD_SIZE && fromJ + 2 < BOARD_SIZE && board[fromI+1][fromJ+2].isEmptyOrOpponent(pieceColor)){
            availableMovesList.add(makePair(fromI+1,fromJ+2));
        }
        if(fromI + 1 < BOARD_SIZE && fromJ - 2 >= 0 && board[fromI+1][fromJ-2].isEmptyOrOpponent(pieceColor)){
            availableMovesList.add(makePair(fromI+1,fromJ-2));
        }

        return availableMovesList;
    }
}
