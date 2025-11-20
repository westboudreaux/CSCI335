package learning.markov;

import learning.core.Histogram;

import java.util.*;

public class MarkovChain<L,S> {
    private LinkedHashMap<L, HashMap<Optional<S>, Histogram<S>>> label2symbol2symbol = new LinkedHashMap<>();

    public Set<L> allLabels() {
        return label2symbol2symbol.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (L language : label2symbol2symbol.keySet()) {
            sb.append(language);
            sb.append('\n');
            for (Map.Entry<Optional<S>, Histogram<S>> entry : label2symbol2symbol.get(language).entrySet()) {
                sb.append("    ");
                sb.append(entry.getKey());
                sb.append(":");
                sb.append(entry.getValue().toString());
                sb.append('\n');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // Increase the count for the transition from prev to next.
    // Should pass SimpleMarkovTest.testCreateChains().
    public void count(Optional<S> prev, L label, S next) {
        label2symbol2symbol.putIfAbsent(label, new HashMap<>());

        HashMap<Optional<S>, Histogram<S>> symbols = label2symbol2symbol.get(label);

        symbols.putIfAbsent(prev, new Histogram<>());

        Histogram<S> sHist = symbols.get(prev);

        sHist.bump(next);
    }

    // Returns P(sequence | label)
    // Should pass SimpleMarkovTest.testSourceProbabilities() and MajorMarkovTest.phraseTest()
    //
    // HINT: Be sure to add 1 to both the numerator and denominator when finding the probability of a
    // transition. This helps avoid sending the probability to zero.
    public double probability(ArrayList<S> sequence, L label) {
        if (!label2symbol2symbol.containsKey(label)) {
            return 0.0;
        }

        HashMap<Optional<S>, Histogram<S>> transitions = label2symbol2symbol.get(label);

        double prob = 1.0;
        Optional<S> prev = Optional.empty();

        for (S next : sequence) {

            Histogram<S> hist = transitions.get(prev);

            int countNext;
            int total;
            int vocab;

            if (hist == null) {
                countNext = 0;
                total = 0;
                vocab = 1;
            } else {
                countNext = hist.getCountFor(next);
                total     = hist.getTotalCounts();

                vocab = hist.size();
                if (vocab == 0) vocab = 1;            // lol double check bro
            }

            double p = (countNext + 1.0) / (total + vocab);

            prob *= p;

            prev = Optional.of(next);
        }

        return prob;
    }


    // Return a map from each label to P(label | sequence).
    // Should pass MajorMarkovTest.testSentenceDistributions()
    public LinkedHashMap<L,Double> labelDistribution(ArrayList<S> sequence) {
        LinkedHashMap<L, Double> result = new LinkedHashMap<>();

        double totalScore = 0.0;

        for (L label : label2symbol2symbol.keySet()) {
            double prob = probability(sequence, label);
            result.put(label, prob);
            totalScore += prob;
        }

        for (L label : result.keySet()) {
            result.put(label, result.get(label) / totalScore);
        }

        return result;
    }

        // Calls labelDistribution(). Returns the label with highest probability.
    // Should pass MajorMarkovTest.bestChainTest()
    public L bestMatchingChain(ArrayList<S> sequence) {
        LinkedHashMap<L, Double> dist = labelDistribution(sequence);

        L bestLabel = null;
        double bestProb = -1.0; // aim low

        for (Map.Entry<L, Double> entry : dist.entrySet()) {
            if (entry.getValue() > bestProb) {
                bestProb = entry.getValue();
                bestLabel = entry.getKey();
            }
        }

        return bestLabel;
    }
}
