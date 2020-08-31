package de.hsh.drangmeister.sepgp.gp.operator.subroutine_extraction;

import de.hsh.drangmeister.sepgp.gp.Gene;
import de.hsh.drangmeister.sepgp.gp.Genome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSubroutineGeneExtractor implements ISubroutineGeneExtractor {

    private Random random;
    private float minSizeRate;
    private float maxSizeRate;

    public RandomSubroutineGeneExtractor(float minSizeRate, float maxSizeRate) {
        this.random = new Random();
        this.minSizeRate = minSizeRate;
        this.maxSizeRate = maxSizeRate;
    }

    @Override
    public List<Gene> extractGenes(Genome genome) {
        int programSize = genome.getGenes().size();
        int extraxtSize = (int) (programSize * minSizeRate) + random.nextInt((int) (programSize * maxSizeRate - programSize * minSizeRate));
        int start = random.nextInt(programSize - extraxtSize);
        List<Gene> tmp = new ArrayList<>(extraxtSize);
        for (int i = 0; i < extraxtSize; i++) {
            Gene gene = genome.getGenes().get(start + i);
            tmp.add(gene);

        }
        return tmp;
    }

}
