package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.IExecutable;
import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.Program;
import de.hsh.drangmeister.sepgp.push.literals.BaseArrayLiteral;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author robin
 */
public class BaseArrayInstructions {

    interface IGenericCommonArrayInstruction<T> {

        ListStack<T> getLiteralStack(Interpreter interpreter);

        ListStack<List<T>> getArrayStack(Interpreter interpreter);

    }

    /**
     * Pushes onto the boolean stack whether the top array item contains the top
     * literal item
     *
     * @param <T>
     */
    protected abstract static class Contains<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<T> literalStack = getLiteralStack(interpreter);
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);
            if (literalStack.isEmpty() || arrayStack.isEmpty()) {
                return false;
            }
            interpreter.getBooleanListStack().push(
                    arrayStack.pop().contains(
                            literalStack.pop()));
            return true;
        }
    }

    /**
     * Pushes onto the boolean stack whether the top array item is empty
     *
     * @param <T>
     */
    protected abstract static class EmptyArray<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);
            if (arrayStack.isEmpty()) {
                return false;
            }
            interpreter.getBooleanListStack().push(arrayStack.peek().isEmpty());
            return true;
        }
    }

    /**
     * Pushes onto the boolean stack whether the stack is empty
     *
     * @param <T>
     */
    protected abstract static class Empty<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            interpreter.getBooleanListStack().push(getArrayStack(interpreter).isEmpty());
            return true;
        }
    }

    /**
     * Executes the top exec element for each element of the top array
     *
     * @param <T>
     */
    protected abstract static class Iterate<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        protected abstract BaseArrayLiteral<T> makeArrayLiteral(List<T> value);

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<T> literalStack = getLiteralStack(interpreter);
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);
            ListStack<IExecutable> execStack = interpreter.getExecListStack();

            if (execStack.isEmpty() || arrayStack.isEmpty()) {
                return false;
            }

            List<T> list = arrayStack.pop();
            if (list.isEmpty()) {
                return false;
            }
            IExecutable code = execStack.pop();

            literalStack.push(list.get(0));
            if (list.size() > 1) {
                list.remove(0);
                execStack.push(new Program(List.of(makeArrayLiteral(list), this, code)));
            }
            execStack.push(code);

            return true;
        }
    }

    /**
     * Pushes the index of the top literal element in the top array element onto
     * the int stack
     *
     * @param <T>
     */
    protected abstract static class IndexOf<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<T> literalStack = getLiteralStack(interpreter);
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);

            if (literalStack.isEmpty() || arrayStack.isEmpty()) {
                return false;
            }

            List<T> list = arrayStack.pop();
            T literal = literalStack.pop();

            interpreter.getIntListStack().push(list.indexOf(literal));

            return true;
        }
    }

    /**
     * Counts the number of occurences of the top literal element in the top
     * array element and pushes it onto the int stack
     *
     * @param <T>
     */
    protected abstract static class OccurrencesOf<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<T> literalStack = getLiteralStack(interpreter);
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);

            if (literalStack.isEmpty() || arrayStack.isEmpty()) {
                return false;
            }

            List<T> list = arrayStack.pop();
            T literal = literalStack.pop();

            int count = 0;
            for (T t : list) {
                if (t.equals(literal)) {
                    count++;
                }
            }
            interpreter.getIntListStack().push(count);

            return true;
        }
    }

    /**
     * Pushes the n-th element of the top array onto the literal stack. N is the
     * top integer
     *
     * @param <T>
     */
    protected abstract static class Nth<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);
            ListStack<Integer> intStack = interpreter.getIntListStack();

            if (intStack.isEmpty() || arrayStack.isEmpty() || arrayStack.peek().isEmpty()) {
                return false;
            }

            List<T> list = arrayStack.pop();
            int n = list.size();
            int indx = (((intStack.pop() % n) + n) % n);

            getLiteralStack(interpreter).push(list.get(indx));

            return true;
        }
    }

    /**
     * Replaces the n-th element of the top array with the top literal element.
     * N is the top integer
     *
     * @param <T>
     */
    protected abstract static class Set<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);
            ListStack<T> literalStack = getLiteralStack(interpreter);
            ListStack<Integer> intStack = interpreter.getIntListStack();
            if (intStack.isEmpty() || arrayStack.isEmpty() || literalStack.isEmpty() || arrayStack.peek().isEmpty()
                    || (literalStack == intStack && literalStack.size() < 2)) {
                return false;
            }

            List<T> list = arrayStack.peek();
            int n = list.size();
            int indx = (((intStack.pop() % n) + n) % n);
            T literal = literalStack.pop();
            list.set(indx, literal);

            return true;
        }
    }

    /**
     * Adds the top literal element to the top array element
     *
     * @param <T>
     */
    protected abstract static class Add<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);
            ListStack<T> literalStack = getLiteralStack(interpreter);

            if (literalStack.isEmpty() || arrayStack.isEmpty()) {
                return false;
            }

            arrayStack.peek().add(literalStack.pop());

            return true;
        }
    }

    /**
     * Removes all occurences of the top literal element from the top array
     * element
     *
     * @param <T>
     */
    protected abstract static class Remove<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);
            ListStack<T> literalStack = getLiteralStack(interpreter);

            if (literalStack.isEmpty() || arrayStack.isEmpty()) {
                return false;
            }

            T lit = literalStack.pop();
            while (arrayStack.peek().remove(lit)) {
            }

            return true;
        }
    }

    /**
     * Pushes the last item of the top array element onto the literal stack
     *
     * @param <T>
     */
    protected abstract static class Last<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);

            if (arrayStack.isEmpty()) {
                return false;
            }

            List<T> list = arrayStack.pop();

            if (!list.isEmpty())
                getLiteralStack(interpreter).push(list.get(list.size() - 1));

            return true;
        }
    }

    /**
     * Pushes the first item of the top array element onto the literal stack
     *
     * @param <T>
     */
    protected abstract static class First<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);

            if (arrayStack.isEmpty()) {
                return false;
            }

            List<T> list = arrayStack.pop();

            if (!list.isEmpty())
                getLiteralStack(interpreter).push(list.get(0));

            return true;
        }
    }

    /**
     * Replaces the first occurence of the second literal element in the top
     * array element with the first literal element
     *
     * @param <T>
     */
    protected abstract static class ReplaceFirst<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);
            ListStack<T> literalStack = getLiteralStack(interpreter);

            if (arrayStack.isEmpty() || literalStack.size() < 2) {
                return false;
            }

            List<T> list = arrayStack.peek();
            T replacer = literalStack.pop();
            T toReplace = literalStack.pop();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(toReplace)) {
                    list.set(i, replacer);
                    return true;
                }
            }

            return true;
        }
    }

    /**
     * Replaces all occurences of the second literal element in the top array
     * element with the first literal element
     *
     * @param <T>
     */
    protected abstract static class Replace<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);
            ListStack<T> literalStack = getLiteralStack(interpreter);

            if (arrayStack.isEmpty() || literalStack.size() < 2) {
                return false;
            }

            List<T> list = arrayStack.peek();
            T replacer = literalStack.pop();
            T toReplace = literalStack.pop();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(toReplace)) {
                    list.set(i, replacer);
                }
            }

            return true;
        }
    }

    /**
     * Pushes all elements of the top array element onto the literal stack in
     * reverse order
     *
     * @param <T>
     */
    protected abstract static class PushAll<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);

            if (arrayStack.isEmpty()) {
                return false;
            }

            ListStack<T> literalStack = getLiteralStack(interpreter);
            List<T> list = arrayStack.pop();

            for (int i = list.size() - 1; i >= 0; i--) {
                literalStack.push(list.get(i));
            }

            return true;
        }
    }

    /**
     * Pushes the subarray of the top array element from n to m onto the array
     * stack. m is the top int element, n is the second int element
     *
     * @param <T>
     */
    protected abstract static class SubArray<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);
            ListStack<Integer> intStack = interpreter.getIntListStack();

            if (intStack.size() < 2 || arrayStack.isEmpty()) {
                return false;
            }

            List<T> list = arrayStack.pop();
            int n = list.size();
            if (n == 0)
                return false;
            int to = intStack.pop();
            int from = intStack.pop();
            to = (((to % n) + n) % n);
            from = (((from % n) + n) % n);
            to = Math.max(to, from);

            List<T> newList = new ArrayList<>(to - from);
            for (int i = from; i < to; i++) {
                newList.add(list.get(i));
            }
            arrayStack.push(newList);

            return true;
        }
    }

    /**
     * Pushes the subarray of the top array element from 0 to n onto the array
     * stack. n is the top int element
     *
     * @param <T>
     */
    protected abstract static class Take<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);
            ListStack<Integer> intStack = interpreter.getIntListStack();

            if (intStack.isEmpty() || arrayStack.isEmpty()) {
                return false;
            }

            List<T> list = arrayStack.pop();
            if (list.isEmpty())
                return false;

            int n = list.size();
            int to = intStack.pop();
            to = (((to % n) + n) % n);

            List<T> newList = new ArrayList<>(to);
            for (int i = 0; i < to; i++) {
                newList.add(list.get(i));
            }
            arrayStack.push(newList);

            return true;
        }
    }

    /**
     * Pushes the size of the top array element onto the int stack
     *
     * @param <T>
     */
    protected abstract static class Length<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);

            if (arrayStack.isEmpty()) {
                return false;
            }

            interpreter.getIntListStack().push(arrayStack.pop().size());

            return true;
        }
    }

    /**
     * Reverses the top array element
     *
     * @param <T>
     */
    protected abstract static class Reverse<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);

            if (arrayStack.isEmpty()) {
                return false;
            }

            List<T> list = arrayStack.peek();
            int l = 0, r = list.size() - 1;
            while (l < r) {
                T left = list.get(l);
                list.set(l, list.get(r));
                list.set(r, left);
                l++;
                r--;
            }

            return true;
        }
    }

    /**
     * Removes the first element from the top array element
     *
     * @param <T>
     */
    protected abstract static class Rest<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);

            if (arrayStack.isEmpty() || arrayStack.peek().isEmpty()) {
                return false;
            }

            arrayStack.peek().remove(0);

            return true;
        }
    }

    /**
     * Removes the last element from the top array element
     *
     * @param <T>
     */
    protected abstract static class ButLast<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);

            if (arrayStack.isEmpty() || arrayStack.peek().isEmpty()) {
                return false;
            }

            arrayStack.peek().remove(arrayStack.peek().size() - 1);

            return true;
        }
    }

    protected abstract static class Concat<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> arrayStack = getArrayStack(interpreter);

            if (arrayStack.size() < 2) {
                return false;
            }

            List<T> from = arrayStack.pop();
            arrayStack.peek().addAll(from);

            return true;
        }
    }

    /**
     * Compares the top two elements for equality and pushes the result onto the
     * boolean stack
     */
    protected abstract static class Equal<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> stack = getArrayStack(interpreter);
            if (stack.size() < 2) {
                return false;
            }
            interpreter.getBooleanListStack().push(stack.pop().equals(stack.pop()));
            return true;
        }
    }

    protected abstract static class Dup<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> stack = getArrayStack(interpreter);
            if (stack.isEmpty()) {
                return false;
            }

            stack.push(new ArrayList<>(stack.peek()));

            return true;
        }
    }

    protected abstract static class Flush<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            getArrayStack(interpreter).flush();
            return true;
        }
    }

    protected abstract static class Pop<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<List<T>> stack = getArrayStack(interpreter);

            if (stack.isEmpty())
                return false;

            getArrayStack(interpreter).pop();
            return true;
        }
    }

    protected abstract static class Rot<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            return getArrayStack(interpreter).rot();
        }
    }

    protected abstract static class Shove<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Integer> intStack = interpreter.getIntListStack();
            if (intStack.isEmpty()) {
                return false;
            }
            getArrayStack(interpreter).shove(intStack.pop());
            return true;
        }
    }

    protected abstract static class StackDepth<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            interpreter.getIntListStack().push(getArrayStack(interpreter).size());
            return true;
        }
    }

    protected abstract static class Swap<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            getArrayStack(interpreter).swap();
            return true;
        }
    }

    protected abstract static class Yank<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Integer> intStack = interpreter.getIntListStack();
            if (intStack.isEmpty()) {
                return false;
            }
            return getArrayStack(interpreter).yank(intStack.pop());
        }
    }

    protected abstract static class YankDup<T> extends BaseInstruction implements IGenericCommonArrayInstruction<T> {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Integer> intStack = interpreter.getIntListStack();
            if (intStack.isEmpty()) {
                return false;
            }
            ListStack<List<T>> stack = getArrayStack(interpreter);
            if (stack.yankDup(intStack.pop())) {
                //Yankdup itself only creates a shallow copy with doesn't work with lists
                //Therefore we create a "deep" copy of the top element (which was yanked up by YankDup)
                stack.push(new ArrayList<>(stack.pop()));
                return true;
            }
            return false;
        }
    }

}
