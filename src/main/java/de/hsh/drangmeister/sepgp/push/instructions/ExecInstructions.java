package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.IExecutable;
import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.Program;
import de.hsh.drangmeister.sepgp.push.literals.IntLiteral;
import de.hsh.drangmeister.sepgp.push.literals.StringLiteral;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.List;

/**
 * @author robin
 */
public class ExecInstructions {

    /**
     * Basically executes the next element on the exec stack n times where n is
     * the top element of the integer stack. Expands into DoRange. Does nothing
     * if n <= 0. Before execution of the loop body the loop index is pushes to
     * the integer stack.
     */
    public static class DoCount extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            ListStack<IExecutable> execStack = interpreter.getExecListStack();
            ListStack<Integer> intStack = interpreter.getIntListStack();

            if (intStack.isEmpty() || execStack.isEmpty() || intStack.peek() <= 0) {
                return false;
            }

            int count = intStack.pop();

            intStack.push(0);
            intStack.push(count - 1);
            execStack.push(new ExecInstructions.DoRange());

            return true;
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 1;
        }
    }

    /**
     * Performs a loop. Looped code is the next element on the exec stack. Loops
     * from the second top argument on the int stack to the top argument of the
     * int stack. Before execution of loop body pushes the current index on the
     * int stack. E.g.: (0 5 DoRange <code>) executes code six times with indices 0, 1, 2, 3, 4, 5, 6.
     */
    public static class DoRange extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            ListStack<IExecutable> execStack = interpreter.getExecListStack();
            ListStack<Integer> intStack = interpreter.getIntListStack();

            if (execStack.isEmpty() || intStack.size() < 2) {
                return false;
            }

            int destinationIndx = intStack.pop();
            int curIndx = intStack.pop();
            IExecutable code = execStack.pop();

            intStack.push(curIndx);
            if (destinationIndx != curIndx) {
                if (curIndx > destinationIndx) {
                    curIndx--;
                } else {
                    curIndx++;
                }

                execStack.push(new Program(List.of(new IntLiteral(curIndx), new IntLiteral(destinationIndx), this, code)));
            }
            execStack.push(code);

            return true;
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 1;
        }
    }

    /**
     * Basically executes the next element on the exec stack n times where n is
     * the top element of the integer stack. Expands into DoRange. Does nothing
     * if n <= 0. In contrast to DoCount the loop index is NOT pushed to the
     * interger stack prior to execution of the loop body.
     */
    public static class DoTimes extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            ListStack<IExecutable> execStack = interpreter.getExecListStack();
            ListStack<Integer> intStack = interpreter.getIntListStack();

            if (intStack.isEmpty() || execStack.isEmpty() || intStack.peek() <= 0) {
                return false;
            }

            int count = intStack.pop();
            IExecutable code = execStack.pop();

            intStack.push(0);
            intStack.push(count - 1);
            execStack.push(new Program(List.of(new IntInstructions.Pop(), code)));
            execStack.push(new ExecInstructions.DoRange());

            return true;
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 1;
        }
    }

    /**
     * If the top element of the boolean stack is true, removes the second
     * element of the exec stack. If top element is false, removes the first
     * element of the exec stack.
     */
    public static class If extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            ListStack<IExecutable> execStack = interpreter.getExecListStack();
            ListStack<Boolean> boolStack = interpreter.getBooleanListStack();

            if (boolStack.isEmpty() || execStack.size() < 2) {
                return false;
            }

            boolean condition = boolStack.pop();

            if (condition) {
                execStack.remove(1);
            } else {
                execStack.remove(0);
            }

            return true;
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 2;
        }
    }

    /**
     * Executes top element only if top boolean is true
     */
    public static class When extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            ListStack<IExecutable> execStack = interpreter.getExecListStack();
            ListStack<Boolean> boolStack = interpreter.getBooleanListStack();

            if (boolStack.isEmpty() || execStack.isEmpty()) {
                return false;
            }

            boolean condition = boolStack.pop();

            if (!condition) {
                execStack.pop();
            }

            return true;
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 1;
        }
    }

    /**
     * Executes the top element (potentially 0 times) while the top of the
     * boolean stack is true
     */
    public static class While extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            ListStack<IExecutable> execStack = interpreter.getExecListStack();
            ListStack<Boolean> boolStack = interpreter.getBooleanListStack();

            if (boolStack.isEmpty() || execStack.isEmpty()) {
                return false;
            }

            boolean condition = boolStack.pop();

            if (condition) {
                IExecutable code = execStack.pop();
                execStack.push(new Program(List.of(this, code)));
                execStack.push(code);
            } else {
                execStack.pop();
            }

            return true;
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 1;
        }
    }

    /**
     * Executes the top element while the top of the boolean stack is true.
     * Executes at least one time
     */
    public static class DoWhile extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            ListStack<IExecutable> execStack = interpreter.getExecListStack();
            ListStack<Boolean> boolStack = interpreter.getBooleanListStack();

            if (execStack.isEmpty()) {
                return false;
            }

            IExecutable code = execStack.pop();
            if (!boolStack.isEmpty())
                execStack.push(new Program(List.of(new While(), code)));
            execStack.push(code);

            return true;
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 1;
        }
    }

    /**
     * Does nothing
     */
    public static class NoOp extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return true;
        }
    }

    /**
     * Does nothing on execution. Starts a new program when used in a genome
     */
    public static class NoOpOpenParen extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return true;
        }
    }

    /**
     * Does nothing on execution. Lifts the last program to a higher level when used in a genome
     */
    public static class NoOpDeletePrevParenPair extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            return true;
        }
    }

    /**
     * Removes the second item from the stack
     */
    public static class K extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            ListStack<IExecutable> execStack = interpreter.getExecListStack();

            if (execStack.size() < 2) {
                return false;
            }

            execStack.remove(1);

            return true;
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 2;
        }
    }

    /**
     * Pops three items off the stack (A, B, C) where A ist the first item
     * popped. Then pushes the following items: (b, c), b, a.
     */
    public static class S extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            ListStack<IExecutable> execStack = interpreter.getExecListStack();

            if (execStack.size() < 3) {
                return false;
            }

            IExecutable a = execStack.pop();
            IExecutable b = execStack.pop();
            IExecutable c = execStack.pop();

            execStack.push(new Program(List.of(b, c)));
            execStack.push(c);
            execStack.push(a);

            return true;
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 3;
        }
    }

    /**
     * Inserts another item beneath the top item of the from (EXEC.Y <Top-Item>)
     */
    public static class Y extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            ListStack<IExecutable> execStack = interpreter.getExecListStack();

            if (execStack.isEmpty()) {
                return false;
            }

            IExecutable code = execStack.pop();

            execStack.push(new Program(List.of(this, code)));
            execStack.push(code);

            return true;
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 1;
        }
    }

    /**
     * Executes the code on top of the exec stack for each individual character
     * on top of the string stack
     */
    public static class StringIterate extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();
            ListStack<IExecutable> execStack = interpreter.getExecListStack();

            if (stringStack.isEmpty() || execStack.isEmpty()) {
                return false;
            }

            String s = stringStack.pop();
            IExecutable code = execStack.pop();

            if (s.isEmpty()) {
                return false;
            }

            if (s.length() > 1) {
                execStack.push(new Program(List.of(new StringLiteral(s.substring(1)), this, code)));
            }

            interpreter.getCharListStack().push(s.charAt(0));
            execStack.push(code);

            return true;
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 1;
        }
    }

    //**************************************************************************
    //                 Common Instructions from here on
    //**************************************************************************
    public static class Equal extends BaseInstructions.Equal<IExecutable> {

        @Override
        public ListStack<IExecutable> getStack(Interpreter interpreter) {
            return interpreter.getExecListStack();
        }
    }

    public static class Empty extends BaseInstructions.Empty<IExecutable> {

        @Override
        public ListStack<IExecutable> getStack(Interpreter interpreter) {
            return interpreter.getExecListStack();
        }
    }

    public static class Dup extends BaseInstructions.Dup<IExecutable> {

        @Override
        public ListStack<IExecutable> getStack(Interpreter interpreter) {
            return interpreter.getExecListStack();
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 1;
        }
    }

    public static class Flush extends BaseInstructions.Flush<IExecutable> {

        @Override
        public ListStack<IExecutable> getStack(Interpreter interpreter) {
            return interpreter.getExecListStack();
        }
    }

    public static class Pop extends BaseInstructions.Pop<IExecutable> {

        @Override
        public ListStack<IExecutable> getStack(Interpreter interpreter) {
            return interpreter.getExecListStack();
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 1;
        }
    }

    public static class Rot extends BaseInstructions.Rot<IExecutable> {

        @Override
        public ListStack<IExecutable> getStack(Interpreter interpreter) {
            return interpreter.getExecListStack();
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 3;
        }
    }

    public static class Shove extends BaseInstructions.Shove<IExecutable> {

        @Override
        public ListStack<IExecutable> getStack(Interpreter interpreter) {
            return interpreter.getExecListStack();
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 1;
        }
    }

    public static class StackDepth extends BaseInstructions.StackDepth<IExecutable> {

        @Override
        public ListStack<IExecutable> getStack(Interpreter interpreter) {
            return interpreter.getExecListStack();
        }
    }

    public static class Swap extends BaseInstructions.Swap<IExecutable> {

        @Override
        public ListStack<IExecutable> getStack(Interpreter interpreter) {
            return interpreter.getExecListStack();
        }

        @Override
        public int numRequiredCodeBlocks() {
            return 2;
        }
    }

    public static class Yank extends BaseInstructions.Yank<IExecutable> {

        @Override
        public ListStack<IExecutable> getStack(Interpreter interpreter) {
            return interpreter.getExecListStack();
        }
    }

    public static class YankDup extends BaseInstructions.YankDup<IExecutable> {

        @Override
        public ListStack<IExecutable> getStack(Interpreter interpreter) {
            return interpreter.getExecListStack();
        }
    }
}
