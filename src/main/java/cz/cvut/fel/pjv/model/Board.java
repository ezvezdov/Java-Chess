package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.pieces.PieceType;
import cz.cvut.fel.pjv.model.utils.BoardReader;
import cz.cvut.fel.pjv.model.utils.BoardWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Board {
    private final int BOARD_SIZE = 8;
    Square[][] board;
    BoardReader br = null;
    BoardWriter bw = null;

    private String START_BOARD_FILE = "/boardData/startBoard.txt";
    private String SAVED_BOARD_FILE = "/boardData/savedBoard.txt";

    private Color nextMove;

    public Board() {
        board = new Square[BOARD_SIZE][BOARD_SIZE];
        br = new BoardReader(START_BOARD_FILE);

        loadBoard();
    }

    public Color getNextMove(){
        return nextMove;
    }

    public ArrayList getSquareStatus(int x, int y){
        /**
         * @return ArrayList wtih square data in format {Color, PieceType}
         */
        ArrayList list = new ArrayList();
        list.add(board[x][y].pieceColor);
        list.add(board[x][y].pieceType);
        return list;
    }

    public ArrayList getBoardAsArrayList(){
        ArrayList boardArrayList = new ArrayList();
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                ArrayList currentSquare = new ArrayList();
                currentSquare.add(board[i][j].pieceColor);
                currentSquare.add(board[i][j].pieceType);
                currentSquare.add(i);
                currentSquare.add(j);
                boardArrayList.add(currentSquare);
            }
        }
        return boardArrayList;
    }

    public void loadBoard(){
        ArrayList boardData = br.getData();
        System.out.println(boardData.toString());
        System.out.println("BoardSize " + boardData.size());
        for(int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++){
            ArrayList currentPiece = (ArrayList) boardData.get(i);
            board[(int) currentPiece.get(2)][(int) currentPiece.get(3)] = new Square((Color)currentPiece.get(0),(PieceType)currentPiece.get(1),(int) currentPiece.get(2),(int) currentPiece.get(3));
        }
        nextMove = (Color) boardData.get(BOARD_SIZE * BOARD_SIZE);
    }

    public void loadSavedGame(){
        br.changeBoardLayout(SAVED_BOARD_FILE);
        loadBoard();
    }

    public void saveGame(){
        if(bw == null){
            bw = new BoardWriter(SAVED_BOARD_FILE);
        }
        ArrayList list = getBoardAsArrayList();
        System.out.println("BoardSize2 " + list.size());
        bw.setData(list,nextMove);
        System.out.println("Game was saved!");
    }

    public ArrayList makeMove(int fromI, int fromJ, int toI, int toJ){
        /**
         * Makes move if it's available
         */
        //TODO check is move available

        if( !board[fromI][fromJ].piece.isValidMove(board, fromI, fromJ, toI, toJ) ){
            return new ArrayList();
        }
        board[toI][toJ].setPiece(board[fromI][fromJ]);
        board[fromI][fromJ].setEmpty();
        ArrayList globalList = new ArrayList();
        ArrayList list1 = new ArrayList(), list2 = new ArrayList();
        list1.add(board[toI][toJ].pieceColor);
        list1.add(board[toI][toJ].pieceType);
        list1.add(toI);
        list1.add(toJ);

        list2.add(board[fromI][fromJ].pieceColor);
        list2.add(board[fromI][fromJ].pieceType);
        list2.add(fromI);
        list2.add(fromJ);
        globalList.add(list1);
        globalList.add(list2);

        return globalList;

    }










    public void printBoard() {
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                String current = "K";
                switch (board[j][i].pieceType){
                    case BISHOP:
                        current = "B ";
                        break;
                    case KING:
                        current = "K ";
                        break;
                    case KNIGHT:
                        current = "N ";
                        break;
                    case EMPTY:
                        current = "E ";
                        break;
                    case PAWN:
                        current = "P ";
                        break;
                    case ROOK:
                        current = "R ";
                        break;
                    case QUEEN:
                        current = "Q ";
                        break;
                }
                System.out.print(current);

            }
            System.out.println();
        }
        System.out.println();
    }
}
