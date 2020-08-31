package de.hsh.drangmeister.sepgp.gp.operator.crossover;

import de.hsh.drangmeister.sepgp.gp.Gene;
import de.hsh.drangmeister.sepgp.gp.Genome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UltCrossover implements ICrossover {

    private float alternationRate;
    private float alignmentDeviation;
    private int maxProgramSize;
    private Random random;

    public UltCrossover(float alternationRate, float alignmentDeviation, int maxProgramSize) {
        this.alternationRate = alternationRate;
        this.alignmentDeviation = alignmentDeviation;
        this.maxProgramSize = maxProgramSize;
        random = new Random();
    }

    @Override
    public Genome crossover(Genome g1, Genome g2) {
        int maxLen = Math.max(g1.getGenes().size(), g2.getGenes().size());

        List<Gene> genes = new ArrayList<>();
        int pointer = 0;
        Genome currentGenome = g1;
        while (pointer < maxLen && genes.size() < maxProgramSize) {

            if (pointer < currentGenome.getGenes().size())
                genes.add(new Gene(currentGenome.getGenes().get(pointer)));

            if (random.nextFloat() < alternationRate) {
                currentGenome = currentGenome == g1 ? g2 : g1;
                pointer = Math.max(0, pointer + (int) (Math.round(random.nextGaussian() * alignmentDeviation)));
            } else {
                pointer++;
            }
        }

        return new Genome(genes, List.of(g1, g2));
    }
}
