package cz.cvut.fel.pjv.model.pieces;

import cz.cvut.fel.pjv.model.Board;
import cz.cvut.fel.pjv.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PawnPiece extends Piece{
    public PawnPiece(Color pieceColor) {
        this.pieceColor = pieceColor;
    }

    private ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ){
        ArrayList availableMovesList = new ArrayList();
        ArrayList list = new ArrayList();
        if (fromJ+1 < BOARD_SIZE && board[fromI][fromJ+1].getPieceType() == PieceType.EMPTY){
            list.add(fromI);
            list.add(fromJ+1);
        }
        if(list.size() != 0){
            availableMovesList.add(list);
        }

        list = new ArrayList();
        if (fromJ+2 < BOARD_SIZE && board[fromI][fromJ+1].getPieceType() == PieceType.EMPTY){
            list.add(fromI);
            list.add(fromJ+2);
        }
        if(list.size() != 0){
            availableMovesList.add(list);
        }

        list = new ArrayList();
        if(fromI-1 >= 0 && fromJ+1 < BOARD_SIZE && board[fromI-1][fromJ+1].getPieceType() != PieceType.EMPTY){
            list.add(fromI-1);
            list.add(fromJ+1);
        }
        if(list.size() != 0){
            availableMovesList.add(list);
        }

        list = new ArrayList();
        if(fromI+1 < BOARD_SIZE && fromJ+1 < BOARD_SIZE && board[fromI+1][fromJ+1].getPieceType() != PieceType.EMPTY){
            list.add(fromI+1);
            list.add(fromJ+1);
        }
        if(list.size() != 0){
            availableMovesList.add(list);
        }

        return availableMovesList;
    }


    @Override
    public boolean isValidMove(Square[][] board, int fromI, int fromJ, int toI, int toJ) {
        ArrayList availableMovesList = makeAvailableMovesList(board,fromI,fromJ);
        System.out.println(availableMovesList);
        for(int i = 0; i < availableMovesList.size(); i++){
            int indexI = (int)((ArrayList)availableMovesList.get(i)).get(0);
            int indexJ = (int)((ArrayList)availableMovesList.get(i)).get(1);
            if(toI == indexI && toJ == indexJ){
                return true;
            }
        }
        return false;
    }
}
