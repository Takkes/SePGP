package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.IExecutable;
import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author robin
 */
public class FloatInstructions {

    /**
     * Calculates modulo of top two stack items. a = pop, b = pop, push(b%a)
     */
    public static class Mod extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return safeFloatTwoArgumentFunction(interpreter, (a, b) -> b % a);
        }
    }

    /**
     * Calculates quotient of top two stack items. a = pop, b = pop, push(b/a)
     */
    public static class Div extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return safeFloatTwoArgumentFunction(interpreter, (a, b) -> b / a);
        }
    }

    /**
     * Calculates the product of top two elements and pushes result
     */
    public static class Mult extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return floatTwoArgumentFunction(interpreter, (a, b) -> a * b);
        }
    }

    /**
     * Calculates the sum of top two elements and pushes result
     */
    public static class Plus extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return floatTwoArgumentFunction(interpreter, (a, b) -> a + b);
        }
    }

    /**
     * Calculates the difference of top two elements and pushes result. a =
     * pop(), b = pop(), push(b-a)
     */
    public static class Minus extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return floatTwoArgumentFunction(interpreter, (a, b) -> b - a);
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
     * equals to top stack element
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
            return floatTwoArgumentFunction(interpreter, (a, b) -> Math.max(a, b));
        }
    }

    /**
     * Pushes the minimum of the top two stack elements
     */
    public static class Min extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return floatTwoArgumentFunction(interpreter, (a, b) -> Math.min(a, b));
        }
    }

    /**
     * Pushes the sine of the top stack element
     */
    public static class Sin extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return floatOneArgumentFunction(interpreter, a -> (float) Math.sin(a));
        }
    }

    /**
     * Pushes the cosine of the top stack element
     */
    public static class Cos extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return floatOneArgumentFunction(interpreter, a -> (float) Math.cos(a));
        }
    }

    /**
     * Pushes the tangent of the top stack element
     */
    public static class Tan extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return floatOneArgumentFunction(interpreter, a -> (float) Math.tan(a));
        }
    }

    /**
     * Increments top element by one
     */
    public static class Inc extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return floatOneArgumentFunction(interpreter, a -> a + 1);
        }
    }

    /**
     * Decrements top element by one
     */
    public static class Dec extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return floatOneArgumentFunction(interpreter, a -> a - 1);
        }
    }

    /**
     * Pushes the result of func applied to the top two arguments on the float
     * stack.
     *
     * @param interpreter
     * @param func
     * @return
     */
    private static boolean floatTwoArgumentFunction(Interpreter interpreter, BiFunction<Float, Float, Float> func) {

        ListStack<Float> stack = interpreter.getFloatListStack();

        if (stack.size() < 2) {
            return false;
        }

        stack.push(func.apply(stack.pop(), stack.pop()));

        return true;
    }

    /**
     * Pushes the result of func applied to the top two arguments on the float
     * stack. Before executions checks if top element is zero and doesn't
     * execute func in this case.
     *
     * @param interpreter
     * @param func
     * @return
     */
    private static boolean safeFloatTwoArgumentFunction(Interpreter interpreter, BiFunction<Float, Float, Float> func) {

        if (interpreter.getFloatListStack().size() < 2 || Math.abs(interpreter.getFloatListStack().peek()) < 10E-9) {
            return false;
        }

        return floatTwoArgumentFunction(interpreter, func);
    }

    /**
     * Pushes 1.0 if the top boolean is true, 0.0 if it's false
     */
    public static class FromBoolean extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Boolean> boolStack = interpreter.getBooleanListStack();

            if (boolStack.isEmpty()) {
                return false;
            }

            if (boolStack.pop()) {
                interpreter.getFloatListStack().push(Float.valueOf(1));
            } else {
                interpreter.getFloatListStack().push(Float.valueOf(0));
            }

            return true;
        }
    }

    /**
     * Pops the top element off the int stack and pushes it on the float stack
     */
    public static class FromInteger extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Integer> intStack = interpreter.getIntListStack();

            if (intStack.isEmpty()) {
                return false;
            }

            interpreter.getFloatListStack().push(Float.valueOf(intStack.pop()));

            return true;
        }
    }

    /**
     * Tries to convert the top string to a float and if successful pushes that
     * float
     */
    public static class FromString extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.isEmpty()) {
                return false;
            }

            try {
                interpreter.getFloatListStack().push(Float.valueOf(stringStack.pop()));
            } catch (NumberFormatException e) {
            }

            return true;
        }
    }

    /**
     * Pushes the top chars int representation as float value onto float stack
     */
    public static class FromChar extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (charStack.isEmpty()) {
                return false;
            }

            interpreter.getFloatListStack().push(Float.valueOf(charStack.pop()));

            return true;
        }
    }

    /**
     * Pushes the result of func applied to the top argument on the float stack.
     *
     * @param interpreter
     * @param func
     * @return
     */
    private static boolean floatOneArgumentFunction(Interpreter interpreter, Function<Float, Float> func) {

        ListStack<Float> stack = interpreter.getFloatListStack();

        if (stack.isEmpty()) {
            return false;
        }

        stack.push(func.apply(stack.pop()));

        return true;
    }

    /**
     * Pushes the result of func applied to the top two arguments on the boolean
     * stack.
     *
     * @param interpreter
     * @param func
     * @return
     */
    private static boolean booleanTwoArgumentFunction(Interpreter interpreter, BiFunction<Float, Float, Boolean> func) {

        ListStack<Float> stack = interpreter.getFloatListStack();

        if (stack.size() < 2) {
            return false;
        }

        interpreter.getBooleanListStack().push(func.apply(stack.pop(), stack.pop()));

        return true;
    }

    //**************************************************************************
    //                 Common Instructions from here on
    //**************************************************************************
    public static class Equal implements IExecutable {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Float> stack = interpreter.getFloatListStack();
            if (stack.size() < 2) {
                return false;
            }
            interpreter.getBooleanListStack().push(Math.abs(stack.pop() - stack.pop()) < 1E-5);
            return true;
        }
    }

    public static class Empty extends BaseInstructions.Empty<Float> {

        @Override
        public ListStack<Float> getStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }
    }

    public static class Dup extends BaseInstructions.Dup<Float> {

        @Override
        public ListStack<Float> getStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }
    }

    public static class Flush extends BaseInstructions.Flush<Float> {

        @Override
        public ListStack<Float> getStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }
    }

    public static class Pop extends BaseInstructions.Pop<Float> {

        @Override
        public ListStack<Float> getStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }
    }

    public static class Rot extends BaseInstructions.Rot<Float> {

        @Override
        public ListStack<Float> getStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }
    }

    public static class Shove extends BaseInstructions.Shove<Float> {

        @Override
        public ListStack<Float> getStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }
    }

    public static class StackDepth extends BaseInstructions.StackDepth<Float> {

        @Override
        public ListStack<Float> getStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }
    }

    public static class Swap extends BaseInstructions.Swap<Float> {

        @Override
        public ListStack<Float> getStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }
    }

    public static class Yank extends BaseInstructions.Yank<Float> {

        @Override
        public ListStack<Float> getStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }
    }

    public static class YankDup extends BaseInstructions.YankDup<Float> {

        @Override
        public ListStack<Float> getStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }
    }

}
