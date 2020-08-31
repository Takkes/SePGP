package de.hsh.drangmeister.sepgp.gp.problem;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.instructions.InstructionType;
import de.hsh.drangmeister.sepgp.push.literals.IntLiteral;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForLoopIndexProblem extends Problem {

    private List<TestElement> trainingData;
    private List<TestElement> testData;
    private LevenshteinDistance levenshteinDistance;
    private int levenshteinThreshold;
    private Random random;

    public ForLoopIndexProblem(int randomSeed) {
        instructionTypes = List.of(InstructionType.EXEC, InstructionType.INT, InstructionType.BOOLEAN, InstructionType.PRINT);
        literals = List.of();
        ercGenerators = List.of();

        levenshteinThreshold = 50;
        levenshteinDistance = new LevenshteinDistance(levenshteinThreshold);

        random = new Random(randomSeed);

        setupTrainingAndTestData();
    }

    private void setupTrainingAndTestData() {

        trainingData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int start = -500 + random.nextInt(500);
            int step = 1 + random.nextInt(10);
            int end = start + random.nextInt(20 * step + 1);
            trainingData.add(new TestElement(start, step, end));
        }
        for (int i = 0; i < 90; i++) {
            int step = 1 + random.nextInt(10);
            int start = -500 + random.nextInt(1001 - (20 * step));
            int end = start + random.nextInt(20 * step + 1);
            trainingData.add(new TestElement(start, step, end));
        }

        testData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int start = -500 + random.nextInt(500);
            int step = 1 + random.nextInt(10);
            int end = start + random.nextInt(20 * step + 1);
            testData.add(new TestElement(start, step, end));
        }
        for (int i = 0; i < 900; i++) {
            int step = 1 + random.nextInt(10);
            int start = -500 + random.nextInt(1001 - (20 * step));
            int end = start + random.nextInt(20 * step + 1);
            testData.add(new TestElement(start, step, end));
        }

    }

    @Override
    public int getNumGenerations() {
        return 300;
    }

    @Override
    public int getNumInputs() {
        return 3;
    }

    @Override
    public int getMaxGenomeSize() {
        return 300;
    }

    @Override
    public int getMinGenomeSize() {
        return getMaxGenomeSize() / 10;
    }

    @Override
    protected Interpreter createInterpreter() {
        return new Interpreter(600);
    }

    @Override
    protected void prepareInterpreterWithInputData(Interpreter interpreter, Object input) {
        TestElement testElement = (TestElement) input;
        interpreter.addInput(new IntLiteral(testElement.start));
        interpreter.addInput(new IntLiteral(testElement.step));
        interpreter.addInput(new IntLiteral(testElement.end));
    }

    @Override
    protected float calculateFitnessForResult(Object inputData, Interpreter interpreterAfterExecution) {

        if (interpreterAfterExecution.shouldBePenalizedForBadBehaviour()) {
            numPenalizedPrograms++;
            return levenshteinThreshold;
        }

        String result = interpreterAfterExecution.getOuput();
        TestElement testElement = (TestElement) inputData;

        int levDist = levenshteinDistance.apply(testElement.expected, result);
        return levDist == -1 ? levenshteinThreshold : levDist;
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

    private class TestElement {

        private int start, step, end;
        private String expected;

        public TestElement(int start, int step, int end) {
            this.start = start;
            this.step = step;
            this.end = end;

            StringBuilder sb = new StringBuilder();
            for (int i = start; i < end; i += step) {
                if (sb.length() != 0)
                    sb.append("\n");
                sb.append(i);
            }
            expected = sb.toString();

        }
    }

}
