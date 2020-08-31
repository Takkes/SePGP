package de.hsh.drangmeister.sepgp.gp.util;

import java.util.ArrayList;
import java.util.List;

public class RunResult {

    private RoundResult[] roundResults;
    private long totalDuration;

    public RunResult(RoundResult[] roundResults, long totalDuration) {
        this.roundResults = roundResults;
        this.totalDuration = totalDuration;
    }

    public RoundResult getRoundResult(int round) {
        return roundResults[round];
    }

    public RoundResult[] getRoundResults() {
        return roundResults;
    }

    public float getAverageGenerationOfFirstPerfectGenome() {
        float totalGen = 0;
        int numGens = 0;
        for (RoundResult rr : roundResults) {
            if (rr.getGenerationOfFirstPerfectGenome() != -1) {
                totalGen += rr.getGenerationOfFirstPerfectGenome();
                numGens++;
            }
        }
        return totalGen / numGens;
    }

    public List<Integer> getGenerationsOfFirstPerfectGenome() {
        List<Integer> gens = new ArrayList<>();
        for (RoundResult rr : roundResults) {
            if (rr.getGenerationOfFirstPerfectGenome() != -1) {
                gens.add(rr.getGenerationOfFirstPerfectGenome());
            }
        }
        return gens;
    }

    public long getTotalDuration() {
        return totalDuration;
    }
}
