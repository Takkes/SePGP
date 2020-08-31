package de.hsh.drangmeister.sepgp.gp.operator.crossover;

import de.hsh.drangmeister.sepgp.gp.Genome;

public interface ICrossover {

    public Genome crossover(Genome g1, Genome g2);

}
