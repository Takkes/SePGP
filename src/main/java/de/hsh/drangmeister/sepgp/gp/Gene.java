package de.hsh.drangmeister.sepgp.gp;

import de.hsh.drangmeister.sepgp.push.IExecutable;

/**
 *
 * @author robin
 */
public class Gene {

    private IGeneticMaterial geneticMaterial;
    private int closeCount;

    public Gene(Gene other) {
        this.geneticMaterial = other.geneticMaterial;
        this.closeCount = other.closeCount;
    }

    public Gene(IGeneticMaterial geneticMaterial, int closeCount) {
        this.geneticMaterial = geneticMaterial;
        this.closeCount = closeCount;
    }

    public IGeneticMaterial getGeneticMaterial() {
        return geneticMaterial;
    }

    public int getCloseCount() {
        return closeCount;
    }

    @Override
    public String toString() {
        return "{ GeneticMaterial: "+geneticMaterial+", CloseCount: "+closeCount+" }";
    }
}
