package checkers.evaluators;

import checkers.core.Checkerboard;
import checkers.core.PlayerColor;

import java.util.function.ToIntFunction;

public class Basic implements ToIntFunction<Checkerboard> {
    @Override
    public int applyAsInt(Checkerboard value) {
        if (!value.gameOver()){
            return value.numPiecesOf(value.getCurrentPlayer()) - value.numPiecesOf(value.getCurrentPlayer().opponent());
        }

        return 0;


    }
}
