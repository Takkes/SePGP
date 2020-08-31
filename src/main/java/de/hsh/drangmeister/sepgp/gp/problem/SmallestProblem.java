package de.hsh.drangmeister.sepgp.gp.problem;

import de.hsh.drangmeister.sepgp.gp.erc.IntERCGenerator;
import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.instructions.InstructionType;
import de.hsh.drangmeister.sepgp.push.literals.IntLiteral;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmallestProblem extends Problem {

    private List<TestElement> trainingData;
    private List<TestElement> testData;
    private int randomSeed;

    public SmallestProblem(int randomSeed) {

        instructionTypes = List.of(InstructionType.EXEC, InstructionType.INT, InstructionType.BOOLEAN, InstructionType.PRINT);
        literals = new ArrayList<>();
        ercGenerators = List.of(new IntERCGenerator(-100, 100));

        this.randomSeed = randomSeed;

        setupTrainingAndTestData();
    }

    @Override
    public int getNumInputs() {
        return 4;
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
    protected void prepareInterpreterWithInputData(Interpreter interpreter, Object training) {
        ((TestElement) training).input.stream().map(IntLiteral::new).forEach(interpreter::addInput);
    }

    @Override
    protected float calculateFitnessForResult(Object inputData, Interpreter interpreterAfterExecution) {

        if (interpreterAfterExecution.shouldBePenalizedForBadBehaviour()) {
            numPenalizedPrograms++;
            return 1;
        }

        String result = interpreterAfterExecution.getOuput();
        String expected = String.valueOf(((TestElement) inputData).result);
        return expected.equals(result) ? 0 : 1;
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

    @Override
    public float getAlignmentDeviation() {
        return 5;
    }

    @Override
    public int getNumGenerations() {
        return 200;
    }

    private void setupTrainingAndTestData() {
        //Training Data
        trainingData = new ArrayList<>();
        trainingData.addAll(List.of(new TestElement(List.of(0, 0, 0, 0), 0), new TestElement(List.of(-44, -44, -7, -13), -44),
                new TestElement(List.of(0, 4, -99, -33), -99), new TestElement(List.of(-22, -22, -22, -22), -22), new TestElement(List.of(99, 100, 99, 100), 99)));

        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < 5; i++) {
            int r = -100 + rand.nextInt(201);
            trainingData.add(new TestElement(List.of(r, r, r, r), r));
        }
        for (int i = 0; i < 10; i++) {
            int r = -100 + rand.nextInt(201);
            int min = r;
            List<Integer> tmp = Arrays.asList(r, r, r, r);
            int update = -100 + rand.nextInt(201);
            min = Math.min(min, update);
            tmp.set(rand.nextInt(4), update);
            trainingData.add(new TestElement(tmp, min));
        }
        for (int i = 0; i < 20; i++) {
            List<Integer> tmp = new ArrayList<>();
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < 4; j++) {
                int next = rand.nextInt(101);
                tmp.add(next);
                min = Math.min(min, next);
            }
            trainingData.add(new TestElement(tmp, min));
        }
        for (int i = 0; i < 60; i++) {
            List<Integer> tmp = new ArrayList<>();
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < 4; j++) {
                int next = -100 + rand.nextInt(201);
                tmp.add(next);
                min = Math.min(min, next);
            }
            trainingData.add(new TestElement(tmp, min));
        }

        //Test data
        testData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int r = -100 + rand.nextInt(201);
            testData.add(new TestElement(List.of(r, r, r, r), r));
        }
        for (int i = 0; i < 100; i++) {
            int r = -100 + rand.nextInt(201);
            int min = r;
            List<Integer> tmp = Arrays.asList(r, r, r, r);
            int update = -100 + rand.nextInt(201);
            min = Math.min(min, update);
            tmp.set(rand.nextInt(4), update);
            testData.add(new TestElement(tmp, min));
        }
        for (int i = 0; i < 200; i++) {
            List<Integer> tmp = new ArrayList<>();
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < 4; j++) {
                int next = rand.nextInt(101);
                tmp.add(next);
                min = Math.min(min, next);
            }
            testData.add(new TestElement(tmp, min));
        }
        for (int i = 0; i < 600; i++) {
            List<Integer> tmp = new ArrayList<>();
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < 4; j++) {
                int next = -100 + rand.nextInt(201);
                tmp.add(next);
                min = Math.min(min, next);
            }
            testData.add(new TestElement(tmp, min));
        }
    }

    private class TestElement {

        List<Integer> input;
        int result;

        public TestElement(List<Integer> input, int result) {
            this.input = input;
            this.result = result;
        }

    }
}