package de.hsh.drangmeister.sepgp.gp.operator.mutation;

import de.hsh.drangmeister.sepgp.gp.Gene;
import de.hsh.drangmeister.sepgp.gp.Genome;
import de.hsh.drangmeister.sepgp.gp.util.RandomBinDistribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniformCloseMutation implements IMutation {

    private float mutationRate;
    private Random random;
    private RandomBinDistribution randomBinDistribution;

    public UniformCloseMutation(float mutationRate, RandomBinDistribution randomBinDistribution) {
        this.mutationRate = mutationRate;
        this.randomBinDistribution = randomBinDistribution;
        random = new Random();
    }

    @Override
    public Genome mutate(Genome genome) {
        List<Gene> genes = new ArrayList<>(genome.getGenes());
        for (int i = 0; i < genes.size(); i++) {
            if (random.nextFloat() < mutationRate) {
                genes.set(i, new Gene(genes.get(i).getGeneticMaterial(), randomBinDistribution.getNext()));
            }
        }
        return new Genome(genes, List.of(genome));
    }
}
