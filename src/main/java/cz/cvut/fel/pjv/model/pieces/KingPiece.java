package cz.cvut.fel.pjv.model.pieces;

import cz.cvut.fel.pjv.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class KingPiece extends Piece{
    public KingPiece(Color pieceColor, int boardI, int boardJ) {
        super(pieceColor, boardI, boardJ);
    }

    @Override
    public ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ) {
        ArrayList availableMoves = new ArrayList();
        if(fromI + 1 < BOARD_SIZE && board[fromI+1][fromJ].isEmpty()){
            if(!isUnderAttack(board,fromI+1, fromJ)){
                availableMoves.add(makePair(fromI+1, fromJ));
            }
        }
        if(fromI - 1 >= 0 ){
            if(!isUnderAttack(board,fromI-1, fromJ) && board[fromI-1][fromJ].isEmpty()){
                availableMoves.add(makePair(fromI-1, fromJ));
            }
        }
        if(fromJ + 1 < BOARD_SIZE && board[fromI][fromJ+1].isEmpty()){
            if(!isUnderAttack(board,fromI, fromJ+1)){
                availableMoves.add(makePair(fromI, fromJ+1));
            }
        }
        if(fromJ - 1 >= 0 && board[fromI][fromJ-1].isEmpty()){
            if(!isUnderAttack(board,fromI, fromJ-1)){
                availableMoves.add(makePair(fromI, fromJ-1));
            }
        }
        if(fromI + 1 < BOARD_SIZE && fromJ + 1 < BOARD_SIZE && board[fromI+1][fromJ+1].isEmpty()){
            if(!isUnderAttack(board,fromI+1, fromJ+1)){
                availableMoves.add(makePair(fromI+1, fromJ+1));
            }
        }
        if(fromI + 1 < BOARD_SIZE && fromJ - 1 >= 0 && board[fromI+1][fromJ-1].isEmpty()){
            if(!isUnderAttack(board,fromI+1, fromJ-1)){
                availableMoves.add(makePair(fromI+1, fromJ-1));
            }
        }
        if(fromI - 1 >= 0 && fromJ + 1 < BOARD_SIZE && board[fromI-1][fromJ+1].isEmpty()){
            if(!isUnderAttack(board,fromI-1, fromJ+1)){
                availableMoves.add(makePair(fromI-1, fromJ+1));
            }
        }
        if(fromI - 1 >= 0 && fromJ - 1 >= 0 && board[fromI-1][fromJ-1].isEmpty()){
            if(!isUnderAttack(board,fromI-1, fromJ-1)){
                availableMoves.add(makePair(fromI-1, fromJ-1));
            }
        }

        System.out.println(availableMoves);
        return availableMoves;
    }

    private boolean isUnderAttack(Square[][] board, int toI, int toJ){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                //TODO check piece color
                //TODO check is not king

                if(!board[i][j].isEmpty() && pieceColor != board[i][j].getPieceColor() && board[i][j].getPieceType() != PieceType.KING && board[i][j].isValidMove(board,toI,toJ)){
                    return true;
                }
            }
        }
        return false;
    }
}
