package de.hsh.drangmeister.sepgp.gp.operator.mutation;

import de.hsh.drangmeister.sepgp.gp.ExecutableSet;
import de.hsh.drangmeister.sepgp.gp.Gene;
import de.hsh.drangmeister.sepgp.gp.Genome;
import de.hsh.drangmeister.sepgp.push.IExecutable;
import de.hsh.drangmeister.sepgp.push.literals.ErcGeneratedLiteral;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniformMutation implements IMutation {

    private float mutationRate;
    private Random random;
    private ExecutableSet executableSet;
    private float ercNumeralStdDevPercentage;
    private float ercNumeralMutationProbability;

    public UniformMutation(float mutationRate, ExecutableSet executableSet, float ercNumeralStdDevPercentage, float ercNumeralMutationProbability) {
        this.mutationRate = mutationRate;
        this.executableSet = executableSet;
        this.ercNumeralMutationProbability = ercNumeralMutationProbability;
        this.ercNumeralStdDevPercentage = ercNumeralStdDevPercentage;
        random = new Random();
    }

    @Override
    public Genome mutate(Genome genome) {
        List<Gene> genes = new ArrayList<>(genome.getGenes());
        for (int i = 0; i < genes.size(); i++) {
            if (random.nextFloat() < mutationRate) {
                IExecutable newExec = null;
                if (genes.get(i).getGeneticMaterial() instanceof ErcGeneratedLiteral && random.nextFloat() < ercNumeralMutationProbability)
                    newExec = ((ErcGeneratedLiteral) genes.get(i).getGeneticMaterial()).mutatedLiteral(ercNumeralStdDevPercentage);
                else
                    newExec = executableSet.getRandomInstruction();
                genes.set(i, new Gene(newExec, genes.get(i).getCloseCount()));
            }
        }

        return new Genome(genes, List.of(genome));
    }
}
