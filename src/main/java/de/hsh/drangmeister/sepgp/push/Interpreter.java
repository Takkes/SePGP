package de.hsh.drangmeister.sepgp.push;

import de.hsh.drangmeister.sepgp.push.literals.BaseIndividualLiteral;
import de.hsh.drangmeister.sepgp.push.literals.BaseLiteral;
import de.hsh.drangmeister.sepgp.push.util.ListStack;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author robin
 */
public class Interpreter {

    protected ListStack<IExecutable> execListStack;
    protected ListStack<Boolean> booleanListStack;
    protected ListStack<Character> charListStack;
    protected ListStack<Integer> intListStack;
    protected ListStack<Float> floatListStack;
    protected ListStack<String> stringListStack;
    protected ListStack<List<Integer>> intArrayListStack;
    protected ListStack<List<Float>> floatArrayListStack;
    protected ListStack<List<String>> stringArrayListStack;
    protected ListStack<BaseLiteral> inputListStack;    //Read-Only
    protected StringBuilder output;
    protected int instructionCount;
    protected int maxInstructions;
    protected boolean penalizeForBadBehaviour;
    protected final int maxExecutionTimeMs;
    private int maxStringLength;

    private static AtomicInteger totalExecs = new AtomicInteger(0);
    private static AtomicInteger executedTooManyInstructionsCounter = new AtomicInteger(0);

    private static AtomicInteger tookTooLongCounter = new AtomicInteger(0);
    private static int tookTooLongThreshold = 1000;

    private static AtomicInteger stringTooLongCounter = new AtomicInteger(0);
    private static int stringTooLongThreshold = 1000;

    public Interpreter(int maxInstructions) {
        execListStack = new ListStack<>();
        booleanListStack = new ListStack<>();
        charListStack = new ListStack<>();
        intListStack = new ListStack<>();
        floatListStack = new ListStack<>();
        stringListStack = new ListStack<>();
        intArrayListStack = new ListStack<>();
        floatArrayListStack = new ListStack<>();
        stringArrayListStack = new ListStack<>();
        inputListStack = new ListStack<>();
        output = new StringBuilder();

        this.maxInstructions = maxInstructions;
        this.maxStringLength = (int) 1E4;

        maxExecutionTimeMs = 1000;

        reset();
    }

    public void reset() {
        output.setLength(0);
        instructionCount = 0;
        penalizeForBadBehaviour = false;

        execListStack.flush();
        booleanListStack.flush();
        charListStack.flush();
        intListStack.flush();
        floatListStack.flush();
        stringListStack.flush();
        intArrayListStack.flush();
        floatArrayListStack.flush();
        stringArrayListStack.flush();
        inputListStack.flush();
    }

    public void execute(Program program) {

        totalExecs.incrementAndGet();

        //Loads program onto exec stack
        execListStack.push(program);

        long start = System.currentTimeMillis();
        while (!execListStack.isEmpty()) {

            IExecutable instruction = execListStack.pop();
            instruction.execute(this);

            instructionCount++;

            if (!stringListStack.isEmpty() && stringListStack.peek().length() > maxStringLength) {
                if (stringTooLongCounter.incrementAndGet() == stringTooLongThreshold) {
                    System.out.println("[" + LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME) + "] " + tookTooLongThreshold + " executions aborted because the STRING was too long.");
                    stringTooLongCounter.set(0);
                }

                penalizeForBadBehaviour = true;
                break;
            }

            if (instructionCount >= maxInstructions) {
                if (executedTooManyInstructionsCounter.incrementAndGet() % 1000000 == 0) {
                    System.out.println(String.format("[" + LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME) + "] " + "Aborted %f%% because they used too many INSTRUCTIONS", 100 * (float) executedTooManyInstructionsCounter.get() / totalExecs.get()));
                    executedTooManyInstructionsCounter.set(0);
                    totalExecs.set(0);
                }

                penalizeForBadBehaviour = true;
                break;
            }

            if (System.currentTimeMillis() - start > maxExecutionTimeMs) {
                if (tookTooLongCounter.incrementAndGet() == tookTooLongThreshold) {
                    System.out.println("[" + LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME) + "] " + tookTooLongThreshold + " executions aborted because they TOOK too long.");
                    tookTooLongCounter.set(0);
                }

                penalizeForBadBehaviour = true;
                break;
            }

        }

    }

    public void printState() {
        int cellSize = 20;
        ArrayDeque<String> state = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.center("INPUT", cellSize));
        sb.append(StringUtils.center("BOOLEAN", cellSize));
        sb.append(StringUtils.center("CHARACTER", cellSize));
        sb.append(StringUtils.center("INTEGER", cellSize));
        sb.append(StringUtils.center("FLOAT", cellSize));
        sb.append(StringUtils.center("STRING", cellSize));
        sb.append(StringUtils.center("INTEGER[]", cellSize));
        sb.append(StringUtils.center("FLOAT[]", cellSize));
        sb.append(StringUtils.center("STRING[]", cellSize));
        sb.append(StringUtils.center("EXEC", cellSize));
        state.push(sb.toString());

        final AtomicBoolean elemsLeft = new AtomicBoolean(true);
        final AtomicInteger indx = new AtomicInteger(0);
        List<ListStack<?>> lists = new ArrayList<>();
        lists.add(inputListStack);
        lists.add(booleanListStack);
        lists.add(charListStack);
        lists.add(intListStack);
        lists.add(floatListStack);
        lists.add(stringListStack);
        lists.add(intArrayListStack);
        lists.add(floatArrayListStack);
        lists.add(stringArrayListStack);
        lists.add(execListStack);
        while (elemsLeft.get()) {
            sb.setLength(0);
            elemsLeft.set(false);
            lists.forEach(l -> {
                if (l.size() > indx.get()) {
                    sb.append(StringUtils.center(l.getFromBottom(indx.get()).toString(), cellSize));
                    elemsLeft.set(true);
                } else {
                    sb.append(StringUtils.center("", cellSize));
                }
            });
            indx.incrementAndGet();
            state.push(sb.toString());
        }

        System.out.println("Output:");
        System.out.println(output.toString());

        state.forEach(System.out::println);
    }

    /**
     * @return the execStack
     */
    public ListStack<IExecutable> getExecListStack() {
        return execListStack;
    }

    /**
     * @return the booleanStack
     */
    public ListStack<Boolean> getBooleanListStack() {
        return booleanListStack;
    }

    /**
     * @return the charStack
     */
    public ListStack<Character> getCharListStack() {
        return charListStack;
    }

    /**
     * @return the intStack
     */
    public ListStack<Integer> getIntListStack() {
        return intListStack;
    }

    /**
     * @return the floatStack
     */
    public ListStack<Float> getFloatListStack() {
        return floatListStack;
    }

    /**
     * @return the stringStack
     */
    public ListStack<String> getStringListStack() {
        return stringListStack;
    }

    /**
     * @return the intArryStack
     */
    public ListStack<List<Integer>> getIntArrayListStack() {
        return intArrayListStack;
    }

    /**
     * @return the floatArrayStack
     */
    public ListStack<List<Float>> getFloatArrayListStack() {
        return floatArrayListStack;
    }

    /**
     * @return the stringArrayStack
     */
    public ListStack<List<String>> getStringArrayListStack() {
        return stringArrayListStack;
    }

    /**
     * @return the inputStack
     */
    public ListStack<BaseLiteral> getInputListStack() {
        return inputListStack;
    }

    /**
     * @return the output
     */
    public StringBuilder getOutputBuilder() {
        return output;
    }

    /**
     * @return output the program generated
     */
    public String getOuput() {
        return output.toString();
    }

    /**
     * Adds an input variable to the input stack to prepare the interpreter for the next run
     *
     * @param lit
     */
    public void addInput(BaseIndividualLiteral lit) {
        inputListStack.push(lit);
    }

    /**
     * Returns whether the interpreter has been forced to stop or stopped normally.
     *
     * @return
     */
    public boolean shouldBePenalizedForBadBehaviour() {
        return penalizeForBadBehaviour;
    }

    public int getMaxStringLength() {
        return maxStringLength;
    }
}
