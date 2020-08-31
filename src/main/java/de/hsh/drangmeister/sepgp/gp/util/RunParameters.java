package de.hsh.drangmeister.sepgp.gp.util;

import de.hsh.drangmeister.sepgp.gp.problem.DoubleLettersProblem;
import de.hsh.drangmeister.sepgp.gp.problem.Problem;

import java.lang.reflect.InvocationTargetException;

public class RunParameters {

    private int numRounds;
    private int minGenomeSize;
    private int maxGenomeSize;
    private int numGenerations;
    private float simpleCrossoverRate;
    private float crossoverAndMutateRate;
    private float uniformMutationRate;
    private float uniformCloseMutationRate;
    private Class<? extends Problem> problemClass;

    private int numSubroutinesToInsert;
    private int numSelectionRoundsDuringExtraction;
    private float subroutineMinSizeRate;
    private float subroutineMaxSizeRate;
    private int numGenerationsWithoutImprovementForSubroutineActivation;
    private int initialRandomSeed = 0;
    private boolean useSubroutines;

    public RunParameters(int numRounds, Class<? extends Problem> problemClass, int numSubroutinesToInsert, int numSelectionRoundsDuringExtraction, float subroutineMinSizeRate, float subroutineMaxSizeRate,
                         int numGenerationsWithoutImprovementForSubroutineActivation, boolean useSubroutines, float simpleCrossoverRate, float crossoverAndMutateRate, float uniformMutationRate, float uniformCloseMutationRate) {
        this.numRounds = numRounds;
        this.problemClass = problemClass;
        this.numSubroutinesToInsert = numSubroutinesToInsert;
        this.numSelectionRoundsDuringExtraction = numSelectionRoundsDuringExtraction;
        this.subroutineMinSizeRate = subroutineMinSizeRate;
        this.subroutineMaxSizeRate = subroutineMaxSizeRate;
        this.numGenerationsWithoutImprovementForSubroutineActivation = numGenerationsWithoutImprovementForSubroutineActivation;
        this.simpleCrossoverRate = simpleCrossoverRate;
        this.crossoverAndMutateRate = crossoverAndMutateRate;
        this.uniformMutationRate = uniformMutationRate;
        this.uniformCloseMutationRate = uniformCloseMutationRate;

        Problem prob = createProblem();
        this.minGenomeSize = prob.getMinGenomeSize();
        this.maxGenomeSize = prob.getMaxGenomeSize();
        this.numGenerations = prob.getNumGenerations();

        this.useSubroutines = useSubroutines;
    }

    public Problem createProblem() {
        try {
            int seed = 0;
            synchronized (this) {
                seed = initialRandomSeed++;
            }
            return problemClass.getDeclaredConstructor(new Class[]{int.class}).newInstance(seed);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getNumRounds() {
        return numRounds;
    }

    public int getMinGenomeSize() {
        return minGenomeSize;
    }

    public int getMaxGenomeSize() {
        return maxGenomeSize;
    }

    public int getNumGenerations() {
        return numGenerations;
    }

    public int getNumSubroutinesToInsert() {
        return numSubroutinesToInsert;
    }

    public int getNumSelectionRoundsDuringExtraction() {
        return numSelectionRoundsDuringExtraction;
    }

    public float getSubroutineMinSizeRate() {
        return subroutineMinSizeRate;
    }

    public float getSubroutineMaxSizeRate() {
        return subroutineMaxSizeRate;
    }

    public int getNumGenerationsWithoutImprovementForSubroutineActivation() {
        return numGenerationsWithoutImprovementForSubroutineActivation;
    }

    public Class<? extends Problem> getProblemClass() {
        return problemClass;
    }

    public boolean shouldUseSubroutines() {
        return useSubroutines;
    }

    public float getSimpleCrossoverRate() {
        return simpleCrossoverRate;
    }

    public float getCrossoverAndMutateRate() {
        return crossoverAndMutateRate;
    }

    public float getUniformMutationRate() {
        return uniformMutationRate;
    }

    public float getUniformCloseMutationRate() {
        return uniformCloseMutationRate;
    }
}
