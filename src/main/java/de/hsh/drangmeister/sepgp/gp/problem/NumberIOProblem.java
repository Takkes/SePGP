package de.hsh.drangmeister.sepgp.gp.problem;

import de.hsh.drangmeister.sepgp.gp.erc.FloatERCGenerator;
import de.hsh.drangmeister.sepgp.gp.erc.IntERCGenerator;
import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.instructions.InstructionType;
import de.hsh.drangmeister.sepgp.push.literals.FloatLiteral;
import de.hsh.drangmeister.sepgp.push.literals.IntLiteral;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumberIOProblem extends Problem {

    private List<TestElement> trainingData;
    private List<TestElement> testData;
    private LevenshteinDistance levenshteinDistance;
    private int levenshteinThreshold;

    private int randomSeed;

    public NumberIOProblem(int randomSeed) {

        instructionTypes = List.of(InstructionType.INT, InstructionType.FLOAT, InstructionType.PRINT);
        literals = new ArrayList<>();
        ercGenerators = List.of(new IntERCGenerator(-100, 100), new FloatERCGenerator(-100, 100));

        levenshteinThreshold = 50;
        levenshteinDistance = new LevenshteinDistance(levenshteinThreshold);

        this.randomSeed=randomSeed;

        setupTrainingAndTestData();
    }


    @Override
    public int getNumGenerations() {
        return 200;
    }

    @Override
    public int getNumInputs() {
        return 2;
    }

    @Override
    public int getMaxGenomeSize() {
        return 200;
    }

    @Override
    public int getMinGenomeSize() {
        return getMaxGenomeSize() / 2;
    }

    @Override
    protected Interpreter createInterpreter() {
        return new Interpreter(200);
    }

    @Override
    protected void prepareInterpreterWithInputData(Interpreter interpreter, Object input) {
        TestElement testElement = (TestElement) input;
        interpreter.addInput(new IntLiteral(testElement.intNum));
        interpreter.addInput(new FloatLiteral(testElement.floatNum));
    }

    @Override
    protected float calculateFitnessForResult(Object inputData, Interpreter interpreterAfterExecution) {

        if (interpreterAfterExecution.shouldBePenalizedForBadBehaviour()) {
            numPenalizedPrograms++;
            return 1;
        }

        String result = interpreterAfterExecution.getOuput();

        TestElement testElement = (TestElement) inputData;
        try {
            //If output can be converted to float, use that
            float res = Float.valueOf(result);
            float diff = Math.abs(res - (testElement.intNum + testElement.floatNum));
            return diff < 1E-5 ? 0 : 1;
        } catch (NumberFormatException e) {
            //otherwise apply worst fitness
            return 1;
        }
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
    public int compareFitness(float fitness1, float fitness2) {
        float diff = fitness2 - fitness1;
        if (Math.abs(diff) < 1E-5)
            return 0;
        return diff < 0 ? -1 : 1;
    }

    @Override
    public boolean isPerfectFitness(float fitness) {
        return Math.abs(fitness) < 1E-5;
    }

    private void setupTrainingAndTestData() {

        Random random = new Random(randomSeed);
        trainingData = new ArrayList<>();
        for (int i = 0; i < 25; i++)
            trainingData.add(new TestElement(-100 + random.nextInt(201), -100 + random.nextFloat() * 200));

        testData = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
            testData.add(new TestElement(-100 + random.nextInt(201), -100 + random.nextFloat() * 200));

    }

    private class TestElement {
        int intNum;
        float floatNum;

        public TestElement(int intNum, float floatNum) {
            this.intNum = intNum;
            this.floatNum = floatNum;
        }
    }

}
