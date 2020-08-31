package de.hsh.drangmeister.sepgp.gp.problem;

import de.hsh.drangmeister.sepgp.gp.erc.IntERCGenerator;
import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.instructions.InstructionType;
import de.hsh.drangmeister.sepgp.push.literals.IntLiteral;
import de.hsh.drangmeister.sepgp.push.literals.StringLiteral;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmallOrLargeProblem extends Problem {

    private List<Integer> trainingData;
    private List<Integer> testData;
    private LevenshteinDistance levenshteinDistance;
    private int levenshteinThreshold;
    private int randomSeed;

    public SmallOrLargeProblem(int randomSeed) {
        instructionTypes = List.of(InstructionType.EXEC, InstructionType.INT, InstructionType.BOOLEAN, InstructionType.STRING, InstructionType.PRINT);
        literals = List.of(new StringLiteral("small"), new StringLiteral("large"));
        ercGenerators = List.of(new IntERCGenerator(-10000, 10000));

        levenshteinThreshold = 50;
        levenshteinDistance = new LevenshteinDistance(levenshteinThreshold);

        this.randomSeed = randomSeed;

        setupTrainingAndTestData();
    }

    @Override
    public int getNumInputs() {
        return 1;
    }

    @Override
    public int getMaxGenomeSize() {
        return 200;
    }

    @Override
    public int getMinGenomeSize() {
        return getMaxGenomeSize() / 10;
    }

    @Override
    protected Interpreter createInterpreter() {
        return new Interpreter(300);
    }

    @Override
    protected void prepareInterpreterWithInputData(Interpreter interpreter, Object training) {
        interpreter.addInput(new IntLiteral(((Integer) training).intValue()));
    }

    @Override
    protected float calculateFitnessForResult(Object inputData, Interpreter interpreterAfterExecution) {

        if (interpreterAfterExecution.shouldBePenalizedForBadBehaviour()) {
            numPenalizedPrograms++;
            return levenshteinThreshold;
        }

        String result = interpreterAfterExecution.getOuput();
        int test = ((Integer) inputData).intValue();
        String expected = test < 1000 ? "small" : test >= 2000 ? "large" : "";
        int distance = levenshteinDistance.apply(expected, result);
        return distance != -1 ? distance : levenshteinThreshold;
    }

    @Override
    public int compareFitness(float fitness1, float fitness2) {
        float diff = fitness2 - fitness1;
        if (Math.abs(diff) < 1E-5)
            return 0;
        return diff < 0 ? -1 : 1;
    }

    private void setupTrainingAndTestData() {
        trainingData = new ArrayList<>();
        trainingData.addAll(List.of(-10000, 0, 980, 1020, 1980, 2020, 10000));
        for (int i = 995; i <= 1004; i++)
            trainingData.add(i);
        for (int i = 1995; i <= 2004; i++)
            trainingData.add(i);
        Random rand = new Random(randomSeed);
        for (int i = 0; i < 73; i++)
            trainingData.add(-10000 + rand.nextInt(20001));

        testData = new ArrayList<>();
        for (int i = 980; i <= 1019; i++)
            testData.add(i);
        for (int i = 1980; i <= 2019; i++)
            testData.add(i);
        for (int i = 0; i < 920; i++)
            testData.add(-10000 + rand.nextInt(20001));
    }

    @Override
    public List<? extends Object> getTrainingData() {
        return trainingData;
    }

    @Override
    public List<? extends Object> getTestData() {
        return testData;
    }

    @Override
    public boolean isPerfectFitness(float fitness) {
        return Math.abs(fitness) < 1E-5;
    }

    @Override
    public float getAlignmentDeviation() {
        return 5;
    }

    @Override
    public int getNumGenerations() {
        return 300;
    }
}
