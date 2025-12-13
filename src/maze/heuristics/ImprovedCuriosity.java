package maze.heuristics;

import core.Pos;
import maze.core.MazeExplorer;
import search.bestfirst.BestFirstQueue;

import java.util.Set;
import java.util.function.ToIntFunction;

public class ImprovedCuriosity implements ToIntFunction<MazeExplorer> {

    @Override
    public int applyAsInt(MazeExplorer node) {
        MazeExplorer goal = node.getGoal();
        Pos current = node.getLocation();
        Pos end = goal.getLocation();


        int base = current.getManhattanDist(end);
        double weight = 3.0 / (1 + base);
        int curiosity = node.getSuccessors().size();
        int bonus = 10;

        return (int)(base - bonus * weight * curiosity);


    }

}