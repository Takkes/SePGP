package de.hsh.drangmeister.sepgp.gp.problem;

import de.hsh.drangmeister.sepgp.gp.erc.BooleanERCGenerator;
import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.instructions.InstructionType;
import de.hsh.drangmeister.sepgp.push.literals.IntArrayLiteral;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class MirrorImageProblem extends Problem {

    private List<List<List<Integer>>> trainingData;
    private List<List<List<Integer>>> testData;

    public MirrorImageProblem() {

        instructionTypes = List.of(InstructionType.EXEC, InstructionType.INT, InstructionType.BOOLEAN, InstructionType.ARRAY_INT, InstructionType.PRINT);
        literals = new ArrayList<>();
        ercGenerators = List.of(new BooleanERCGenerator());

        setupTrainingAndTestData();
    }

    @Override
    public int getNumGenerations() {
        return 300;
    }

    @Override
    public int getNumInputs() {
        return 2;
    }

    @Override
    public int getMaxGenomeSize() {
        return 100;
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
    protected void prepareInterpreterWithInputData(Interpreter interpreter, Object training) {
        //If there is only one element, put it into the stack two times, otherwise put both elements onto the stack
        List<List<Integer>> data = (List<List<Integer>>) training;
        if (data.size() == 1) {
            interpreter.getInputListStack().push(new IntArrayLiteral(new ArrayList<>(data.get(0))));
            interpreter.getInputListStack().push(new IntArrayLiteral(new ArrayList<>(data.get(0))));
        } else {
            interpreter.getInputListStack().push(new IntArrayLiteral(new ArrayList<>(data.get(0))));
            interpreter.getInputListStack().push(new IntArrayLiteral(new ArrayList<>(data.get(1))));
        }
    }

    @Override
    protected float calculateFitnessForResult(Object inputData, Interpreter interpreterAfterExecution) {

        if (interpreterAfterExecution.shouldBePenalizedForBadBehaviour()) {
            numPenalizedPrograms++;
            return 1;
        }

        String result = interpreterAfterExecution.getOuput();
        List<List<Integer>> data = (List<List<Integer>>) inputData;
        String expected = null;
        if (data.size() == 2) {
            List<Integer> l1 = data.get(0);
            List<Integer> l2 = data.get(1);
            expected = "true";
            for (int i = 0; i < l1.size(); i++) {
                if (l1.get(i) != l2.get(l2.size() - 1 - i)) {
                    expected = "false";
                    break;
                }
            }
        } else {
            expected = "false";
        }

        return result.equals(expected) ? 0 : 1;
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
        SecureRandom random = new SecureRandom();

        trainingData = new ArrayList<>();
        trainingData.addAll(List.of(List.of(List.of()), List.of(List.of(1)), List.of(List.of(0), List.of(1)), List.of(List.of(1),
                List.of(0)), List.of(List.of(-44), List.of(16)), List.of(List.of(-13), List.of(-12)),
                List.of(List.of(2, 1), List.of(1, 2)), List.of(List.of(0, 1), List.of(1, 0)), List.of(List.of(0, 7), List.of(7, 0)),
                List.of(List.of(5, 8), List.of(5, 8)), List.of(List.of(34, 12), List.of(34, 12)), List.of(List.of(456, 456)),
                List.of(List.of(40, 831), List.of(-431, -680)), List.of(List.of(1, 2, 1)), List.of(List.of(1, 2, 3, 4, 5, 4, 3, 2, 1)),
                List.of(List.of(45, 99, 0, 12, 44, 7, 7, 44, 12, 0, 99, 45)),
                List.of(List.of(24, 23, 22, 21, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24)),
                List.of(List.of(33, 45, -941)), List.of(List.of(33, -941, 45), List.of(33, 45, -941)), List.of(List.of(45, 33, -941), List.of(33, 45, -941)),
                List.of(List.of(45, -941, 33), List.of(33, 45, -941)), List.of(List.of(-941, 33, 45), List.of(33, 45, -941)), List.of(List.of(-941, 45, 33), List.of(33, 45, -941))));

        int maxListSize = 50;
        for (int i = 0; i < 37; i++) {
            int size = random.nextInt(maxListSize);
            ArrayList<Integer> first = new ArrayList<>();
            ArrayList<Integer> second = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                first.add(-1000 + random.nextInt(2001));
            }
            for (int j = 0; j < size; j++) {
                second.add(first.get(size - 1 - j));
            }
            trainingData.add(List.of(first, second));
        }
        for (int i = 0; i < 10; i++) {
            int size = random.nextInt(maxListSize);
            ArrayList<Integer> first = new ArrayList<>();
            ArrayList<Integer> second = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                int next = -1000 + random.nextInt(2001);
                first.add(next);
                second.add(next);
            }
            trainingData.add(List.of(first, second));
        }
        for (int i = 0; i < 20; i++) {
            int size = random.nextInt(maxListSize);
            ArrayList<Integer> first = new ArrayList<>();
            ArrayList<Integer> second = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                first.add(-1000 + random.nextInt(2001));
            }
            for (int j = 0; j < size; j++) {
                second.add(first.get(size - 1 - j));
            }
            //Change some elements
            if (size != 0) {
                int numChange = 3 + random.nextInt(8);
                for (int j = 0; j < numChange; j++) {
                    ArrayList<Integer> list = random.nextBoolean() ? first : second;
                    list.set(random.nextInt(list.size()), -1000 + random.nextInt(2001));
                }
            }
            trainingData.add(List.of(first, second));
        }
        for (int i = 0; i < 10; i++) {
            int size = random.nextInt(maxListSize);
            ArrayList<Integer> first = new ArrayList<>();
            ArrayList<Integer> second = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                first.add(-1000 + random.nextInt(2001));
                second.add(-1000 + random.nextInt(2001));
            }
            trainingData.add(List.of(first, second));
        }

        //Test data
        testData = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            int size = random.nextInt(maxListSize);
            ArrayList<Integer> first = new ArrayList<>();
            ArrayList<Integer> second = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                first.add(-1000 + random.nextInt(2001));
            }
            for (int j = 0; j < size; j++) {
                second.add(first.get(size - 1 - j));
            }
            testData.add(List.of(first, second));
        }
        for (int i = 0; i < 100; i++) {
            int size = random.nextInt(maxListSize);
            ArrayList<Integer> first = new ArrayList<>();
            ArrayList<Integer> second = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                int next = -1000 + random.nextInt(2001);
                first.add(next);
                second.add(next);
            }
            testData.add(List.of(first, second));
        }
        for (int i = 0; i < 200; i++) {
            int size = random.nextInt(maxListSize);
            ArrayList<Integer> first = new ArrayList<>();
            ArrayList<Integer> second = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                first.add(-1000 + random.nextInt(2001));
            }
            for (int j = 0; j < size; j++) {
                second.add(first.get(size - 1 - j));
            }
            //Change some elements
            if (size != 0) {
                int numChange = 3 + random.nextInt(8);
                for (int j = 0; j < numChange; j++) {
                    ArrayList<Integer> list = random.nextBoolean() ? first : second;
                    list.set(random.nextInt(list.size()), -1000 + random.nextInt(2001));
                }
            }
            testData.add(List.of(first, second));
        }
        for (int i = 0; i < 200; i++) {
            int size = random.nextInt(maxListSize);
            ArrayList<Integer> first = new ArrayList<>();
            ArrayList<Integer> second = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                first.add(-1000 + random.nextInt(2001));
                second.add(-1000 + random.nextInt(2001));
            }
            testData.add(List.of(first, second));
        }
    }
}
