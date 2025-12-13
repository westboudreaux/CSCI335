package maze.heuristics;

import core.Pos;
import maze.core.MazeExplorer;

import java.util.function.ToIntFunction;

public class ImprovedManhattan implements ToIntFunction<MazeExplorer> {



    @Override
    public int applyAsInt(MazeExplorer node) {
        MazeExplorer goal = node.getGoal();
        Pos current = node.getLocation();
        Pos end = goal.getLocation();
        int weight = current.getX() / node.getM().getXSize() * node.getM().getEnd().getX();

        return weight * current.getManhattanDist(end);
    }
}