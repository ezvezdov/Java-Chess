package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.pieces.PieceType;
import cz.cvut.fel.pjv.model.players.Player;
import cz.cvut.fel.pjv.model.utils.BoardReader;
import cz.cvut.fel.pjv.model.utils.BoardWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Board {
    private final String START_BOARD_FILE = "/boardData/startBoard.txt";
    private final String SAVED_BOARD_FILE = "/boardData/savedBoard.txt";
    private final int BOARD_SIZE = 8;

    private final Model model;
    private final Square[][] board;
    private BoardReader br;
    private final BoardWriter bw;

    public Board(Model model) {
        this.model = model;

        board = new Square[BOARD_SIZE][BOARD_SIZE];
        bw = new BoardWriter();

        initBoardReader();
        loadBoard();
    }

    public Square[][] getBoard() {
        return board;
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

    /**
     * Return true if King piece placed on this square.
     */
    public boolean isKing(int boardI, int boardJ){
        return board[boardI][boardJ].isKing();
    }

    /**
     * Return true if square is empty.
     *
     * @param boardI I coordinate of square
     * @param  boardJ J coorinate of square
     * @return true if empty, else false.
     */
    public boolean isEmptySquare(int boardI, int boardJ){
        return board[boardI][boardJ].isEmpty();
    }

    /**
     * Return true if in square is opponent's piece (another color piece)
     *
     * @param boardI I coordinate of square
     * @param  boardJ J coorinate of square
     * @param currentPlayerColor another piece color
     * @return true if piece on this square is another player piece
     */
    public boolean isOpponentPiece(int boardI, int boardJ, Color currentPlayerColor){
        return board[boardI][boardJ].isOpponent(currentPlayerColor);
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


    /**
     * Set new square
     *
     * @param pieceColor piece's color
     * @param pieceType piece to set on square
     * @param boardI I coordinate of square
     * @param boardJ J coordinate of square
     */
    public void setSquare(Color pieceColor, PieceType pieceType, int boardI, int boardJ){
        board[boardI][boardJ] = new Square(pieceColor,pieceType,boardI,boardJ);
    }

    /**
     *
     * Set player, who should make next move using color of his pieces.
     *
     * @param color color of piece, that player play
     */
    public void setCurrentPlayerColor(Color color){
        model.setCurrentPlayerColor(color);
    }

    /**
     * Set game type to model.
     * @param gameType type of game
     */
    public void setGameType(GameType gameType){
        model.setGameType(gameType);
    }

    /**
     * Set players data.
     *
     * @param player1 player1 Player instance
     * @param player2 player2 Player instance
     */
    public void setPlayer(Player player1, Player player2){
        model.setPlayers(player1,player2);
    }

    /**
     * Set new values on timer.
     *
     * @param timer1 timer1 value
     * @param timer2 timer2 value
     */
    public void setTimers(long timer1, long timer2){
        model.setTimers(timer1,timer2);
    }
    /**
     * Load board date from file.
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
        bw.setData(list,model.getCurrentPlayerColor(),model.getGameType(),model.getPlayer1(),model.getPlayer2());
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
            roqueMove = makeMove(fromI-4,fromJ,fromI-1,toJ);
        }
        else{
            roqueMove = makeMove(fromI+3,fromJ,fromI+1,toJ);
        }

        return roqueMove;
    }

    /**
     *
     * Check if current move was promotion.
     *
     * @param fromI piece I start coordinate
     * @param fromJ piece J start coordinate
     * @param toI piece I destination coordinate
     * @param toJ piece J destination coordinate
     * @return true if move was promotion.
     */
    private boolean isPromotion(int fromI, int fromJ, int toI, int toJ){
        if(!board[fromI][fromJ].isPawn()){
            return false;
        }
        return toJ == 0 || toJ == BOARD_SIZE-1;

    }

    /**
     * Make promotion, change pawn to queen.
     *
     * @param fromI piece I start coordinate
     * @param fromJ piece J start coordinate
     * @param toI piece I destination coordinate
     * @param toJ piece J destination coordinate
     */
    private void makePromotion(int fromI, int fromJ, int toI, int toJ){
        System.out.println("PROMOTION");
        board[fromI][fromJ].setQuinInsteadOfPawn();
    }

    /**
     * Check if pawn moved two squares forward.
     *
     * @param fromI piece I start coordinate
     * @param fromJ piece J start coordinate
     * @param toI piece I destination coordinate
     * @param toJ piece J destination coordinate
     * @return true if pawn moved two squares forward.
     */
    private boolean isTwoSquareMove(int fromI, int fromJ, int toI, int toJ){
        return board[fromI][fromJ].isPawn() && Math.abs(fromJ - toJ) == 2;
    }

    /**
     * Set TwoSquareMove (pawn moved two squares forward)
     *
     * @param fromI piece I start coordinate
     * @param fromJ piece J start coordinate
     * @param toI piece I destination coordinate
     * @param toJ piece J destination coordinate
     */
    private void setTwoSquareMove(int fromI, int fromJ, int toI, int toJ){
        board[fromI][fromJ].setTwoSquareMove();
    }

    /**
     *
     * Check is current move en Passant.
     *
     * @param fromI piece I start coordinate
     * @param fromJ piece J start coordinate
     * @param toI piece I destination coordinate
     * @param toJ piece J destination coordinate
     * @return true is current move is en Passant.
     */
    private boolean isEnPassant(int fromI, int fromJ, int toI, int toJ){
        if(!board[fromI][fromJ].isPawn()){
            return false;
        }
        return Math.abs(fromI - toI) == 1 && Math.abs(fromJ - toJ) == 1 && board[toI][toJ].isEmpty();
    }

    /**
     * Make en Passant move (capture opponent's pawn)
     *
     * @param fromI piece I start coordinate
     * @param fromJ piece J start coordinate
     * @param toI piece I destination coordinate
     * @param toJ piece J destination coordinate
     * @return ArrayList with changes in format
     *         { { Color pieceColor, PieceType pieceType, boardI, boardJ } ...}
     */
    private ArrayList makeEnPassant(int fromI, int fromJ, int toI, int toJ){
        ArrayList enPassantMove;
        if(fromI < toI){
            board[fromI+1][fromJ].setEmpty();
            enPassantMove = getSquareStatus(fromI+1,fromJ);
        }
        else{
            board[fromI-1][fromJ].setEmpty();
            enPassantMove = getSquareStatus(fromI-1,fromJ);
        }
        return enPassantMove;
    }

    /**
     * Make move (check roque, check en Passant, check promotion)
     *
     * @param fromI piece I start coordinate
     * @param fromJ piece J start coordinate
     * @param toI piece I destination coordinate
     * @param toJ piece J destination coordinate
     * @return ArrayList with changes in format
     *         { { Color pieceColor, PieceType pieceType, boardI, boardJ } ...}
     */
    public ArrayList makeMove(int fromI, int fromJ, int toI, int toJ){
        /**
         * Makes move if it's available (changes)
         */

        ArrayList globalList = new ArrayList();

        // Check roque
        if(isRoque(fromI,fromJ,toI,toJ)){
            globalList = makeRoqueMove(fromI,fromJ,toI,toJ);
        }
        // Check promotion
        if(isPromotion(fromI,fromJ,toI,toJ)){
            makePromotion(fromI,fromJ,toI,toJ);
        }
        if(isTwoSquareMove(fromI,fromJ,toI,toJ)){
            setTwoSquareMove(fromI,fromJ,toI,toJ);
        }
        // Check en Passant
        if(isEnPassant(fromI,fromJ,toI,toJ)){
            globalList.add(makeEnPassant(fromI,fromJ,toI,toJ));
        }

        board[toI][toJ].setPieceFromSquare(board[fromI][fromJ]);
        board[fromI][fromJ].setEmpty();

        board[toI][toJ].pieceHasMoved();

        globalList.add(getSquareStatus(toI,toJ));
        globalList.add(getSquareStatus(fromI,fromJ));

        return globalList;
    }


    /**
     * DEBUG PRINT BOARD
     */
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
