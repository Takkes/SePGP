package de.hsh.drangmeister.sepgp.gp.operator.simplification;

import de.hsh.drangmeister.sepgp.gp.Gene;
import de.hsh.drangmeister.sepgp.gp.Genome;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ResultSimplifier implements ISimplification {

    private int cleaningRetries;
    private Function<Genome, Float> fitnessFunction;
    private BiFunction<Float, Float, Integer> fitnessComparator;
    private Random random;

    public ResultSimplifier(int cleaningRetries, Function<Genome, Float> fitnessFunction, BiFunction<Float, Float, Integer> fitnessComparator) {
        this.cleaningRetries = cleaningRetries;
        this.fitnessFunction = fitnessFunction;
        this.fitnessComparator = fitnessComparator;
        this.random = new Random();
    }

    @Override
    public Genome simplify(Genome genome) {

        List<Gene> workingGenes = new ArrayList<>(genome.getSubroutineExpandedList());
        List<Gene> tmpList = new ArrayList<>(workingGenes.size());

        //Calculate old fitness
        float prevFitness = fitnessFunction.apply(new Genome(genome.getGenes()));
        for (int i = 10; i >= 1; i--) {
            int failCounter = 0;
            List<Pair<Integer, Gene>> removedGenes = new ArrayList<>();
            while (!workingGenes.isEmpty() && failCounter < cleaningRetries) {
                removedGenes.clear();
                for (int j = 0; j < i && !workingGenes.isEmpty(); j++) {
                    //Find next gene to remove
                    int indx = random.nextInt(workingGenes.size());
                    removedGenes.add(new Pair(indx, workingGenes.get(indx)));
                    workingGenes.set(indx, null);
                }

                //Check if removal had negative impact
                float newFitness = fitnessFunction.apply(new Genome((workingGenes)));
                if (fitnessComparator.apply(prevFitness, newFitness) != 0) {
                    //If removal had any impact, revert it
                    for (int j = Math.min(i - 1, removedGenes.size() - 1); j >= 0; j--) {
                        workingGenes.set(removedGenes.get(j).getKey(), removedGenes.get(j).getValue());
                    }
                    failCounter++;
                } else {
                    failCounter = 0;
                    tmpList.clear();
                    for (Gene g : workingGenes) {
                        if (g != null)
                            tmpList.add(g);
                    }

                    List<Gene> t = workingGenes;
                    workingGenes = tmpList;
                    tmpList = t;
                }
            }
        }

        return new Genome(workingGenes);
    }
}
