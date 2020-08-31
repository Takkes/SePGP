package de.hsh.drangmeister.sepgp.gp.problem;

import de.hsh.drangmeister.sepgp.gp.erc.SyllablesStringERCGenerator;
import de.hsh.drangmeister.sepgp.gp.erc.VisibleCharacterERCGenerator;
import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.instructions.InstructionType;
import de.hsh.drangmeister.sepgp.push.literals.CharLiteral;
import de.hsh.drangmeister.sepgp.push.literals.StringLiteral;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SyllablesProblem extends Problem {

    private List<TestElement> trainingData;
    private List<TestElement> testData;
    private LevenshteinDistance levenshteinDistance;
    private int levenshteinThreshold;
    private Random random;
    private int randomSeed;

    public SyllablesProblem(int randomSeed) {

        instructionTypes = List.of(InstructionType.EXEC, InstructionType.INT, InstructionType.BOOLEAN, InstructionType.CHAR, InstructionType.STRING, InstructionType.PRINT);
        literals = new ArrayList<>(List.of(new StringLiteral("The number of syllables is "), new StringLiteral("aieouy"), new CharLiteral('a'), new CharLiteral('e'), new CharLiteral('i'),
                new CharLiteral('o'), new CharLiteral('u'), new CharLiteral('y')));
        ercGenerators = List.of(new VisibleCharacterERCGenerator(), new SyllablesStringERCGenerator(20, randomSeed));

        levenshteinThreshold = 20;
        levenshteinDistance = new LevenshteinDistance(levenshteinThreshold);

        this.random = new Random(randomSeed);
        this.randomSeed = randomSeed;

        setupTrainingAndTestData();
    }

    private void setupTrainingAndTestData() {

        SyllablesStringERCGenerator stringGenerator = new SyllablesStringERCGenerator(20, randomSeed);

        trainingData = new ArrayList<>();
        trainingData.addAll(List.of(new TestElement(""), new TestElement("a"), new TestElement("v"), new TestElement("4"), new TestElement("o"),
                new TestElement(" "), new TestElement("aei"), new TestElement("ouy"), new TestElement("chf"), new TestElement("quite"),
                new TestElement("a r e9j>"), new TestElement("you are many yay yea"), new TestElement("ssssssssssssssssssss"),
                new TestElement("oooooooooooooooooooo"), new TestElement("wi wi wi wi wi wi wi"), new TestElement("x y x y x y x y x y "),
                new TestElement("eioyeioyeioyeioyeioy")));
        for (int i = 0; i < 83; i++)
            trainingData.add(new TestElement(stringGenerator.getRandomString(random.nextInt(20))));

        testData = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
            testData.add(new TestElement(stringGenerator.getRandomString(random.nextInt(20))));
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
        return getMaxGenomeSize() / 2;
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
            return 5 * (levenshteinThreshold + 20);
        }

        String result = interpreterAfterExecution.getOuput();
        TestElement testElement = (TestElement) inputData;

        int levDist = levenshteinDistance.apply(testElement.expected, result);
        if (levDist == -1)
            levDist = levenshteinThreshold;

        String numString = result.replace("The number of syllables is ", "");
        int intOff = 20;
        try {
            int numRes = Integer.valueOf(numString);
            intOff = Math.min(intOff, Math.abs(testElement.numSyllables - numRes));
        } catch (NumberFormatException e) {

        }

        return levDist + intOff;
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

        String input;
        int numSyllables;
        String expected;

        public TestElement(String input) {
            this.input = input;

            numSyllables = input.length() - input.replaceAll("[aeiouy]", "").length();
            expected = "The number of syllables is " + numSyllables;
        }
    }

}
