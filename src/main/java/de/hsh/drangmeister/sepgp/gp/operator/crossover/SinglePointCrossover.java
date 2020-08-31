package de.hsh.drangmeister.sepgp.gp.operator.crossover;

import de.hsh.drangmeister.sepgp.gp.Gene;
import de.hsh.drangmeister.sepgp.gp.Genome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SinglePointCrossover implements ICrossover {

    private int maxGenomeSize;
    private Random random;

    public SinglePointCrossover(int maxGenomeSize) {
        this.maxGenomeSize = maxGenomeSize;
        this.random = new Random();
    }

    @Override
    public Genome crossover(Genome g1, Genome g2) {
        boolean successful = false;
        List<Gene> genes = new ArrayList<>();
        while (!successful) {

            int point1 = random.nextInt(g1.getGenes().size());
            int point2 = random.nextInt(g2.getGenes().size());

            if (point1 + (g2.getGenes().size() - point2) > maxGenomeSize) {
                continue;
            }


            for (int i = 0; i < point1; i++)
                genes.add(g1.getGenes().get(i));
            for (int i = point2; i < g2.getGenes().size(); i++)
                genes.add(g2.getGenes().get(i));

            successful = true;
        }

        return new Genome(genes, List.of(g1, g2));
    }
}
