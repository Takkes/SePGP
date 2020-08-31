package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.function.BiFunction;

/**
 * @author robin
 */
public class BooleanInstructions {

    /**
     * Applies the boolean-and function to the top two booleans and pushes the
     * result
     */
    public static class And extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return twoArgumentFunction(interpreter, (a, b) -> a && b);
        }
    }

    /**
     * Inverts top element, then applies and-operator and pushes the result
     */
    public static class InvertFirstThenAnd extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return twoArgumentFunction(interpreter, (a, b) -> !a && b);
        }
    }

    /**
     * Inverts second element, then applies and-operator and pushes the result
     */
    public static class InvertSecondThenAnd extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return twoArgumentFunction(interpreter, (a, b) -> a && !b);
        }
    }

    /**
     * Applies the boolean-or function to the top two booleans and pushes the
     * result
     */
    public static class Or extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return twoArgumentFunction(interpreter, (a, b) -> a || b);
        }
    }

    /**
     * Applies the boolean-xor function to the top two booleans and pushes the
     * result
     */
    public static class Xor extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return twoArgumentFunction(interpreter, (a, b) -> a ^ b);
        }
    }

    /**
     * Applies the given function to the top two elements and pushes the result
     *
     * @param interpreter
     * @param func
     * @return
     */
    private static boolean twoArgumentFunction(Interpreter interpreter, BiFunction<Boolean, Boolean, Boolean> func) {

        ListStack<Boolean> stack = interpreter.getBooleanListStack();
        int n = stack.size();

        if (n < 2) {
            return false;
        }

        boolean res = func.apply(stack.get(0), stack.get(1));
        stack.set(1, res);
        stack.pop();

        return true;
    }

    /**
     * If top float is 0, pushes false onto the boolean stack, true otherwise
     */
    public static class FromFloat extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Float> floatStack = interpreter.getFloatListStack();
            if (floatStack.isEmpty()) {
                return false;
            }

            float f = floatStack.pop();
            interpreter.getBooleanListStack().push(Math.abs(f) < 1E-5);

            return true;
        }
    }

    /**
     * If top int is 0, pushes false onto the boolean stack, true otherwise
     */
    public static class FromInteger extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Integer> intListStack = interpreter.getIntListStack();

            if (intListStack.isEmpty()) {
                return false;
            }

            int i = intListStack.pop();
            interpreter.getBooleanListStack().push(i == 0);

            return true;
        }
    }

    /**
     * Pushes the inverse of the top element onto the stack
     */
    public static class Not extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Boolean> boolListStack = interpreter.getBooleanListStack();

            if (boolListStack.isEmpty()) {
                return false;
            }

            boolean b = boolListStack.pop();
            boolListStack.push(!b);

            return true;
        }
    }

    /**
     * Pushes a random boolean to the boolean stack
     */
    /*public static class Rand extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            interpreter.getBooleanListStack().push(interpreter.getRandom().nextBoolean());

            return true;
        }
    }*/

    //**************************************************************************
    //                 Common Instructions from here on
    //**************************************************************************
    public static class Equal extends BaseInstructions.Equal<Boolean> {

        @Override
        public ListStack<Boolean> getStack(Interpreter interpreter) {
            return interpreter.getBooleanListStack();
        }
    }

    public static class Empty extends BaseInstructions.Empty<Boolean> {

        @Override
        public ListStack<Boolean> getStack(Interpreter interpreter) {
            return interpreter.getBooleanListStack();
        }
    }

    public static class Dup extends BaseInstructions.Dup<Boolean> {

        @Override
        public ListStack<Boolean> getStack(Interpreter interpreter) {
            return interpreter.getBooleanListStack();
        }
    }

    public static class Flush extends BaseInstructions.Flush<Boolean> {

        @Override
        public ListStack<Boolean> getStack(Interpreter interpreter) {
            return interpreter.getBooleanListStack();
        }
    }

    public static class Pop extends BaseInstructions.Pop<Boolean> {

        @Override
        public ListStack<Boolean> getStack(Interpreter interpreter) {
            return interpreter.getBooleanListStack();
        }
    }

    public static class Rot extends BaseInstructions.Rot<Boolean> {

        @Override
        public ListStack<Boolean> getStack(Interpreter interpreter) {
            return interpreter.getBooleanListStack();
        }
    }

    public static class Shove extends BaseInstructions.Shove<Boolean> {

        @Override
        public ListStack<Boolean> getStack(Interpreter interpreter) {
            return interpreter.getBooleanListStack();
        }
    }

    public static class StackDepth extends BaseInstructions.StackDepth<Boolean> {

        @Override
        public ListStack<Boolean> getStack(Interpreter interpreter) {
            return interpreter.getBooleanListStack();
        }
    }

    public static class Swap extends BaseInstructions.Swap<Boolean> {

        @Override
        public ListStack<Boolean> getStack(Interpreter interpreter) {
            return interpreter.getBooleanListStack();
        }
    }

    public static class Yank extends BaseInstructions.Yank<Boolean> {

        @Override
        public ListStack<Boolean> getStack(Interpreter interpreter) {
            return interpreter.getBooleanListStack();
        }
    }

    public static class YankDup extends BaseInstructions.YankDup<Boolean> {

        @Override
        public ListStack<Boolean> getStack(Interpreter interpreter) {
            return interpreter.getBooleanListStack();
        }
    }

}
