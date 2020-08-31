package de.hsh.drangmeister.sepgp.gp.operator.mutation;

import de.hsh.drangmeister.sepgp.gp.ExecutableSet;
import de.hsh.drangmeister.sepgp.gp.Genome;

public interface IMutation {

    Genome mutate(Genome genome);

}
