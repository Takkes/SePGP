package de.hsh.drangmeister.sepgp.gp.operator.subroutine_extraction;

import de.hsh.drangmeister.sepgp.gp.Gene;
import de.hsh.drangmeister.sepgp.gp.Genome;

import java.util.List;

public interface ISubroutineGeneExtractor {

    List<Gene> extractGenes(Genome genome);

}
