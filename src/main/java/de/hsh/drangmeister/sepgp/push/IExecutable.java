
package de.hsh.drangmeister.sepgp.push;

import de.hsh.drangmeister.sepgp.gp.IGeneticMaterial;

/**
 * @author robin
 */
public interface IExecutable extends IGeneticMaterial {

    /**
     * @param interpreter Interpreter to use with this instruction
     * @return whether execution succeeded or failed
     */
    boolean execute(Interpreter interpreter);

}
