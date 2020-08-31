package de.hsh.drangmeister.sepgp.gp.problem;

import de.hsh.drangmeister.sepgp.gp.Subroutine;
import de.hsh.drangmeister.sepgp.gp.erc.ERCGenerator;
import de.hsh.drangmeister.sepgp.gp.Genome;
import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.instructions.InstructionType;
import de.hsh.drangmeister.sepgp.push.literals.BaseLiteral;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author robin
 */
public abstract class Problem {

    protected List<InstructionType> instructionTypes;
    protected List<BaseLiteral> literals;
    protected List<ERCGenerator> ercGenerators;
    protected int numPenalizedPrograms;

    /**
     * Executes all test cases on each genome, calculates the average fitness per test case and updates the genomes
     * fitness accordingly
     *
     * @param genomes
     */
    public void evaluateTrainingFitness(List<Genome> genomes) {
        int trainingDataSize = getTrainingData().size();
        genomes.parallelStream().forEach(genome -> {
            //Run each genome for each test pair and update fitness
            float fitness = evaluateTotalTrainingFitness(genome);
            genome.setFitness(fitness / trainingDataSize);

        });
    }

    /**
     * Executes all test cases on the given genome and returns the total fitness that was summed up over all test cases.
     * To get average fitness divide by @getTestDataSize()
     */
    private float evaluateTotalFitness(Genome genome, List<? extends Object> data) {
        float fitness = 0;

        Interpreter interpreter = createInterpreter();
        for (Object training : data) {
            interpreter.reset();
            prepareInterpreterWithInputData(interpreter, training);

            interpreter.execute(genome.getPushProgram());

            float currentFitness = calculateFitnessForResult(training, interpreter);

            fitness += currentFitness;
            genome.addFitnessValue(currentFitness);
        }


        return fitness;
    }

    /**
     * Executes all training test cases on the given genome and returns the total fitness that was summed up over all test cases.
     * To get average fitness divide by @getTestDataSize()
     */
    public float evaluateTotalTrainingFitness(Genome genome) {
        return evaluateTotalFitness(genome, getTrainingData());
    }

    /**
     * Executes all testing test cases on the given genome and returns the total fitness that was summed up over all test cases.
     * To get average fitness divide by @getTestDataSize()
     */
    public float evaluateTotalTestFitness(Genome genome) {
        return evaluateTotalFitness(genome, getTestData());
    }

    /**
     * @return alignment deviation used in the ult crossover operator. 10 for most problems but different for some
     */
    public float getAlignmentDeviation() {
        return 10;
    }

    /**
     * @return number of generations
     */
    public abstract int getNumGenerations();

    /**
     * @return the number of inputs this problem has
     */
    public abstract int getNumInputs();

    /**
     * @return maximal genome size, that is maximal number of genes in a genome
     */
    public abstract int getMaxGenomeSize();

    /**
     * @return minimal genome size, that is minimal number of genes in a genome
     */
    public abstract int getMinGenomeSize();

    /**
     * Creates an interpreter for the given training data
     */
    protected abstract Interpreter createInterpreter();

    /**
     * Prepares the given interpreter with the given input data
     */
    protected abstract void prepareInterpreterWithInputData(Interpreter interpreter, Object input);

    /**
     * Calculates the fitness for a particular run with the given input data and resulting output
     */
    protected abstract float calculateFitnessForResult(Object inputData, Interpreter interpreterAfterExecution);

    /**
     * Returns the test cases used for training
     */
    public abstract List<? extends Object> getTrainingData();

    /**
     * Returns the test cases used for testing
     */
    public abstract List<? extends Object> getTestData();

    /**
     * Compares two genomes and returns the better one. This is needed since all problems might have different
     * interpretations of the fitness value. If fitness is equal, returns g1
     */
    public Genome getBetterGenome(Genome g1, Genome g2) {
        int comp = compareFitness(g1.getFitness(), g2.getFitness());
        if (comp < 0)
            return g2;
        return g1;
    }

    /**
     * Compares two fitness values.
     *
     * @return < 0 if fitness2 ist better than fitness1, > 0 if fitness 1 is better than fitness2 and 0 if they are equal
     */
    public abstract int compareFitness(float fitness1, float fitness2);

    /**
     * Returns whether the supplied value equals a perfect fitness
     */
    public abstract boolean isPerfectFitness(float fitness);

    public List<ERCGenerator> getErcGenerators() {
        return ercGenerators;
    }

    public List<BaseLiteral> getLiterals() {
        return literals;
    }

    public List<InstructionType> getInstructionTypes() {
        return instructionTypes;
    }

    public int getNumPenalizedPrograms() {
        return numPenalizedPrograms;
    }
}
