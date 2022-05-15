package cz.cvut.fel.pjv.model.players;

import cz.cvut.fel.pjv.model.Square;
import cz.cvut.fel.pjv.model.players.Player;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer extends Player {

     public ComputerPlayer(Color playerColor) {
        this.name = "Computer";
        this.playerColor = playerColor;
    }

    /**
     *
     * @param BOARD_SIZE size of board
     * @param board
     * @return Arraylist in format {fromI, fromJ, toI, toJ}
     */
    public ArrayList makeMove(int BOARD_SIZE, Square[][] board){

        ArrayList allPossibleMoves = new ArrayList();
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(board[i][j].isOpponent(playerColor)){
                    continue;
                }
                ArrayList availableMoves = board[i][j].getPieceAvailableMoves(board,i,j);
                if(availableMoves.size() == 0){
                    continue;
                }
                allPossibleMoves.add(availableMoves);
                availableMoves.add(i);
                availableMoves.add(j);
            }
        }
        Random random = new Random();
        ArrayList randomPiece = (ArrayList) allPossibleMoves.get(random.nextInt(allPossibleMoves.size()));
        ArrayList random_move = (ArrayList) randomPiece.get(random.nextInt(randomPiece.size()-2));
        int pieceI = (int) randomPiece.get(randomPiece.size()-2);
        int pieceJ = (int) randomPiece.get(randomPiece.size()-1);
        ArrayList move = new ArrayList();
        move.add(pieceI);
        move.add(pieceJ);
        move.add(random_move.get(0));
        move.add(random_move.get(1));

        return move;
    }
}
