package de.hsh.drangmeister.sepgp.gp.util;

import de.hsh.drangmeister.sepgp.gp.Genome;

import java.util.ArrayList;
import java.util.List;

public class RoundResult {

    private Genome bestGenome;
    private Genome bestGenomeSimplified;
    private float bestGenomeTestFitness;
    private float bestGenomeTrainingFitness;
    private long simplificationDuration;
    private boolean hasPerfectFitness;
    private long generatioDuration;
    private int generationOfFirstPerfectGenome;
    private List<Integer> subroutineIntroductions;
    private int numPenalizedPrograms;
    private String extractionMethod;
    private String selectionMethod;
    private GenerationInfo[] generationInfos;

    public RoundResult(Genome bestGenome, Genome bestGenomeSimplified, float bestGenomeTestFitness, float bestGenomeTrainingFitness,
                       long simplificationDuration, boolean hasPerfectFitness, long generatioDuration,
                       int generationOfFirstPerfectGenome, List<Integer> subroutineIntroductions, int numPenalizedPrograms,
                       String extractionMethod, String selectionMethod, GenerationInfo[] generationInfos) {
        this.bestGenome = bestGenome;
        this.bestGenomeSimplified = bestGenomeSimplified;
        this.bestGenomeTestFitness = bestGenomeTestFitness;
        this.bestGenomeTrainingFitness = bestGenomeTrainingFitness;
        this.simplificationDuration = simplificationDuration;
        this.hasPerfectFitness = hasPerfectFitness;
        this.generatioDuration = generatioDuration;
        this.generationOfFirstPerfectGenome = generationOfFirstPerfectGenome;
        this.numPenalizedPrograms = numPenalizedPrograms;
        this.extractionMethod = extractionMethod;
        this.selectionMethod = selectionMethod;
        this.generationInfos = generationInfos;

        this.subroutineIntroductions = new ArrayList<>();
        this.subroutineIntroductions.addAll(subroutineIntroductions);
    }

    public Genome getBestGenome() {
        return bestGenome;
    }

    public Genome getBestGenomeSimplified() {
        return bestGenomeSimplified;
    }

    public float getBestGenomeTestFitness() {
        return bestGenomeTestFitness;
    }

    public float getBestGenomeTrainingFitness() {
        return bestGenomeTrainingFitness;
    }

    public long getSimplificationDuration() {
        return simplificationDuration;
    }

    public boolean hasPerfectFitness() {
        return hasPerfectFitness;
    }

    public long getGeneratioDuration() {
        return generatioDuration;
    }

    public int getGenerationOfFirstPerfectGenome() {
        return generationOfFirstPerfectGenome;
    }

    public List<Integer> getSubroutineIntroductions() {
        return subroutineIntroductions;
    }

    public int getNumPenalizedPrograms() {
        return numPenalizedPrograms;
    }

    public String getExtractionMethod() {
        return extractionMethod;
    }

    public String getSelectionMethod() {
        return selectionMethod;
    }

    public GenerationInfo getGenerationInfo(int generation){
        return generationInfos[generation];
    }
}
