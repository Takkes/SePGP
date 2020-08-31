package de.hsh.drangmeister.sepgp.gp.problem;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.instructions.InstructionType;
import de.hsh.drangmeister.sepgp.push.literals.CharLiteral;
import de.hsh.drangmeister.sepgp.push.literals.StringLiteral;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DoubleLettersProblem extends Problem {

    private List<TestElement> trainingData;
    private List<TestElement> testData;
    private LevenshteinDistance levenshteinDistance;
    private int levenshteinThreshold;
    private Random random;

    public DoubleLettersProblem(int randomSeed) {

        instructionTypes = List.of(InstructionType.EXEC, InstructionType.INT, InstructionType.BOOLEAN, InstructionType.CHAR, InstructionType.STRING, InstructionType.PRINT);
        literals = List.of(new CharLiteral('!'));
        ercGenerators = new ArrayList<>();

        levenshteinThreshold = 60;
        levenshteinDistance = new LevenshteinDistance(levenshteinThreshold);

        this.random = new Random(randomSeed);

        setupTrainingAndTestData();
    }

    private void setupTrainingAndTestData() {

        trainingData = new ArrayList<>();
        trainingData.addAll(List.of(new TestElement(""), new TestElement("A"), new TestElement("!"), new TestElement(" "), new TestElement("*"), new TestElement("\t"), new TestElement("\n"),
                new TestElement("B\n"), new TestElement("\n\n"), new TestElement("CD"), new TestElement("ef"), new TestElement("!!"), new TestElement("q!"), new TestElement("!R"),
                new TestElement("!#"), new TestElement("@!"), new TestElement("!F!"), new TestElement("T$L"), new TestElement("4ps"), new TestElement("q\t"),
                new TestElement("!!!"), new TestElement("i:!i:!i:!i:!i:!"), new TestElement("88888888888888888888"), new TestElement("                         "),
                new TestElement("ssssssssssssssssssss"), new TestElement("!!!!!!!!!!!!!!!!!!!!"), new TestElement("Ha Ha Ha Ha Ha Ha"), new TestElement("x\ny!x\ny!x\ny!x\ny!x\ny!"),
                new TestElement("1!1!1!1!1!1!1!1!1!1!"), new TestElement("G5G5G5G5G5G5G5G5G5G5"), new TestElement(">_=]>_=]>_=]>_=]>_=]"), new TestElement("k!!k!!k!!k!!k!!k!!k!")));

        for (int i = 0; i < 68; i++)
            trainingData.add(new TestElement(getRandomString(random.nextInt(20))));

        testData = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
            testData.add(new TestElement(getRandomString(random.nextInt(20))));

    }

    private String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        int range = 'z' - ' ';
        float exclamationProbability = random.nextFloat() * 0.2f;
        for (int i = 0; i < length; i++) {
            if (random.nextFloat() < exclamationProbability)
                sb.append('!');
            else
                sb.append((char) (' ' + random.nextInt(range)));
        }

        return sb.toString();
    }

    @Override
    public int getNumGenerations() {
        return 300;
    }

    @Override
    public int getNumInputs() {
        return 1;
    }

    @Override
    public int getMaxGenomeSize() {
        return 800;
    }

    @Override
    public int getMinGenomeSize() {
        return getMaxGenomeSize() / 20;
    }

    @Override
    protected Interpreter createInterpreter() {
        return new Interpreter(1600);
    }

    @Override
    protected void prepareInterpreterWithInputData(Interpreter interpreter, Object input) {
        interpreter.addInput(new StringLiteral(((TestElement) input).input));
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

        private String input;
        private String expected;

        public TestElement(String input) {
            this.input = input;

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                if (Character.isLetter(input.charAt(i))) {
                    for (int j = 0; j < 2; j++)
                        sb.append(input.charAt(i));
                } else if (input.charAt(i) == '!') {
                    for (int j = 0; j < 3; j++)
                        sb.append(input.charAt(i));
                } else
                    sb.append(input.charAt(i));
            }
            expected = sb.toString();
        }
    }

}
