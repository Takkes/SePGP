package de.hsh.drangmeister.sepgp.gp.problem;

import de.hsh.drangmeister.sepgp.gp.erc.BooleanERCGenerator;
import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.instructions.InstructionType;
import de.hsh.drangmeister.sepgp.push.literals.StringLiteral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CompareStringLengthsProblem extends Problem {

    private List<TestElement> trainingData;
    private List<TestElement> testData;
    private Random random;

    public CompareStringLengthsProblem(int randomSeed) {
        instructionTypes = List.of(InstructionType.EXEC, InstructionType.INT, InstructionType.BOOLEAN, InstructionType.STRING);
        literals = List.of();
        ercGenerators = List.of(new BooleanERCGenerator());
        this.random = new Random(randomSeed);

        setupTrainingAndTestData();
    }

    private void setupTrainingAndTestData() {
        int maxStringLength = 50;

        trainingData = new ArrayList<>();
        trainingData.addAll(List.of(new TestElement("", "", ""), new TestElement("", "a", "bc"), new TestElement("a", "", "bc"), new TestElement("a", "bc", ""), new TestElement("", "bc", "a"), new TestElement("bc", "a", ""), new TestElement("bc", "", "a")));
        for (int i = 0; i < 2; i++) {
            String s = getRandomString(random.nextInt(maxStringLength));
            trainingData.addAll(List.of(new TestElement(s, "", ""), new TestElement("", s, ""), new TestElement("", "", s)));
        }
        for (int i = 0; i < 3; i++) {
            String s = getRandomString(random.nextInt(maxStringLength));
            trainingData.addAll(List.of(new TestElement(s, s, ""), new TestElement("", s, s), new TestElement(s, "", s)));
        }
        for (int i = 0; i < 3; i++) {
            String s = getRandomString(random.nextInt(maxStringLength));
            trainingData.addAll(List.of(new TestElement(s, s, s), new TestElement(s, s, s), new TestElement(s, s, s)));
        }
        for (int i = 0; i < 25; i++) {

            int[] lengths = new int[3];
            lengths[0] = random.nextInt(maxStringLength);
            do {
                lengths[1] = random.nextInt(maxStringLength);
            } while (lengths[1] == lengths[0]);
            do {
                lengths[2] = random.nextInt(maxStringLength);
            } while (lengths[2] == lengths[0] || lengths[2] == lengths[1]);
            Arrays.sort(lengths);

            trainingData.add(new TestElement(getRandomString(lengths[0]), getRandomString(lengths[1]), getRandomString(lengths[2])));
        }
        for (int i = 0; i < 50; i++) {
            trainingData.add(new TestElement(getRandomString(random.nextInt(maxStringLength)), getRandomString(random.nextInt(maxStringLength)), getRandomString(random.nextInt(maxStringLength))));
        }

        testData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String s = getRandomString(random.nextInt(maxStringLength));
            testData.addAll(List.of(new TestElement(s, s, s), new TestElement(s, s, s), new TestElement(s, s, s)));
        }
        for (int i = 0; i < 200; i++) {

            int[] lengths = new int[3];
            lengths[0] = random.nextInt(maxStringLength);
            do {
                lengths[1] = random.nextInt(maxStringLength);
            } while (lengths[1] == lengths[0]);
            do {
                lengths[2] = random.nextInt(maxStringLength);
            } while (lengths[2] == lengths[0] || lengths[2] == lengths[1]);
            Arrays.sort(lengths);

            testData.add(new TestElement(getRandomString(lengths[0]), getRandomString(lengths[1]), getRandomString(lengths[2])));
        }
        for (int i = 0; i < 700; i++) {
            testData.add(new TestElement(getRandomString(random.nextInt(maxStringLength)), getRandomString(random.nextInt(maxStringLength)), getRandomString(random.nextInt(maxStringLength))));
        }
    }

    private String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append((char) ('a' + random.nextInt(26)));
        return sb.toString();
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
        return 400;
    }

    @Override
    public int getMinGenomeSize() {
        return getMaxGenomeSize() / 20;
    }

    @Override
    protected Interpreter createInterpreter() {
        return new Interpreter(600);
    }

    @Override
    protected void prepareInterpreterWithInputData(Interpreter interpreter, Object input) {
        TestElement testElement = (TestElement) input;
        interpreter.addInput(new StringLiteral(testElement.s1));
        interpreter.addInput(new StringLiteral(testElement.s2));
        interpreter.addInput(new StringLiteral(testElement.s3));
    }

    @Override
    protected float calculateFitnessForResult(Object inputData, Interpreter interpreterAfterExecution) {

        if (interpreterAfterExecution.shouldBePenalizedForBadBehaviour()) {
            numPenalizedPrograms++;
            return 1;
        }

        //No boolean available? Failed test case
        if (interpreterAfterExecution.getBooleanListStack().isEmpty())
            return 1;
        //Boolean available: Is it equal to the expected value?
        return interpreterAfterExecution.getBooleanListStack().pop() == ((TestElement) inputData).expected ? 0 : 1;
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
        String s1, s2, s3;
        boolean expected;

        public TestElement(String s1, String s2, String s3) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;

            expected = s1.length() < s2.length() && s2.length() < s3.length();
        }
    }

}
