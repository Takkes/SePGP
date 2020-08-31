package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

/**
 * @author robin
 */
public class BaseInstructions {

    interface IGenericCommonInstruction<T> {

        ListStack<T> getStack(Interpreter interpreter);

    }

    /**
     * Compares the top two elements for equality and pushes the result onto the
     * boolean stack
     */
    protected abstract static class Equal<T> extends BaseInstruction implements IGenericCommonInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<T> stack = getStack(interpreter);
            if (stack.size() < 2) {
                return false;
            }
            interpreter.getBooleanListStack().push(stack.pop().equals(stack.pop()));
            return true;
        }
    }

    /**
     * Pushes to the boolean stack whether the stack is empty
     *
     * @param <T>
     */
    protected abstract static class Empty<T> extends BaseInstruction implements IGenericCommonInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            interpreter.getBooleanListStack().push(getStack(interpreter).isEmpty());
            return true;
        }
    }

    protected abstract static class Dup<T> extends BaseInstruction implements IGenericCommonInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            return getStack(interpreter).dup();
        }
    }

    public abstract static class Flush<T> extends BaseInstruction implements IGenericCommonInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            getStack(interpreter).flush();
            return true;
        }
    }

    public abstract static class Pop<T> extends BaseInstruction implements IGenericCommonInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {

            ListStack<T> stack = getStack(interpreter);

            if (stack.isEmpty())
                return false;

            getStack(interpreter).pop();
            return true;
        }
    }

    public abstract static class Rot<T> extends BaseInstruction implements IGenericCommonInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            return getStack(interpreter).rot();
        }
    }

    public abstract static class Shove<T> extends BaseInstruction implements IGenericCommonInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Integer> intStack = interpreter.getIntListStack();
            if (intStack.isEmpty()) {
                return false;
            }
            getStack(interpreter).shove(intStack.pop());
            return true;
        }
    }

    public abstract static class StackDepth<T> extends BaseInstruction implements IGenericCommonInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            interpreter.getIntListStack().push(getStack(interpreter).size());
            return true;
        }
    }

    public abstract static class Swap<T> extends BaseInstruction implements IGenericCommonInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            getStack(interpreter).swap();
            return true;
        }
    }

    public abstract static class Yank<T> extends BaseInstruction implements IGenericCommonInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Integer> intStack = interpreter.getIntListStack();
            if (intStack.isEmpty()) {
                return false;
            }
            return getStack(interpreter).yank(intStack.pop());
        }
    }

    public abstract static class YankDup<T> extends BaseInstruction implements IGenericCommonInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Integer> intStack = interpreter.getIntListStack();
            if (intStack.isEmpty()) {
                return false;
            }
            return getStack(interpreter).yankDup(intStack.pop());
        }
    }

}
