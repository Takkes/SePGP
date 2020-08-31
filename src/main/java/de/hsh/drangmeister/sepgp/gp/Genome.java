package de.hsh.drangmeister.sepgp.gp;

import de.hsh.drangmeister.sepgp.push.IExecutable;
import de.hsh.drangmeister.sepgp.push.Program;
import de.hsh.drangmeister.sepgp.push.instructions.BaseInstruction;
import de.hsh.drangmeister.sepgp.push.instructions.ExecInstructions;

import java.util.*;

/**
 * Genomes are immutable.
 * Exception is that parents can be cleared. This ensures that we don't have a very long reference chain of genomes
 * so that the GC can do its work
 */
public class Genome {

    private List<Gene> genes;
    private Program convertedProgram;
    private float fitness;
    private List<Float> individualFitnessResults;
    private List<Genome> parents;
    private List<Subroutine> containedSubroutines;
    private int initalCapacityPushCreationStack;

    public Genome(List<Gene> genes) {
        this(genes, List.of());
    }

    public Genome(List<Gene> genes, List<Genome> parents) {
        this.genes = Collections.unmodifiableList(genes);
        individualFitnessResults = new ArrayList<>();
        containedSubroutines = new ArrayList<>();
        fitness = 0;
        this.parents = parents;
        initalCapacityPushCreationStack = 0;
        createPushProgram();
    }

    private void createPushProgram() {


        List<Gene> expanded = getSubroutineExpandedList();

        ArrayDeque<Program> stack = new ArrayDeque<>(initalCapacityPushCreationStack);
        stack.push(new Program());

        for (Gene gene : expanded) {
            stack.peek().setProcessed(true);

            IExecutable executable = (IExecutable) gene.getGeneticMaterial();
            if (executable instanceof ExecInstructions.NoOpOpenParen || executable instanceof ExecInstructions.NoOpDeletePrevParenPair) {
                if (executable instanceof ExecInstructions.NoOpOpenParen) {
                    Program p = new Program(stack.peek());
                    stack.peek().addExecutable(p);
                    stack.push(p);
                } else {
                    Program removed = null;
                    if (stack.peek().getParentProgram() != null) {
                        removed = elevateInnermostProgram(stack.peek().getParentProgram());
                    } else {
                        removed = elevateInnermostProgram(stack.peek());
                    }
                    if (removed != null && removed == stack.peek())
                        stack.pop();
                }
            } else {
                stack.peek().addExecutable(executable);

                if (executable instanceof BaseInstruction) {
                    BaseInstruction bi = (BaseInstruction) executable;
                    ArrayDeque<Program> tmpStack = new ArrayDeque<>();
                    for (int i = 0; i < bi.numRequiredCodeBlocks(); i++) {
                        Program p = new Program(stack.peek());
                        tmpStack.push(p);
                        stack.peek().addExecutable(p);
                    }
                    tmpStack.forEach(stack::push);
                }
            }

            for (int i = 0; i < gene.getCloseCount() && stack.size() > 1; i++)
                stack.pop();

        }
        while (stack.size() > 1) {
            stack.peek().setProcessed(true);
            stack.pop();
        }

        this.convertedProgram = stack.pop();
    }

    /**
     * Expands all subroutines such that all Genes in the returned list only contain IExecutables
     */
    private void expandSubroutines(List<Gene> genesToExtract, List<Gene> extractedList) {
        for (Gene gene : genesToExtract) {

            //This can happen when this genome is currently being simplified
            if (gene == null)
                continue;

            if (gene.getGeneticMaterial() instanceof IExecutable) {
                extractedList.add(gene);
            } else {
                Subroutine subroutine = (Subroutine) gene.getGeneticMaterial();
                containedSubroutines.add(subroutine);
                containedSubroutines.addAll(subroutine.getContainedSubroutines());
                expandSubroutines(subroutine.getGenes(), extractedList);
            }
        }
    }

    public List<Gene> getSubroutineExpandedList() {
        List<Gene> expanded = new ArrayList<>();
        expandSubroutines(genes, expanded);
        return expanded;
    }

    public int getTotalLength() {
        int totalLength = 0;
        for (Gene gene : genes) {
            if (gene.getGeneticMaterial() instanceof IExecutable) {
                totalLength++;

                if (gene.getGeneticMaterial() instanceof ExecInstructions.NoOpOpenParen)
                    initalCapacityPushCreationStack++;
                else if (gene.getGeneticMaterial() instanceof BaseInstruction) {
                    initalCapacityPushCreationStack += ((BaseInstruction) gene.getGeneticMaterial()).numRequiredCodeBlocks();
                }

            } else {
                totalLength += ((Subroutine) gene.getGeneticMaterial()).getTotalLength();
            }
        }
        return totalLength;
    }

    public String getSubroutineCallHierarchy() {
        List<String> subCalls = new ArrayList<>();
        for (Subroutine subroutine : containedSubroutines)
            subCalls.add(subroutine.getCallHierarchy("\n"));
        return String.join("", subCalls);
    }

    public Program getPushProgram() {
        return convertedProgram;
    }

    private Program elevateInnermostProgram(Program program) {
        List<IExecutable> executables = program.getExecutables();
        for (int i = executables.size() - 1; i >= 0; i--) {
            if (executables.get(i) instanceof Program) {
                Program p = (Program) executables.get(i);

                if (!p.hasBeenProcessed())
                    continue;

                //See if the innermost program is inside this program
                Program ret = elevateInnermostProgram(p);
                if (ret != null)
                    return ret;

                //This is the program we are searching for
                List<IExecutable> elevationList = p.getExecutables();
                executables.remove(i);
                for (int j = 0; j < elevationList.size(); j++)
                    executables.add(i + j, elevationList.get(j));

                return p;
            }
        }
        return null;
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public int getSize() {
        return genes.size();
    }

    public float getFitnessForTrainingCase(int id) {
        return individualFitnessResults.get(id);
    }

    public void addFitnessValue(float fitness) {
        individualFitnessResults.add(fitness);
    }

    public List<Genome> getParents() {
        return parents;
    }

    public void clearParents() {
        this.parents = List.of();
    }

    /**
     * @return directly and indirectly called subroutines
     */
    public List<Subroutine> getContainedSubroutines() {
        return containedSubroutines;
    }
}
