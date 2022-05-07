package cz.cvut.fel.pjv.model.pieces;

import cz.cvut.fel.pjv.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class KingPiece extends Piece{
    public KingPiece(Color pieceColor, int boardI, int boardJ) {
        super(pieceColor, boardI, boardJ);
    }

    @Override
    protected ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ) {
        return null;
    }
}
