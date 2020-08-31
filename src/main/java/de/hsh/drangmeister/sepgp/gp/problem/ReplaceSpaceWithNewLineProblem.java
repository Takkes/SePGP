package de.hsh.drangmeister.sepgp.gp.problem;

import de.hsh.drangmeister.sepgp.gp.erc.ReplaceSpaceWithNewLineStringERCGenerator;
import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.instructions.InstructionType;
import de.hsh.drangmeister.sepgp.push.literals.CharLiteral;
import de.hsh.drangmeister.sepgp.push.literals.StringLiteral;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;

public class ReplaceSpaceWithNewLineProblem extends Problem {

    private List<TestElement> trainingData;
    private List<TestElement> testData;
    private LevenshteinDistance levenshteinDistance;
    private int levenshteinThreshold;
    private int randomSeed;

    public ReplaceSpaceWithNewLineProblem(int randomSeed) {
        instructionTypes = List.of(InstructionType.EXEC, InstructionType.INT, InstructionType.BOOLEAN, InstructionType.CHAR, InstructionType.STRING, InstructionType.PRINT);
        literals = List.of(new CharLiteral(' '), new CharLiteral('\n'));
        ercGenerators = List.of();

        levenshteinThreshold = 20;
        levenshteinDistance = new LevenshteinDistance(levenshteinThreshold);

        this.randomSeed = randomSeed;
        System.out.println("Started with random seed: " + randomSeed);

        setupTrainingAndTestData();
    }

    private void setupTrainingAndTestData() {
        trainingData = new ArrayList<>();
        trainingData.addAll(List.of(new TestElement(""), new TestElement("A"), new TestElement("*"), new TestElement(" "), new TestElement("s"), new TestElement("B "), new TestElement("  "),
                new TestElement(" D"), new TestElement("ef"), new TestElement("!!"), new TestElement(" F "), new TestElement("T L"), new TestElement("4ps"), new TestElement("q  "),
                new TestElement("   "), new TestElement("  e"), new TestElement("hi "), new TestElement("  $  "), new TestElement("      9"), new TestElement("i !i !i !i !i"),
                new TestElement("88888888888888888888"), new TestElement("                   "), new TestElement("ssssssssssssssssssss"), new TestElement("1 1 1 1 1 1 1 1 1 1 "),
                new TestElement(" v v v v v v v v v v"), new TestElement("Ha Ha Ha Ha Ha Ha Ha"), new TestElement("x y!x y!x y!x y!x y!"), new TestElement("G5G5G5G5G5G5G5G5G5G5"),
                new TestElement(">_=]>_=]>_=]>_=]>_=]"), new TestElement("^_^ ^_^ ^_^ ^_^ ^_^ ")));

        ReplaceSpaceWithNewLineStringERCGenerator stringERCGenerator = new ReplaceSpaceWithNewLineStringERCGenerator(20, 0.2f, randomSeed);
        for (int i = 0; i < 70; i++)
            trainingData.add(new TestElement(stringERCGenerator.generateString()));

        testData = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
            testData.add(new TestElement(stringERCGenerator.generateString()));
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
        interpreter.addInput(new StringLiteral(((TestElement) input).inputString));
    }

    @Override
    protected float calculateFitnessForResult(Object inputData, Interpreter interpreterAfterExecution) {

        if (interpreterAfterExecution.shouldBePenalizedForBadBehaviour()) {
            numPenalizedPrograms++;
            return 31;
        }

        //Levenshtein distance threshold is 20
        //difference between expected int result and actual int result is capped to 20
        //So worst fitness is 40

        String result = interpreterAfterExecution.getOuput();
        TestElement testElement = (TestElement) inputData;

        int levDistance = testElement.expectedString.equals(result) ? 0 : 30;

        int intDist = 1;
        if (!interpreterAfterExecution.getIntListStack().isEmpty())
            intDist = testElement.numNonWhitespaceCharacter == interpreterAfterExecution.getIntListStack().pop() ? 0 : 1;


        return levDistance + intDist;

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

        String inputString;
        int numNonWhitespaceCharacter;
        String expectedString;

        public TestElement(String inputString) {
            this.inputString = inputString;

            numNonWhitespaceCharacter = 0;
            for (int i = 0; i < inputString.length(); i++) {
                if (!Character.isWhitespace(inputString.charAt(i)))
                    numNonWhitespaceCharacter++;
            }

            expectedString = inputString.replaceAll(" ", "\n");
        }
    }

}
