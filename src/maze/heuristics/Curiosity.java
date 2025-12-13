package maze.heuristics;

import core.Pos;
import maze.core.MazeExplorer;
import search.bestfirst.BestFirstQueue;

import java.util.Set;
import java.util.function.ToIntFunction;

public class Curiosity implements ToIntFunction<MazeExplorer> {

    private static final int BONUS = 10;

    @Override
    public int applyAsInt(MazeExplorer node) {
        MazeExplorer goal = node.getGoal();
        Pos current = node.getLocation();
        Pos end = goal.getLocation();

        int base = current.getManhattanDist(end);
        int curiosity = node.getSuccessors().size();

        return base - BONUS * curiosity;


    }

}