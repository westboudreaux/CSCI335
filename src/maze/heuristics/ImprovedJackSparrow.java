package maze.heuristics;

import core.Pos;
import maze.core.MazeExplorer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.ToIntFunction;

public class ImprovedJackSparrow implements ToIntFunction<MazeExplorer> {



    @Override
    public int applyAsInt(MazeExplorer node) {
        Pos current = node.getLocation();
        MazeExplorer goal = node.getGoal();
        Set<Pos> booty = node.getAllTreasureFromMaze();
        Set<Pos> collected = node.getAllTreasureFound();
        int best = Integer.MAX_VALUE;

        for(Pos loot : booty){
            if(!collected.contains(loot)){
                int dist = current.getManhattanDist(loot);
                if(dist < best){
                    best = dist;
                    return best + current.getManhattanDist(goal.getLocation());
                }
            }
        }

        return current.getManhattanDist(goal.getLocation());
    }
}