package de.hsh.drangmeister.sepgp.gp;

import de.hsh.drangmeister.sepgp.gp.operator.selection.ISelection;
import de.hsh.drangmeister.sepgp.gp.operator.selection.LexicaseSelection;
import de.hsh.drangmeister.sepgp.gp.operator.mutation.IMutation;
import de.hsh.drangmeister.sepgp.gp.operator.mutation.UniformCloseMutation;
import de.hsh.drangmeister.sepgp.gp.operator.mutation.UniformMutation;
import de.hsh.drangmeister.sepgp.gp.operator.crossover.ICrossover;
import de.hsh.drangmeister.sepgp.gp.operator.crossover.UltCrossover;
import de.hsh.drangmeister.sepgp.gp.operator.selection.TournamentSelection;
import de.hsh.drangmeister.sepgp.gp.operator.simplification.ResultSimplifier;
import de.hsh.drangmeister.sepgp.gp.operator.subroutine_extraction.FitnessSubroutineGeneExtractor;
import de.hsh.drangmeister.sepgp.gp.operator.subroutine_extraction.ISubroutineGeneExtractor;
import de.hsh.drangmeister.sepgp.gp.operator.subroutine_extraction.RandomSubroutineGeneExtractor;
import de.hsh.drangmeister.sepgp.gp.problem.Problem;
import de.hsh.drangmeister.sepgp.gp.util.*;
import de.hsh.drangmeister.sepgp.push.IExecutable;
import de.hsh.drangmeister.sepgp.push.instructions.InputInstructions;
import de.hsh.drangmeister.sepgp.push.instructions.InstructionList;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * @author robin
 */
public class GPRunner {


    public GPRunner() {

    }

    public RunResult runGP(RunParameters runParameters) {

        int minGenomeSize = runParameters.getMinGenomeSize();
        int maxGenomeSize = runParameters.getMaxGenomeSize();
        int numGenerations = runParameters.getNumGenerations();
        int numRounds = runParameters.getNumRounds();
        int numGenerationsWithoutImprovementForSubroutineActivation = runParameters.getNumGenerationsWithoutImprovementForSubroutineActivation();
        int numSubroutinesToInsertIntoPopulation = runParameters.getNumSubroutinesToInsert();
        int populationSize = 1000;
        boolean useSubroutines = runParameters.shouldUseSubroutines();
        float simpleCrossoverRate = runParameters.getSimpleCrossoverRate();
        float crossoverAndMutateRate = runParameters.getCrossoverAndMutateRate();
        float uniformMutationRate = runParameters.getUniformMutationRate();
        float uniformCloseMutationRate = runParameters.getUniformCloseMutationRate();

        long startTime = System.currentTimeMillis();
        RoundResult[] roundResults = new RoundResult[numRounds];
        int estimatedNumberOfGenomeSelectionsPerGeneration = (int) (populationSize + populationSize * (simpleCrossoverRate + crossoverAndMutateRate));
        AtomicInteger doneCounter = new AtomicInteger(0);
        AtomicInteger successfulCounter = new AtomicInteger(0);

        IntStream.range(0, numRounds).parallel().forEach(k -> {

            //Initialize everything
            GenerationInfo[] generationInfo = new GenerationInfo[runParameters.getNumGenerations()];
            Problem problem = runParameters.createProblem();
            ExecutableSet executableSet = createInstructionSet(problem);
            RandomBinDistribution randomBinDistribution = new RandomBinDistribution(4, 1 / 16.f);
            Random random = new Random();
            ISubroutineGeneExtractor subroutineGeneExtractor = new FitnessSubroutineGeneExtractor(runParameters.getNumSelectionRoundsDuringExtraction(), problem::evaluateTotalTrainingFitness /*Base the extraction on TRAINING fitness not test fitness*/
                    , problem::compareFitness, runParameters.getSubroutineMinSizeRate(), runParameters.getSubroutineMaxSizeRate());
            //ISubroutineGeneExtractor subroutineGeneExtractor = new RandomSubroutineGeneExtractor(runParameters.getSubroutineMinSizeRate(), runParameters.getSubroutineMaxSizeRate());

            ICrossover crossoverOperator = new UltCrossover(0.01f, problem.getAlignmentDeviation(), maxGenomeSize);
            ISelection selectionOperator = new LexicaseSelection(problem.getTrainingData().size(), 5);
            IMutation uniformMutation = new UniformMutation(0.01f, executableSet, 0.1f, 0.5f);
            IMutation uniformCloseMutation = new UniformCloseMutation(0.1f, randomBinDistribution);
            ResultSimplifier resultSimplifier = new ResultSimplifier(400, problem::evaluateTotalTestFitness /*Base the simplification on TEST fitness not training fitness*/
                    , problem::compareFitness);
            BiFunction<Float, Float, Integer> fitnessComparator = problem::compareFitness;


            long startRound = System.currentTimeMillis();
            boolean foundPerfectGenome = false;
            List<Genome> selectedGenomes = Collections.synchronizedList(new ArrayList<>(estimatedNumberOfGenomeSelectionsPerGeneration));
            List<Genome> genomes = new ArrayList<>(populationSize);
            List<Subroutine> subroutines = new ArrayList<>();
            List<Integer> subroutineIntroductions = new ArrayList<>();
            //initialize population
            for (int i = 0; i < populationSize; i++) {
                int size = minGenomeSize + random.nextInt(maxGenomeSize - minGenomeSize);
                List<Gene> genes = new ArrayList<>();
                for (IExecutable ex : executableSet.getRandomInstructions(size)) {
                    genes.add(new Gene(ex, randomBinDistribution.getNext()));
                }
                genomes.add(new Genome(genes));
            }

            //Do actual work
            List<Genome> nextGeneration = Collections.synchronizedList(new ArrayList<>());
            for (int i = 0; i < numGenerations; i++) {

                problem.evaluateTrainingFitness(genomes);

                //Calculate statistical data about generation
                Genome bestGenome = genomes.get(0);
                float totalFitness = 0;
                float totalGenomeSize = 0;
                List<Subroutine> subroutinesInUse = new ArrayList<>();
                Set<Integer> fitnessValues = new HashSet<>();   //To keep track of number of individual fitness values
                Set<IExecutable> usedInstructions = new HashSet<>();
                for (Genome gen : genomes) {
                    if (gen.getFitness() < bestGenome.getFitness() || (Math.abs(gen.getFitness() - bestGenome.getFitness()) < 1E-5 && gen.getContainedSubroutines().size() > bestGenome.getContainedSubroutines().size()))
                        bestGenome = gen;
                    totalFitness += gen.getFitness();
                    totalGenomeSize += gen.getSize();
                    fitnessValues.add((int) (gen.getFitness() * 1E4));     //Use 4 decimal digits
                    subroutinesInUse.addAll(gen.getContainedSubroutines());

                    for (Gene gene : gen.getGenes()) {
                        if (gene.getGeneticMaterial() instanceof IExecutable) {
                            usedInstructions.add((IExecutable) gene.getGeneticMaterial());
                        }
                    }

                }
                float avgFitness = totalFitness / populationSize;
                float stdDev = 0;
                for (Genome gen : genomes) {
                    stdDev += Math.pow(avgFitness - gen.getFitness(), 2);
                }
                stdDev = (float) Math.sqrt(stdDev / populationSize);
                generationInfo[i] = new GenerationInfo(bestGenome, avgFitness, totalGenomeSize / populationSize, fitnessValues.size(), stdDev, new HashSet<>(subroutinesInUse).size(), subroutinesInUse.size(), usedInstructions.size());
                if (problem.isPerfectFitness(bestGenome.getFitness())) {
                    //Make sure that not only training fitness is perfect but also test fitness
                    if (problem.isPerfectFitness(problem.evaluateTotalTestFitness(bestGenome))) {
                        successfulCounter.incrementAndGet();
                        foundPerfectGenome = true;
                    }
                }


                //Create new subroutines by finding those genomes that improved over at least one of their parents
                if (i != 0 && !foundPerfectGenome) {

                    float greatestImprovement = 0;
                    Subroutine subroutineFromMostImprovedGenome = null;

                    for (Genome genome : genomes) {
                        //Find genomes that are better than parents
                        Iterator<Genome> it = genome.getParents().iterator();
                        float worstParentFitness = it.next().getFitness();
                        while (it.hasNext()) {
                            float next = it.next().getFitness();
                            if (fitnessComparator.apply(next, worstParentFitness) < 0)
                                worstParentFitness = next;
                        }
                        if (fitnessComparator.apply(genome.getFitness(), worstParentFitness) > 0 && Math.abs(genome.getFitness() - worstParentFitness) > greatestImprovement) {

                            subroutineFromMostImprovedGenome = new Subroutine(subroutineGeneExtractor.extractGenes(genome));
                            greatestImprovement = Math.abs(genome.getFitness() - worstParentFitness);
                        }

                        //Break the reference chain
                        genome.clearParents();
                    }

                    if (subroutineFromMostImprovedGenome != null) {
                        subroutines.add(subroutineFromMostImprovedGenome);
                    }

                }

                //Apply evolutionary operators
                if (i != numGenerations - 1 && !foundPerfectGenome) {

                    //Preselect genomes in parallel
                    int genomeSelector = 0;
                    selectedGenomes.clear();
                    IntStream.range(0, estimatedNumberOfGenomeSelectionsPerGeneration).parallel().forEach(e -> {
                        Genome next = selectionOperator.selectGenome(genomes, fitnessComparator);
                        selectedGenomes.add(next);
                    });

                    for (int j = 0; j < populationSize; j++) {
                        Genome next = genomeSelector < selectedGenomes.size() ? selectedGenomes.get(genomeSelector++) : selectionOperator.selectGenome(genomes, fitnessComparator);
                        float randFloat = random.nextFloat();
                        if (randFloat < simpleCrossoverRate)
                            nextGeneration.add(crossoverOperator.crossover(
                                    next, genomeSelector < selectedGenomes.size() ? selectedGenomes.get(genomeSelector++) : selectionOperator.selectGenome(genomes, fitnessComparator)
                            ));
                        else if (randFloat < simpleCrossoverRate + crossoverAndMutateRate) {
                            Genome parent2 = genomeSelector < selectedGenomes.size() ? selectedGenomes.get(genomeSelector++) : selectionOperator.selectGenome(genomes, fitnessComparator);
                            Genome newGenome = uniformMutation.mutate(
                                    crossoverOperator.crossover(
                                            next, parent2
                                    )
                            );
                            //The genes are coming from crossover + mutation, but the parents are next and parent2
                            nextGeneration.add(new Genome(newGenome.getGenes(), List.of(next, parent2)));
                        } else if (randFloat < simpleCrossoverRate + crossoverAndMutateRate + uniformMutationRate)
                            nextGeneration.add(uniformMutation.mutate(next));
                        else
                            nextGeneration.add(uniformCloseMutation.mutate(next));

                        //Update best ancestor fitness of newly added genome
                        Genome genome = nextGeneration.get(nextGeneration.size() - 1);
                        float bestAncestorFitness = genome.getParents().get(0).getFitness();
                        for (int l = 1; l < genome.getParents().size(); l++) {
                            float af = genome.getParents().get(l).getFitness();
                            if (fitnessComparator.apply(af, bestAncestorFitness) > 0)
                                bestAncestorFitness = af;
                        }

                    }


                    //Insert subroutines into population if necessary
                    //Do so by selecting a genome from current generation, then replace a random gene
                    //with a random subroutine and replace a genome in the next generation with newly
                    //created genome
                    if (useSubroutines &&
                            i >= numGenerationsWithoutImprovementForSubroutineActivation &&
                            !subroutines.isEmpty() &&
                            fitnessComparator.apply(bestGenome.getFitness(), generationInfo[i - numGenerationsWithoutImprovementForSubroutineActivation].getBestGenome().getFitness()) <= 0) {
                        subroutineIntroductions.add(i);
                        for (int l = 0; l < numSubroutinesToInsertIntoPopulation; l++) {
                            Genome next = selectionOperator.selectGenome(genomes, fitnessComparator);

                            Subroutine subroutine = subroutines.get(random.nextInt(subroutines.size()));

                            List<Gene> genes = new ArrayList<>(next.getGenes());
                            genes.set(random.nextInt(genes.size()), new Gene(subroutine, randomBinDistribution.getNext()));

                            nextGeneration.set(l, new Genome(genes, List.of(next)));
                        }
                        subroutines.clear();
                    }


                    genomes.clear();
                    genomes.addAll(nextGeneration);
                    nextGeneration.clear();

                } else {
                    //Last generation or found perfect genome; add results
                    long beforeSimplification = System.currentTimeMillis();
                    Genome simplified = resultSimplifier.simplify(bestGenome);
                    float bestGenomeTestFitness = problem.evaluateTotalTestFitness(bestGenome) / problem.getTestData().size();
                    long simplificationDuration = System.currentTimeMillis() - beforeSimplification;
                    roundResults[k] = new RoundResult(bestGenome, simplified, bestGenomeTestFitness, bestGenome.getFitness(),
                            simplificationDuration, problem.isPerfectFitness(bestGenomeTestFitness), System.currentTimeMillis() - startRound,
                            foundPerfectGenome ? i : -1, subroutineIntroductions, problem.getNumPenalizedPrograms(),
                            subroutineGeneExtractor.getClass().getSimpleName(), selectionOperator.getClass().getSimpleName(),
                            generationInfo);
                }


                if (foundPerfectGenome)
                    break;

            }

            System.out.println("[" + LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME) + "] " + doneCounter.incrementAndGet() + "/" + numRounds + " done- Succesful: " + successfulCounter.get());

        });

        long took = System.currentTimeMillis() - startTime;
        System.out.println("Time total: " + took / 60000 + "min " + (took % 60000) / 1000 + "s " + (took % 1000) + "ms\n");

        return new RunResult(roundResults, took);
    }

    private ExecutableSet createInstructionSet(Problem problem) {
        List<IExecutable> instructionList = InstructionList.getInstructions(problem.getInstructionTypes());
        int numIns = problem.getNumInputs();
        for (int i = 0; i < numIns; i++)
            instructionList.add(new InputInstructions.InputValue(i));
        return new ExecutableSet(instructionList, problem.getLiterals(), problem.getErcGenerators());
    }
}
