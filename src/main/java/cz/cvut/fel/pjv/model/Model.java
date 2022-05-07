package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.Controller;
import cz.cvut.fel.pjv.model.pieces.Piece;
import cz.cvut.fel.pjv.model.pieces.PieceType;
import cz.cvut.fel.pjv.view.View;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Model {
    private Controller ctrl;

    boolean isGame = true;
    Board board = null;

    private Color currentPlayerColor;
    private Boolean isSelectedPiece = false;
    private int selectedPieceI, selectedPieceJ;

    public Model(Controller controller) {
        ctrl = controller;
    }


    public void startGame(){
        resetBoard();

    }
    private void resetBoard(){
        board = new Board();
        currentPlayerColor = board.getNextMove();
    }

    public void squareWasClicked(int boardI, int boardJ){

        System.out.println(isCurrentPlayersPiece(boardI,boardJ));
//        if(!isSelectedPiece && isEmptySquare(boardI,boardJ)){
//            unselectPiece();
//        }
        // make move
        if(isSelectedPiece && isEmptySquare(boardI,boardJ) || isSelectedPiece && !isCurrentPlayersPiece(boardI,boardJ)){
            System.out.println("Move has done!");
            ArrayList changesList;
            changesList = board.makeMove(selectedPieceI,selectedPieceJ,boardI,boardJ);
            if(changesList.size() != 0){
                currentPlayerColor = currentPlayerColor == Color.BLACK ? Color.WHITE : Color.BLACK;
            }

            unselectPiece();


            ctrl.updateBoard(changesList);


        }
        //select piece
        else if(!isSelectedPiece && !isEmptySquare(boardI,boardJ) && isCurrentPlayersPiece(boardI,boardJ)){
            selectPiece(boardI, boardJ);
        }
        //reselect piece
        else if(isSelectedPiece && !isEmptySquare(boardI,boardJ) && isCurrentPlayersPiece(boardI, boardJ)){
            unselectPiece();
            selectPiece(boardI, boardJ);
        }
        board.printBoard();

    }

    private void selectPiece(int boardI, int boardJ){
        System.out.println("Piece have selected!");
        isSelectedPiece = true;
        selectedPieceI = boardI;
        selectedPieceJ = boardJ;
        ctrl.selectPiece(boardI,boardJ);
    }

    private void unselectPiece(){
        System.out.println("Seleceted piece is clear!");
        ctrl.selectPiece(selectedPieceI,selectedPieceJ);
        isSelectedPiece = false;
        selectedPieceI = -1;
        selectedPieceJ = -1;
    }

    private boolean isCurrentPlayersPiece(int boardI, int indexJ){
        ArrayList list = board.getSquareStatus(boardI,indexJ);
        Color pieceColor = (Color) list.get(0);
        PieceType pieceType = (PieceType) list.get(1);
        if(pieceColor != currentPlayerColor || pieceType == PieceType.EMPTY){
            return false;
        }
        return true;
    }

    private boolean isEmptySquare(int boardI, int boardJ){
        ArrayList list = board.getSquareStatus(boardI,boardJ);
        PieceType pieceType = (PieceType) list.get(1);
        if(pieceType == PieceType.EMPTY){
            return true;
        }
        return false;
    }

    public ArrayList getBoardAsArrayList(){
        return board.getBoardAsArrayList();
    }


}
