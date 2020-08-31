package de.hsh.drangmeister.sepgp;

import de.hsh.drangmeister.sepgp.gp.GPRunner;
import de.hsh.drangmeister.sepgp.gp.Genome;
import de.hsh.drangmeister.sepgp.gp.Subroutine;
import de.hsh.drangmeister.sepgp.gp.problem.*;
import de.hsh.drangmeister.sepgp.gp.util.RoundResult;
import de.hsh.drangmeister.sepgp.gp.util.RunParameters;
import de.hsh.drangmeister.sepgp.gp.util.RunResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author robin
 */
public class Main {

    public static void main(String[] args) {

        Class<? extends Problem> problemClass = SyllablesProblem.class;
        int numberRounds = 50;

        RunParameters[] runParameters = new RunParameters[]{

               new RunParameters(numberRounds, problemClass, 5, 10, 1 / 20.f, 1 / 6.f,
                        15, false,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 5, 10, 1 / 20.f, 1 / 6.f,
                        15, false,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 5, 10, 1 / 20.f, 1 / 6.f,
                        15, false,
                        0.2f, 0.5f, 0.2f, 0.1f),


                new RunParameters(numberRounds, problemClass, 5, 10, 1 / 20.f, 1 / 6.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 10, 10, 1 / 20.f, 1 / 6.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 15, 10, 1 / 20.f, 1 / 6.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 20, 10, 1 / 20.f, 1 / 6.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 30, 10, 1 / 20.f, 1 / 6.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),

                new RunParameters(numberRounds, problemClass, 15, 1, 1 / 20.f, 1 / 6.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 15, 5, 1 / 20.f, 1 / 6.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 15, 15, 1 / 20.f, 1 / 6.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 15, 20, 1 / 20.f, 1 / 6.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),

                new RunParameters(numberRounds, problemClass, 15, 10, 1 / 4.f, 1 / 2.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 15, 10, 1 / 8.f, 1 / 4.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 15, 10, 1 / 16.f, 1 / 8.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 15, 10, 1 / 32.f, 1 / 16.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 15, 10, 1 / 64.f, 1 / 32.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 15, 10, 1 / 128.f, 1 / 64.f,
                        15, true,
                        0.2f, 0.5f, 0.2f, 0.1f),

                new RunParameters(numberRounds, problemClass, 15, 10, 1 / 20.f, 1 / 6.f,
                        5, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 15, 10, 1 / 20.f, 1 / 6.f,
                        10, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 15, 10, 1 / 20.f, 1 / 6.f,
                        20, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 15, 10, 1 / 20.f, 1 / 6.f,
                        30, true,
                        0.2f, 0.5f, 0.2f, 0.1f),
                new RunParameters(numberRounds, problemClass, 15, 10, 1 / 20.f, 1 / 6.f,
                        40, true,
                        0.2f, 0.5f, 0.2f, 0.1f),


        };

        long startTime = System.currentTimeMillis();
        int done = 0;
        for (RunParameters runParameter : runParameters) {
            GPRunner gpRunner = new GPRunner();
            RunResult runResult = gpRunner.runGP(runParameter);
            printResults(runParameter.getProblemClass().getSimpleName().replace("Problem", ""), runResult, runParameter);
            System.out.println("[" + LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME) + "] " + (++done) + "/" + runParameters.length + " runs done");
        }

        long took = System.currentTimeMillis() - startTime;
        System.out.println("Time for all runs: " + took / 60000 + "min " + (took % 60000) / 1000 + "s " + (took % 1000) + "ms\n");

    }

    private static void printResults(String problemName, RunResult runResult, RunParameters runParameters) {

        int perfectCounter = 0;
        StringBuilder summarySb = new StringBuilder();
        float expandedTotalLength = 0;
        for (RoundResult roundResult : runResult.getRoundResults()) {
            summarySb.append("Training fitness: " + roundResult.getBestGenomeTrainingFitness() + "\n");
            summarySb.append("Test fitness: " + roundResult.getBestGenomeTestFitness() + "\n");
            summarySb.append("Generation took " + roundResult.getGeneratioDuration() / 60000 + "min " + (roundResult.getGeneratioDuration() % 60000) / 1000 + "s " + (roundResult.getGeneratioDuration() % 1000) + "ms\n");
            summarySb.append("Simplification took " + roundResult.getSimplificationDuration() / 1000 + "s " + roundResult.getSimplificationDuration() % 1000 + "ms\n");
            summarySb.append("Is perfect: " + (roundResult.hasPerfectFitness() ? "true" : "nobody's perfect") + "\n");
            summarySb.append("Simplified program: " + roundResult.getBestGenomeSimplified().getPushProgram() + "\n");
            summarySb.append("Original program: " + roundResult.getBestGenome().getPushProgram() + "\n");
            summarySb.append("Subroutine call hierarchy: " + roundResult.getBestGenome().getSubroutineCallHierarchy() + "\n");
            summarySb.append("Expanded length: " + roundResult.getBestGenome().getTotalLength() + "\n");
            summarySb.append("Introduced subroutines in generations: " + roundResult.getSubroutineIntroductions().stream().map(String::valueOf).collect(Collectors.joining(", ")) + "\n");
            summarySb.append("Num penalized programs: " + roundResult.getNumPenalizedPrograms() + "\n");
            summarySb.append("Extraction method: " + roundResult.getExtractionMethod() + "\n");
            summarySb.append("Selection method: " + roundResult.getSelectionMethod() + "\n");
            summarySb.append("Subroutines: \n");
            Set<Subroutine> subroutines = new HashSet<>(roundResult.getBestGenome().getContainedSubroutines());
            for (Subroutine subroutine : subroutines) {
                summarySb.append(subroutine.getSubroutineId() + " (Size: " + subroutine.getTotalLength() + ") : " + new Genome(subroutine.getGenes()).getPushProgram() + " \n");
            }
            summarySb.append("\n");

            if (roundResult.hasPerfectFitness())
                perfectCounter++;

            expandedTotalLength += roundResult.getBestGenome().getTotalLength();
        }

        //Create detailed information
        StringBuilder[] stringBuilderDetail = new StringBuilder[9];
        for (int i = 0; i < stringBuilderDetail.length; i++)
            stringBuilderDetail[i] = new StringBuilder();
        for (int i = 0; i < stringBuilderDetail.length; i++)
            stringBuilderDetail[i].append("Generation;");
        for (int i = 0; i < runParameters.getNumRounds(); i++) {
            stringBuilderDetail[0].append("Best fitness " + (i + 1) + ";");
            stringBuilderDetail[1].append("Avg fitness " + (i + 1) + ";");
            stringBuilderDetail[2].append("Avg genome size " + (i + 1) + ";");
            stringBuilderDetail[3].append("Num different fitness values " + (i + 1) + ";");
            stringBuilderDetail[4].append("Fitness std. dev. " + (i + 1) + ";");
            stringBuilderDetail[5].append("Expanded length best genome " + (i + 1) + ";");
            stringBuilderDetail[6].append("Num different Subroutines " + (i + 1) + ";");
            stringBuilderDetail[7].append("Num total Subroutines " + (i + 1) + ";");
            stringBuilderDetail[8].append("Num different instructions " + (i + 1) + ";");
        }
        for (int i = 0; i < stringBuilderDetail.length; i++)
            stringBuilderDetail[i].append('\n');
        for (int i = 0; i < runParameters.getNumGenerations(); i++) {

            for (int k = 0; k < stringBuilderDetail.length; k++)
                stringBuilderDetail[k].append((i + 1) + ";");

            for (int k = 0; k < runParameters.getNumRounds(); k++) {
                if (runResult.getRoundResult(k).getGenerationInfo(i) != null) {
                    stringBuilderDetail[0].append(runResult.getRoundResult(k).getGenerationInfo(i).getBestGenome().getFitness() + ";");
                    stringBuilderDetail[1].append(runResult.getRoundResult(k).getGenerationInfo(i).getAvgFitness() + ";");
                    stringBuilderDetail[2].append(runResult.getRoundResult(k).getGenerationInfo(i).getAvgGenomeSize() + ";");
                    stringBuilderDetail[3].append(runResult.getRoundResult(k).getGenerationInfo(i).getNumDifferentFitnessValues() + ";");
                    stringBuilderDetail[4].append(runResult.getRoundResult(k).getGenerationInfo(i).getStdDevFitnessValues() + ";");
                    stringBuilderDetail[5].append(runResult.getRoundResult(k).getGenerationInfo(i).getBestGenome().getTotalLength() + ";");
                    stringBuilderDetail[6].append(runResult.getRoundResult(k).getGenerationInfo(i).getNumDifferentSubroutines() + ";");
                    stringBuilderDetail[7].append(runResult.getRoundResult(k).getGenerationInfo(i).getNumTotalSubroutines() + ";");
                    stringBuilderDetail[8].append(runResult.getRoundResult(k).getGenerationInfo(i).getNumDifferentInstructions() + ";");
                } else {
                    for (StringBuilder sb : stringBuilderDetail)
                        sb.append(";");
                }

            }
            for (int k = 0; k < stringBuilderDetail.length; k++)
                stringBuilderDetail[k].append('\n');
        }

        //Create per-round-table
        StringBuilder allInfoSb = new StringBuilder();
        for (int i = 0; i < runParameters.getNumRounds(); i++) {
            allInfoSb.append("Generation;");
            allInfoSb.append("Best fitness;");
            allInfoSb.append("Avg fitness;");
            allInfoSb.append("Avg genome size;");
            allInfoSb.append("Num different fitness values;");
            allInfoSb.append("Fitness std dev;");
            allInfoSb.append("Num different subroutines;");
            allInfoSb.append("Num total subroutines;");
            allInfoSb.append("Num different instructions;;;");
        }
        allInfoSb.append("\n");
        for (int i = 0; i < runParameters.getNumGenerations(); i++) {
            for (int k = 0; k < runParameters.getNumRounds(); k++) {
                allInfoSb.append((i + 1) + ";");
                if (runResult.getRoundResult(k).getGenerationInfo(i) != null) {
                    allInfoSb.append(runResult.getRoundResult(k).getGenerationInfo(i).getBestGenome().getFitness() + ";");
                    allInfoSb.append(runResult.getRoundResult(k).getGenerationInfo(i).getAvgFitness() + ";");
                    allInfoSb.append(runResult.getRoundResult(k).getGenerationInfo(i).getAvgGenomeSize() + ";");
                    allInfoSb.append(runResult.getRoundResult(k).getGenerationInfo(i).getNumDifferentFitnessValues() + ";");
                    allInfoSb.append(runResult.getRoundResult(k).getGenerationInfo(i).getStdDevFitnessValues() + ";");
                    allInfoSb.append(runResult.getRoundResult(k).getGenerationInfo(i).getNumDifferentSubroutines() + ";");
                    allInfoSb.append(runResult.getRoundResult(k).getGenerationInfo(i).getNumTotalSubroutines() + ";");
                    allInfoSb.append(runResult.getRoundResult(k).getGenerationInfo(i).getNumDifferentInstructions() + ";;;");
                } else {
                    allInfoSb.append(";;;;;;;;;;");
                }

            }
            allInfoSb.append('\n');
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String date = df.format(Calendar.getInstance().getTime());

        summarySb.append("\n~~~~~ Round params ~~~~~");
        summarySb.append("\nnumSubroutinesToInsert " + runParameters.getNumSubroutinesToInsert());
        summarySb.append("\nnumSelectionRoundsDuringExtraction " + runParameters.getNumSelectionRoundsDuringExtraction());
        summarySb.append("\nsubroutineMinSizeRate " + runParameters.getSubroutineMinSizeRate());
        summarySb.append("\nsubroutineMaxSizeRate " + runParameters.getSubroutineMaxSizeRate());
        summarySb.append("\nnumGenerationsWithoutImprovementForSubroutineActivation " + runParameters.getNumGenerationsWithoutImprovementForSubroutineActivation());
        summarySb.append("\n~~~~~~~~~~~~~~~~~~~~~~~~");

        summarySb.append("\nNumber of perfect genomes in best genomes: " + perfectCounter);
        summarySb.append("\nAverage generation of first perfect genome: " + runResult.getAverageGenerationOfFirstPerfectGenome());
        summarySb.append("\nAverage expanded length of best genome: " + expandedTotalLength / runParameters.getNumRounds());
        summarySb.append("\nTime total: " + runResult.getTotalDuration() / 60000 + "min " + (runResult.getTotalDuration() % 60000) / 1000 + "s " + (runResult.getTotalDuration() % 1000) + "ms\n");

        summarySb.append("\nGenerations of first perfect genome:");
        for (int i : runResult.getGenerationsOfFirstPerfectGenome())
            summarySb.append("\n" + i);

        String summary = summarySb.toString();

        try {
            File file = new File("output\\" + problemName + "_" + runParameters.getNumRounds() + "runs_" + date + (runParameters.shouldUseSubroutines() ? "" : "_NO_SR_") + ".csv");
            file.getParentFile().mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(allInfoSb.toString().replaceAll("\\.", ",") + "\n\n");
            for (int k = 0; k < stringBuilderDetail.length; k++) {
                writer.write(stringBuilderDetail[k].toString().replaceAll("\\.", ","));
                writer.write("\n\n");
            }
            for (int i = 0; i < 3; i++)
                writer.write("\n");
            writer.write(summary);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(summary);
    }

}
