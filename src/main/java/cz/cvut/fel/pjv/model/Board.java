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
    private Square[][] board;
    private BoardReader br;
    private BoardWriter bw;

    public Board(Model model) {
        this.model = model;

        board = new Square[BOARD_SIZE][BOARD_SIZE];
        bw = new BoardWriter();

        initBoardReader();
        loadBoard();
    }


    /**
     * BoardReader initialization and set START_BOARD_FILE to initialize pieces positions on the board
     */
    private void initBoardReader(){
        br = new BoardReader(this);
        br.setFilePath(START_BOARD_FILE);
    }

    /**
     *  Get data about square (piece type on it, it's color, coordinates on board)
     *
     * @param boardI I coordinate of square in boardX system
     * @param boardJ J coordinate of square in boardX system
     * @return ArrayList with square data in format {Color pieceColor, PieceType pieceType, int boardI, int boardJ}
     */
    public ArrayList getSquareStatus(int boardI, int boardJ){
        ArrayList list = new ArrayList();
        list.add(board[boardI][boardJ].getPieceColor());
        list.add(board[boardI][boardJ].getPieceType());
        list.add(boardI);
        list.add(boardJ);
        return list;
    }
    public boolean isKing(int boardI, int boardJ){
        return board[boardI][boardJ].isKing();
    }


    /**
     * Get data about whole board.
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

    public Square[][] getBoard() {
        return board;
    }

    public void setSquare(Color pieceColor, PieceType pieceType, int boardI, int boardJ){
        board[boardI][boardJ] = new Square(pieceColor,pieceType,boardI,boardJ);
    }
    public void setCurrentPlayerColor(Color color){
        model.setCurrentPlayerColor(color);
    }

    public void setGameType(GameType gameType){
        model.setGameType(gameType);
    }

    /**
     *
     */
    private void loadBoard(){
        br.setData();
    }

    /**
     *  Load board from SAVED_BOARD_FILE using BoardReade/BoardWriter annotation format
     */
    public void loadSavedGame(){
        br.setFilePath(SAVED_BOARD_FILE);
        loadBoard();
    }

    /**
     * Save game to SAVED_BOARD_FILE in BoardReade/BoardWriter annotation format
     */
    public void saveGame(){
        bw.setFilePath(SAVED_BOARD_FILE);
        ArrayList list = getBoardAsArrayList();
        System.out.println("BoardSize2 " + list.size());
        bw.setData(list,model.getCurrentPlayerColor(),model.isSinglePlayer());
        System.out.println("Game was saved!");
    }

    /**
     * Return true if move valid
     *
     * @param fromI boardI start coordinate of piece to move
     * @param fromJ boardJ start coordinate of piece to move
     * @param toI   boardI destination coordinate of piece to move
     * @param toJ   boardI destination coordinate of piece to move
     * @return  true if move valid
     */
    public boolean isValidMove(int fromI, int fromJ, int toI, int toJ){
        return board[fromI][fromJ].isValidMove(board,toI,toJ);
    }

    /**
     *  Returns true if king's move was part of roque
     *
     * @param fromI king's start boardI position
     * @param fromJ king's start boardJ position
     * @param toI boardI coordinate where king moves
     * @param toJ boardJ coordinate where king moves
     * @return true if move was a part of roque
     */
    private boolean isRoque(int fromI, int fromJ, int toI, int toJ){
        return board[fromI][fromJ].isKing() && Math.abs(fromI - toI) == 2;
    }

    /**
     *  Make Rook move if king make roque move.
     *
     * @param fromI king's start boardI position
     * @param fromJ king's start boardJ position
     * @param toI   boardI coordinate where king moves
     * @param toJ   boardJ coordinate where king moves
     * @return Arraylist with board changes
     */
    private ArrayList makeRoqueMove(int fromI, int fromJ, int toI, int toJ){
        ArrayList roqueMove = new ArrayList();
        if(fromI - toI == 2){
            roqueMove = makeMove(0,0,3,0);
        }
        else{
            roqueMove = makeMove(7,0,5,0);
        }

        return roqueMove;
    }

    private boolean isPromotion(int fromI, int fromJ, int toI, int toJ){
        if(!board[fromI][fromJ].isPawn()){
            return false;
        }
        return toJ == 0 || toJ == BOARD_SIZE-1;

    }

    private void makePromotion(int fromI, int fromJ, int toI, int toJ){
        System.out.println("PROMOTION");
        board[fromI][fromJ].setQuinInsteadOfPawn();
    }

    public ArrayList makeMove(int fromI, int fromJ, int toI, int toJ){
        /**
         * Makes move if it's available (changes)
         */

        ArrayList globalList = new ArrayList();

        if(isRoque(fromI,fromJ,toI,toJ)){
            globalList = makeRoqueMove(fromI,fromJ,toI,toJ);
        }
        if(isPromotion(fromI,fromJ,toI,toJ)){
            makePromotion(fromI,fromJ,toI,toJ);
        }

        board[fromI][fromJ].pieceHasMoved();

        board[toI][toJ].setPieceFromSquare(board[fromI][fromJ]);
        board[fromI][fromJ].setEmpty();

        globalList.add(getSquareStatus(toI,toJ));
        globalList.add(getSquareStatus(fromI,fromJ));



        return globalList;
    }




    public void printBoard() {
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                String current = "K";
                switch (board[j][i].getPieceType()){
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
