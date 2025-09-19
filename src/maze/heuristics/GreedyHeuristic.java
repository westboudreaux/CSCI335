package maze.heuristics;

import maze.core.MazeExplorer;

import java.util.function.ToIntFunction;

public class GreedyHeuristic implements ToIntFunction<MazeExplorer> {



    @Override
    public int applyAsInt(MazeExplorer node) {
        // create some better estimate for the nearest treasure
        // the goal of this heuristic will be to collect all treasure disregarding the distance to them
        return 0;
    }
}