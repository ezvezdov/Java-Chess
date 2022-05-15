package cz.cvut.fel.pjv.model.pieces;

import cz.cvut.fel.pjv.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BishopPiece extends Piece{
    public BishopPiece(Color pieceColor, int boardI, int boardJ) {
        this.pieceColor = pieceColor;
    }

    @Override
    public ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ) {
        ArrayList availableMovesList = new ArrayList();
        int j = fromJ+1;
        for(int i = fromI+1; i < BOARD_SIZE && j < BOARD_SIZE; i++){
            if(!board[i][j].isEmpty()){
                //add opponent piece to available moves
                if(board[i][j].getPieceColor() != pieceColor){
                    availableMovesList.add(makePair(i,j));
                }
                break;
            }
            availableMovesList.add(makePair(i,j));
            j++;
        }

        j = fromJ-1;
        for(int i = fromI-1; i >= 0 && j >= 0; i--){
            if(!board[i][j].isEmpty()){
                //add opponent piece to available moves
                if(board[i][j].getPieceColor() != pieceColor){
                    availableMovesList.add(makePair(i,j));
                }
                break;
            }
            availableMovesList.add(makePair(i,j));
            j--;
        }

        j = fromJ-1;
        for(int i = fromI+1; i < BOARD_SIZE && j >= 0; i++){
            if(!board[i][j].isEmpty()){
                //add opponent piece to available moves
                if(board[i][j].getPieceColor() != pieceColor){
                    availableMovesList.add(makePair(i,j));
                }
                break;
            }
            availableMovesList.add(makePair(i,j));
            j--;
        }

        j = fromJ+1;
        for(int i = fromI-1; i >= 0 && j < BOARD_SIZE; i--){
            if(!board[i][j].isEmpty()){
                //add opponent piece to available moves
                if(board[i][j].getPieceColor() != pieceColor){
                    availableMovesList.add(makePair(i,j));
                }
                break;
            }
            availableMovesList.add(makePair(i,j));
            j++;
        }
        return availableMovesList;
    }


}
