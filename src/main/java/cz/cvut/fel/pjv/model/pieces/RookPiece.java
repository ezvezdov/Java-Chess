package cz.cvut.fel.pjv.model.pieces;

import cz.cvut.fel.pjv.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class RookPiece extends Piece{
    public RookPiece(Color pieceColor, int boardI, int boardJ) {
        super(pieceColor, boardI, boardJ);
    }

    @Override
    public ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ) {
        ArrayList availableMovesList = new ArrayList();
        for(int i = fromI+1; i < BOARD_SIZE; i++){
            if(!board[i][fromJ].isEmpty()){
                //add opponent piece to available moves
                if(board[i][fromJ].getPieceColor() != pieceColor){
                    availableMovesList.add(makePair(i,fromJ));
                }
                break;
            }

            availableMovesList.add(makePair(i,fromJ));
        }

        for(int i = fromI-1; i >= 0; i--){
            if(!board[i][fromJ].isEmpty()){
                //add opponent piece to available moves
                if(board[i][fromJ].getPieceColor() != pieceColor){
                    availableMovesList.add(makePair(i,fromJ));
                }
                break;
            }
            availableMovesList.add(makePair(i,fromJ));
        }

        for(int j = fromJ+1; j < BOARD_SIZE; j++){
            if(!board[fromI][j].isEmpty()){
                //add opponent piece to available moves
                if(board[fromI][j].getPieceColor() != pieceColor){
                    availableMovesList.add(makePair(fromI,j));
                }
                break;
            }
            availableMovesList.add(makePair(fromI,j));
        }
        for(int j = fromJ-1; j >= 0; j--){
            if(!board[fromI][j].isEmpty()){
                //add opponent piece to available moves
                if(board[fromI][j].getPieceColor() != pieceColor){
                    availableMovesList.add(makePair(fromI,j));
                }
                break;
            }
            availableMovesList.add(makePair(fromI,j));
        }
        return availableMovesList;
    }

}
