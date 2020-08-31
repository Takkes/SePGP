package de.hsh.drangmeister.sepgp.gp.operator.selection;

import de.hsh.drangmeister.sepgp.gp.Genome;

import java.util.*;
import java.util.function.BiFunction;

public class LexicaseSelection implements ISelection {

    private int trainingDataSize;
    private int maxSelectionRounds;
    private Random random;
    private List<Float> bestFitnessValues;

    public LexicaseSelection(int trainingDataSize, int maxSelectionRounds) {
        this.trainingDataSize = trainingDataSize;
        this.maxSelectionRounds = maxSelectionRounds;
        this.random = new Random();
        this.bestFitnessValues = new ArrayList<>();
    }

    @Override
    public Genome selectGenome(List<Genome> genomes, BiFunction<Float, Float, Integer> fitnessComparator) {
        Set<Integer> usedTrainingCases = new HashSet<>();
        List<Genome> candidates = new ArrayList<>(genomes);
        for (int i = 0; i < maxSelectionRounds && candidates.size() > 1; i++) {
            //Find next training case to use
            int nextTrainingCase = random.nextInt(trainingDataSize);
            while (usedTrainingCases.contains(nextTrainingCase))
                nextTrainingCase = random.nextInt(trainingDataSize);

            //Find best fitness that exists for that training case
            float bestFitness = candidates.get(0).getFitnessForTrainingCase(nextTrainingCase);
            for (Genome g : candidates) {
                if (fitnessComparator.apply(bestFitness, g.getFitnessForTrainingCase(nextTrainingCase)) < 0)
                    bestFitness = g.getFitnessForTrainingCase(nextTrainingCase);
            }

            //Eliminate all genomes that have worse fitness than the best
            Iterator<Genome> it = candidates.iterator();
            while (it.hasNext()) {
                //Basically checking for unequality here - but since bestFitness is always the smallest value this
                //means that the fitness of the individual is greater than bestFitness
                if (Math.abs(it.next().getFitnessForTrainingCase(nextTrainingCase) - bestFitness) > 1E-5)
                    it.remove();
            }

        }

        return candidates.size() == 1 ? candidates.get(0) : candidates.get(random.nextInt(candidates.size()));
    }

}
