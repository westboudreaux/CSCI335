package checkers.searchers;

import checkers.core.Checkerboard;
import checkers.core.CheckersSearcher;
import checkers.core.Move;
import checkers.core.PlayerColor;
import checkers.evaluators.Basic;
import core.Duple;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Set;
import java.util.function.ToIntFunction;


public class NegaMax  extends CheckersSearcher {
    private int numNodes = 0;

    public NegaMax(ToIntFunction<Checkerboard> e) {
        super(e);
    }

    @Override
    public int numNodesExpanded() {
        return numNodes;
    }

    @Override
    public Optional<Duple<Integer, Move>> selectMove(Checkerboard board) {
        return selectHelp(board, getDepthLimit());
    }

    public Optional<Duple<Integer, Move>> selectHelp(Checkerboard board, int depth) {
        // Base cases
        // 1. current player wins
        if (board.playerWins(board.getCurrentPlayer())) {
            return Optional.of(new Duple<>(Integer.MAX_VALUE, board.getLastMove()));
        }

        // 2. other player wins
        if (board.playerWins(board.getCurrentPlayer().opponent())) {
            return Optional.of(new Duple<>(-Integer.MAX_VALUE, board.getLastMove()));
        }
        Optional<Duple<Integer, Move>> bestMove = Optional.empty();
        if (depth != 0) {
            int score = -Integer.MAX_VALUE;
            // use get all boards instead
            for (Checkerboard alternative: board.getNextBoards()) {
                numNodes += 1;


                int scoreFor = -selectHelp(alternative, depth - 1).get().getFirst();



                if (bestMove.isEmpty() || bestMove.get().getFirst() < scoreFor) {
                   bestMove = Optional.of(new Duple<>(scoreFor, alternative.getLastMove()));
               }

            }
        }
        if (depth == 0) {
            return Optional.of(new Duple<>(getEvaluator().applyAsInt(board), board.getLastMove()));
        }
        return bestMove;







    }

    public int getNumNodes() {
        return numNodes;
    }

    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }
}
