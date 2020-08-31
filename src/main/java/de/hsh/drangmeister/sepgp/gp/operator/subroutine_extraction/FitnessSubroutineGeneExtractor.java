package de.hsh.drangmeister.sepgp.gp.operator.subroutine_extraction;

import de.hsh.drangmeister.sepgp.gp.Gene;
import de.hsh.drangmeister.sepgp.gp.Genome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This implementation does the following numRound times:
 * - remove random sublist from genes
 * - calculate news fitness
 * It then selects the sublist which, when it was  removed from the genes, resulted in the worst fitness. It is likely
 * that this means that this sublist is particularly useful
 */
public class FitnessSubroutineGeneExtractor implements ISubroutineGeneExtractor {

    private Random random;
    private int numRounds;
    private BiFunction<Float, Float, Integer> fitnessComparator;
    private Function<Genome, Float> fitnessFunction;
    private float minSizeRate;
    private float maxSizeRate;

    public FitnessSubroutineGeneExtractor(int numRounds, Function<Genome, Float> fitnessFunction, BiFunction<Float, Float, Integer> fitnessComparator, float minSizeRate, float maxSizeRate) {
        this.numRounds = numRounds;
        this.random = new Random();
        this.fitnessComparator = fitnessComparator;
        this.fitnessFunction = fitnessFunction;
        this.minSizeRate = minSizeRate;
        this.maxSizeRate = maxSizeRate;
    }

    @Override
    public List<Gene> extractGenes(Genome genome) {
        List<Gene> bestGenes = new ArrayList<>();
        List<Gene> tmpGenes = new ArrayList<>();
        int programSize = genome.getGenes().size();
        List<Gene> workList = new ArrayList<>(genome.getGenes());
        float worstFitness = -1;

        for (int i = 0; i < numRounds; i++) {
            //Calculate new sublist params
            int range = (int) (programSize * maxSizeRate - programSize * minSizeRate);
            int extraxtSize = (int) (programSize * minSizeRate) + (range != 0 ? random.nextInt(range) : 0);
            if (extraxtSize == 0)
                extraxtSize = 1;
            int start = random.nextInt(programSize - extraxtSize);
            tmpGenes.clear();

            //Remove sublist from genes
            for (int j = 0; j < extraxtSize; j++) {
                Gene gene = workList.get(start + j);
                tmpGenes.add(gene);
                workList.set(start + j, null);
            }

            //Calculate new fitness and if it is worse that worst fitness, use these new genes
            float fitness = fitnessFunction.apply(new Genome(workList));
            if (i == 0 || fitnessComparator.apply(worstFitness, fitness) > 0) {
                bestGenes.clear();
                bestGenes.addAll(tmpGenes);
                worstFitness = fitness;
            }

            //Restore previous genes
            for (int j = 0; j < extraxtSize; j++) {
                workList.set(start + j, tmpGenes.get(j));
            }
        }

        return bestGenes;
    }

}
