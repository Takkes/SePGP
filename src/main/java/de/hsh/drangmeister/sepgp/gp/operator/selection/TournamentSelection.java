package de.hsh.drangmeister.sepgp.gp.operator.selection;

import de.hsh.drangmeister.sepgp.gp.Genome;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public class TournamentSelection implements ISelection {

    private int numRounds;
    private Random random;

    public TournamentSelection(int numRounds) {
        this.numRounds = numRounds;
        random = new Random();
    }

    @Override
    public Genome selectGenome(List<Genome> genomes, BiFunction<Float, Float, Integer> fitnessComparator) {
        Genome best = genomes.get(random.nextInt(genomes.size()));
        for (int i = 0; i < numRounds; i++) {
            Genome competitor = genomes.get(random.nextInt(genomes.size()));
            if (fitnessComparator.apply(best.getFitness(), competitor.getFitness()) < 0)
                best = competitor;
        }
        return best;
    }
}
