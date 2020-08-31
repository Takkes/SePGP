package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author robin
 */
public class IntInstructions {

    /**
     * Calculates modulo of top two stack items. a = pop, b = pop, push(b%a)
     */
    public static class Mod extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return safeIntTwoArgumentFunction(interpreter, (a, b) -> b % a);
        }
    }

    /**
     * Calculates quotient of top two stack items. a = pop, b = pop, push(b/a)
     */
    public static class Div extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return safeIntTwoArgumentFunction(interpreter, (a, b) -> b / a);
        }
    }

    /**
     * Calculates the product of top two elements and pushes result
     */
    public static class Mult extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return intTwoArgumentFunction(interpreter, (a, b) -> a * b);
        }
    }

    /**
     * Calculates the sum of top two elements and pushes result
     */
    public static class Plus extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return intTwoArgumentFunction(interpreter, (a, b) -> a + b);
        }
    }

    /**
     * Calculates the difference of top two elements and pushes result. a =
     * pop(), b = pop(), push(b-a)
     */
    public static class Minus extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return intTwoArgumentFunction(interpreter, (a, b) -> b - a);
        }
    }

    /**
     * Pushes to the boolean stack whether second stack element is less than top
     * stack element
     */
    public static class LessThan extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return booleanTwoArgumentFunction(interpreter, (a, b) -> b < a);
        }
    }

    /**
     * Pushes to the boolean stack whether second stack element is less than or
     * equals to the top stack element
     */
    public static class LessThanOrEquals extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return booleanTwoArgumentFunction(interpreter, (a, b) -> b <= a);
        }
    }

    /**
     * Pushes to the boolean stack whether second stack element is greater than
     * top stack element
     */
    public static class GreaterThan extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return booleanTwoArgumentFunction(interpreter, (a, b) -> b > a);
        }
    }

    /**
     * Pushes to the boolean stack whether second stack element is greater than
     * or equals to top stack element
     */
    public static class GreaterThanOrEquals extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return booleanTwoArgumentFunction(interpreter, (a, b) -> b >= a);
        }
    }

    /**
     * Pushes the maximum of the top two stack elements
     */
    public static class Max extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return intTwoArgumentFunction(interpreter, (a, b) -> Math.max(a, b));
        }
    }

    /**
     * Pushes the minimum of the top two stack elements
     */
    public static class Min extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return intTwoArgumentFunction(interpreter, (a, b) -> Math.min(a, b));
        }
    }

    /**
     * Increments top element by one
     */
    public static class Inc extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return intOneArgumentFunction(interpreter, a -> a + 1);
        }
    }

    /**
     * Decrements top element by one
     */
    public static class Dec extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return intOneArgumentFunction(interpreter, a -> a - 1);
        }
    }

    /**
     * Pushes the result of func applied to the top two arguments on the int
     * stack.
     *
     * @param interpreter
     * @param func
     * @return
     */
    private static boolean intTwoArgumentFunction(Interpreter interpreter, BiFunction<Integer, Integer, Integer> func) {

        ListStack<Integer> stack = interpreter.getIntListStack();

        if (stack.size() < 2) {
            return false;
        }

        stack.push(func.apply(stack.pop(), stack.pop()));

        return true;
    }

    /**
     * Pushes the result of func applied to the top two arguments on the int
     * stack. Before executions checks if top element is zero and doesn't
     * execute func in this case.
     *
     * @param interpreter
     * @param func
     * @return
     */
    private static boolean safeIntTwoArgumentFunction(Interpreter interpreter, BiFunction<Integer, Integer, Integer> func) {

        if (interpreter.getIntListStack().size() < 2 || interpreter.getIntListStack().peek() == 0) {
            return false;
        }

        return intTwoArgumentFunction(interpreter, func);
    }

    private static boolean intOneArgumentFunction(Interpreter interpreter, Function<Integer, Integer> func) {

        ListStack<Integer> stack = interpreter.getIntListStack();

        if (stack.isEmpty()) {
            return false;
        }

        stack.push(func.apply(stack.pop()));

        return true;
    }

    /**
     * Pushes 1 if the top boolean is true, 0 if it's false
     */
    public static class FromBoolean extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Boolean> boolStack = interpreter.getBooleanListStack();

            if (boolStack.isEmpty()) {
                return false;
            }

            if (boolStack.pop()) {
                interpreter.getIntListStack().push(1);
            } else {
                interpreter.getIntListStack().push(0);
            }

            return true;
        }
    }

    /**
     * Pops the top element off the float stack, casts it to int and pushes it
     * onto the int stack
     */
    public static class FromFloat extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Float> floatStack = interpreter.getFloatListStack();

            if (floatStack.isEmpty()) {
                return false;
            }

            interpreter.getIntListStack().push((int) floatStack.pop().floatValue());

            return true;
        }
    }

    /**
     * Tries to convert the top string to an integer and if successful pushes
     * that integer
     */
    public static class FromString extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.isEmpty()) {
                return false;
            }

            try {
                interpreter.getIntListStack().push(Integer.valueOf(stringStack.pop()));
            } catch (NumberFormatException e) {
            }

            return true;
        }
    }

    /**
     * Pushes the top chars int representation onto int stack
     */
    public static class FromChar extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (charStack.isEmpty()) {
                return false;
            }

            interpreter.getIntListStack().push(Integer.valueOf(charStack.pop()));

            return true;
        }
    }

    /**
     * Pushes the result of func applied to the top two arguments on the boolean
     * stack.
     *
     * @param interpreter
     * @param func
     * @return
     */
    private static boolean booleanTwoArgumentFunction(Interpreter interpreter, BiFunction<Integer, Integer, Boolean> func) {

        ListStack<Integer> stack = interpreter.getIntListStack();

        if (stack.size() < 2) {
            return false;
        }

        interpreter.getBooleanListStack().push(func.apply(stack.pop(), stack.pop()));

        return true;
    }

    //**************************************************************************
    //                 Common Instructions from here on
    //**************************************************************************
    public static class Equal extends BaseInstructions.Equal<Integer> {

        @Override
        public ListStack<Integer> getStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }
    }

    public static class Empty extends BaseInstructions.Empty<Integer> {

        @Override
        public ListStack<Integer> getStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }
    }

    public static class Dup extends BaseInstructions.Dup<Integer> {

        @Override
        public ListStack<Integer> getStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }
    }

    public static class Flush extends BaseInstructions.Flush<Integer> {

        @Override
        public ListStack<Integer> getStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }
    }

    public static class Pop extends BaseInstructions.Pop<Integer> {

        @Override
        public ListStack<Integer> getStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }
    }

    public static class Rot extends BaseInstructions.Rot<Integer> {

        @Override
        public ListStack<Integer> getStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }
    }

    public static class Shove extends BaseInstructions.Shove<Integer> {

        @Override
        public ListStack<Integer> getStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }
    }

    public static class StackDepth extends BaseInstructions.StackDepth<Integer> {

        @Override
        public ListStack<Integer> getStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }
    }

    public static class Swap extends BaseInstructions.Swap<Integer> {

        @Override
        public ListStack<Integer> getStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }
    }

    public static class Yank extends BaseInstructions.Yank<Integer> {

        @Override
        public ListStack<Integer> getStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }
    }

    public static class YankDup extends BaseInstructions.YankDup<Integer> {

        @Override
        public ListStack<Integer> getStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }
    }

}
