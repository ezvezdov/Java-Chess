package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.model.pieces.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Square {
    private final int boardI;
    private final int boardJ;
    private Piece piece = null;
    private PieceType pieceType = null;
    private Color pieceColor = null;

    public Square(Color pieceColor, PieceType pieceType, int boardI, int boardJ) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
        this.boardI = boardI;
        this.boardJ = boardJ;

        initPiece();
    }

    /**
     * Initialise piece on this square
     */
    private void initPiece(){
        switch (pieceType){
            case BISHOP:
                piece = new BishopPiece(pieceColor);
                break;
            case KING:
                piece = new KingPiece(pieceColor);
                break;
            case KNIGHT:
                piece = new KnightPiece(pieceColor);
                break;
            case PAWN:
                piece = new PawnPiece(pieceColor,boardI,boardJ);
                break;
            case QUEEN:
                piece = new QueenPiece(pieceColor,boardI,boardJ);
                break;
            case ROOK:
                piece = new RookPiece(pieceColor,boardI,boardJ);
                break;
        }

    }

    /**
     * Get type of piece on this square.
     *
     * @return pieceType
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Get color of piece on this square.
     *
     * @return pieceColor
     */
    public Color getPieceColor() {
        return pieceColor;
    }

    /**
     * Return true if square is empty.
     *
     * @return true if empty, else false.
     */
    public boolean isEmpty(){
        return pieceType == PieceType.EMPTY;
    }

    /**
     * Return true if in square is opponent's piece (another color piece)
     *
     * @param color color of another square piece
     * @return true if piece on this square is another player piece
     */
    public boolean isOpponent(Color color){
        return !isEmpty() && color != getPieceColor();
    }


    /**
     * Return true if square is empty or piece in this square is  opponent's piece
     *
     * @param color color of another square piece
     */
    public boolean isEmptyOrOpponent(Color color){
        if(isEmpty()){
            return true;
        }
        return isOpponent(color);
    }

    /**
     * Return true if King piece placed on this square.
     */
    boolean isKing(){
        return pieceType == PieceType.KING;
    }

    /**
     * Return true if Rook piece placed on this square.
     */
    public boolean isRook(){
        return pieceType == PieceType.ROOK;
    }

    /**
     * Return true if Pawn piece placed on this square.
     */
    public boolean isPawn(){
        return pieceType == PieceType.PAWN;
    }

    /**
     * Set information, that piece has moved, to piece.
     */
    public void pieceHasMoved(){
        piece.setPieceMoved();
    }

    /**
     * Return true if piece was moved or false if in start position.
     */
    public boolean getPieceMoved(){
        return piece.getPieceMoved();
    }

    /**
     * Return true if pawn was moved to 2 squares forward.
     */
    public boolean getTwoSquareMove(){
        return piece.getTwoSquareMove();
    }

    /**
     * Set information that pawn was moved two squares forward to pawn on this square.
     */
    public void setTwoSquareMove(){
        piece.setTwoSquareMove();
    }

    /**
     * Copy piece data from another square to this square.
     * @param sq another square
     */
    void setPieceFromSquare(Square sq){
        pieceColor = sq.pieceColor;
        pieceType = sq.pieceType;
        piece = sq.piece;
    }

    /**
     * Remove piece from this square.
     */
    void setEmpty(){
        pieceType = PieceType.EMPTY;
        pieceColor = Color.WHITE;
        piece = null;
    }

    /**
     * Set quin to this square instead of pawn (promotion)
     */
     void setQuinInsteadOfPawn(){
        piece = new QueenPiece(pieceColor,boardI,boardJ);
        pieceType = PieceType.QUEEN;
    }

    /**
     * Check is move from this.boardI this.boardJ to toI toJ valid.
     *
     * @param board array of Squares (board)
     * @param toI I destination coordinate
     * @param toJ J destination coordinate
     * @return true if move possible.
     */
    public boolean isValidMove(Square[][] board, int toI, int toJ){
        return this.piece.isValidMove(board, this.boardI, this.boardJ, toI, toJ);
    }

    /**
     * Get available moves of piece on this square.
     *
     * @param board array of Squares (board)
     * @param fromI I coordinate move from
     * @param fromJ J coordinate move from
     * @return Arraylist of ArrayLists in format
     *         { { Color pieceColor, PieceType pieceType, boardI, boardJ } ...}
     */
    public ArrayList getPieceAvailableMoves(Square[][] board, int fromI, int fromJ){
        if(board[fromI][fromJ].isEmpty()){
            return new ArrayList();
        }
        return this.piece.makeAvailableMovesList(board,fromI, fromJ);
    }

}
