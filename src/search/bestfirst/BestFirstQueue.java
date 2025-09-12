package search.bestfirst;

import core.Duple;
import search.SearchNode;
import search.SearchQueue;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.ToIntFunction;

public class BestFirstQueue<T> implements SearchQueue<T> {
    // Each object in the priority queue is an estimate paired with a SearchNode.
    private final PriorityQueue<Duple<Integer, SearchNode<T>>> queue;

    // For each object encountered, this is the lowest total length estimate
    // encountered so far.
    private final HashMap<T, Integer> lowestEstimateFor;

    // Use this heuristic to get the estimated distance to the goal node.
    private final ToIntFunction<T> heuristic;

    public BestFirstQueue(ToIntFunction<T> heuristic) {
        // Compare duples by their first element (the integer estimate).
        queue = new PriorityQueue<>(Comparator.comparingInt(Duple::getFirst));
        lowestEstimateFor = new HashMap<>();
        this.heuristic = heuristic;
    }

    @Override
    public void enqueue(SearchNode<T> node) {
        T state = node.getValue();
        // total estimated cost = cost so far + heuristic
        int estimate = node.getDepth() + heuristic.applyAsInt(state);

        // Only add if this is a new state or a better estimate
        if (!lowestEstimateFor.containsKey(state) || estimate < lowestEstimateFor.get(state)) {
            lowestEstimateFor.put(state, estimate);
            queue.add(new Duple<>(estimate, node));
        }
    }

    @Override
    public Optional<SearchNode<T>> dequeue() {
        if (queue.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(queue.poll().getSecond());
    }
}
