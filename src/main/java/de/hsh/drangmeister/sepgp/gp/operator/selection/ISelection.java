package de.hsh.drangmeister.sepgp.gp.operator.selection;

import de.hsh.drangmeister.sepgp.gp.Genome;

import java.util.List;
import java.util.function.BiFunction;

public interface ISelection {

    Genome selectGenome(List<Genome> genomes, BiFunction<Float, Float, Integer> fitnessComparator);

}
