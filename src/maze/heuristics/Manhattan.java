package maze.heuristics;

import core.Pos;
import maze.core.MazeExplorer;

import java.util.function.ToIntFunction;

public class Manhattan implements ToIntFunction<MazeExplorer> {



    @Override
    public int applyAsInt(MazeExplorer node) {
        MazeExplorer goal = node.getGoal();
        Pos current = node.getLocation();
        Pos end = goal.getLocation();

        return current.getManhattanDist(end);
    }
}