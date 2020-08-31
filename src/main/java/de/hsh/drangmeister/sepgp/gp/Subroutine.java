package de.hsh.drangmeister.sepgp.gp;

import de.hsh.drangmeister.sepgp.push.IExecutable;
import de.hsh.drangmeister.sepgp.push.instructions.ExecInstructions;

import java.util.*;

public class Subroutine implements IGeneticMaterial {

    private static int idCounter;

    private int subroutineId;
    private List<Gene> genes;
    private Set<Subroutine> containedSubroutines;
    private int totalLength;

    public Subroutine(List<Gene> genes) {
        synchronized (Subroutine.class) {
            subroutineId = idCounter++;
        }

        containedSubroutines = new HashSet<>();
        totalLength = 0;

        this.genes = Collections.unmodifiableList(genes);

        for (Gene gene : this.genes) {
            if (gene.getGeneticMaterial() instanceof Subroutine) {
                Subroutine subroutine = (Subroutine) gene.getGeneticMaterial();
                containedSubroutines.add(subroutine);
                totalLength += subroutine.getTotalLength();
            } else {
                totalLength++;
            }
        }

    }

    /**
     * Subroutines are created once and never modified, therefore they can be identified by there id
     */
    @Override
    public int hashCode() {
        return subroutineId;
    }

    /**
     * Subroutines are created once and never modified, therefore they can be identified by there id
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return ((Subroutine) obj).subroutineId == subroutineId;
    }

    public int getSubroutineId() {
        return subroutineId;
    }

    public Set<Subroutine> getContainedSubroutines() {
        return containedSubroutines;
    }

    public String getCallHierarchy(String offset) {
        if (containedSubroutines.isEmpty())
            return offset + String.valueOf(subroutineId);

        List<String> subCalls = new ArrayList<>();
        for (Subroutine subroutine : containedSubroutines)
            subCalls.add(subroutine.getCallHierarchy(offset + "    "));
        return offset + subroutineId + String.join("", subCalls);
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public int getTotalLength() {
        return totalLength;
    }

}
