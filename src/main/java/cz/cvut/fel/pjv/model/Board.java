package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.pieces.PieceType;
import cz.cvut.fel.pjv.model.utils.BoardReader;
import cz.cvut.fel.pjv.model.utils.BoardWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Board {
    private String START_BOARD_FILE = "/boardData/startBoard.txt";
    private String SAVED_BOARD_FILE = "/boardData/savedBoard.txt";
    private final int BOARD_SIZE = 8;

    private Model model;
    Square[][] board;
    BoardReader br;
    BoardWriter bw = null;

    public Board(Model model) {
        this.model = model;

        board = new Square[BOARD_SIZE][BOARD_SIZE];
        initBoardReader();
        loadBoard();
    }


    private void initBoardReader(){
        br = new BoardReader(this);
        br.setFilePath(START_BOARD_FILE);
    }

    /**
     *
     * @param boardI I coordinate of square in boardX system
     * @param boardJ J coordinate of square in boardX system
     * @return ArrayList wtih square data in format {Color pieceColor, PieceType pieceType, int boardI, int boardJ}
     */
    public ArrayList getSquareStatus(int boardI, int boardJ){
        ArrayList list = new ArrayList();
        list.add(board[boardI][boardJ].pieceColor);
        list.add(board[boardI][boardJ].pieceType);
        list.add(boardI);
        list.add(boardJ);
        return list;
    }

    /**
     *
     * @return ArrayList of ArrayLists in format { {Color pieceColor, PieceType pieceType, int boardI, int boardJ} ...}
     */
    public ArrayList getBoardAsArrayList(){
        ArrayList boardArrayList = new ArrayList();
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                boardArrayList.add(getSquareStatus(i,j));
            }
        }
        return boardArrayList;
    }

    public void setSquare(Color pieceColor, PieceType pieceType, int boardI, int boardJ){
        board[boardI][boardJ] = new Square(pieceColor,pieceType,boardI,boardJ);
    }
    public void setCurrentPlayerColor(Color color){
        model.setCurrentPlayerColor(color);
    }

    public void setIsSinglePlayer(boolean isSingleplayer){
        model.setIsSinglePlayer(isSingleplayer);
    }

    public void loadBoard(){
        br.setData();
    }

    public void loadSavedGame(){
        br.setFilePath(SAVED_BOARD_FILE);
        loadBoard();
    }

    public void saveGame(){
        if(bw == null){
            bw = new BoardWriter();
        }
        bw.setFilePath(SAVED_BOARD_FILE);
        ArrayList list = getBoardAsArrayList();
        System.out.println("BoardSize2 " + list.size());
        bw.setData(list,model.getCurrentPlayerColor(),model.isSinglePlayer());
        System.out.println("Game was saved!");
    }

    public boolean isValidMove(int fromI, int fromJ, int toI, int toJ){
        return board[fromI][fromJ].isValidMove(board,toI,toJ);
//        return board[fromI][fromJ].piece.isValidMove(board, fromI, fromJ, toI, toJ);
    }

    public ArrayList makeMove(int fromI, int fromJ, int toI, int toJ){
        /**
         * Makes move if it's available
         */
        //TODO check is move available

        if( !board[fromI][fromJ].piece.isValidMove(board, fromI, fromJ, toI, toJ) ){
            return new ArrayList();
        }
        ArrayList globalList = new ArrayList();

        board[toI][toJ].setPiece(board[fromI][fromJ]);
        board[fromI][fromJ].setEmpty();


        globalList.add(getSquareStatus(toI,toJ));
        globalList.add(getSquareStatus(fromI,fromJ));

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
