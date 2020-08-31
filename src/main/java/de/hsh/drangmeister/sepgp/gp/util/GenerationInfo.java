package de.hsh.drangmeister.sepgp.gp.util;

import de.hsh.drangmeister.sepgp.gp.Genome;

public class GenerationInfo {

    private Genome bestGenome;
    private float avgFitness;
    private float avgGenomeSize;
    private int numDifferentFitnessValues;
    private float stdDevFitnessValues;
    private int numDifferentSubroutines;
    private int numTotalSubroutines;
    private int numDifferentInstructions;

    public GenerationInfo(Genome bestGenome, float avgFitness, float avgGenomeSize,
                          int differentFitnessValues, float stdDevFitnessValues, int numDifferentSubroutines, int numTotalSubroutines, int numDifferentInstructions) {
        this.bestGenome = bestGenome;
        this.avgFitness = avgFitness;
        this.avgGenomeSize = avgGenomeSize;
        this.numDifferentFitnessValues = differentFitnessValues;
        this.stdDevFitnessValues = stdDevFitnessValues;
        this.numDifferentSubroutines = numDifferentSubroutines;
        this.numTotalSubroutines = numTotalSubroutines;
        this.numDifferentInstructions = numDifferentInstructions;
    }

    public Genome getBestGenome() {
        return bestGenome;
    }

    public float getAvgFitness() {
        return avgFitness;
    }

    public float getAvgGenomeSize() {
        return avgGenomeSize;
    }

    public int getNumDifferentFitnessValues() {
        return numDifferentFitnessValues;
    }

    public float getStdDevFitnessValues() {
        return stdDevFitnessValues;
    }

    public int getNumDifferentSubroutines() {
        return numDifferentSubroutines;
    }

    public int getNumTotalSubroutines() {
        return numTotalSubroutines;
    }

    public int getNumDifferentInstructions() {
        return numDifferentInstructions;
    }
}
