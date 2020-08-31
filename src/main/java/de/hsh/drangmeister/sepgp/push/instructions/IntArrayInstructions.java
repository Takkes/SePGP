package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.literals.BaseArrayLiteral;
import de.hsh.drangmeister.sepgp.push.literals.IntArrayLiteral;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.List;

/**
 * @author robin
 */
public class IntArrayInstructions {

    public static class Contains extends BaseArrayInstructions.Contains<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class EmptyArray extends BaseArrayInstructions.EmptyArray<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Empty extends BaseArrayInstructions.Empty<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Iterate extends BaseArrayInstructions.Iterate<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

        @Override
        protected BaseArrayLiteral<Integer> makeArrayLiteral(List<Integer> value) {
            return new IntArrayLiteral(value);
        }

    }

    public static class IndexOf extends BaseArrayInstructions.IndexOf<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class OccurrencesOf extends BaseArrayInstructions.OccurrencesOf<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Nth extends BaseArrayInstructions.Nth<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Set extends BaseArrayInstructions.Set<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Add extends BaseArrayInstructions.Add<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Remove extends BaseArrayInstructions.Remove<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Last extends BaseArrayInstructions.Last<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class First extends BaseArrayInstructions.First<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class ReplaceFirst extends BaseArrayInstructions.ReplaceFirst<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Replace extends BaseArrayInstructions.Replace<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class PushAll extends BaseArrayInstructions.PushAll<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class SubArray extends BaseArrayInstructions.SubArray<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Take extends BaseArrayInstructions.Take<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Length extends BaseArrayInstructions.Length<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Reverse extends BaseArrayInstructions.Reverse<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Rest extends BaseArrayInstructions.Rest<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class ButLast extends BaseArrayInstructions.ButLast<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Concat extends BaseArrayInstructions.Concat<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Equal extends BaseArrayInstructions.Equal<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Dup extends BaseArrayInstructions.Dup<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Flush extends BaseArrayInstructions.Flush<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Pop extends BaseArrayInstructions.Pop<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Rot extends BaseArrayInstructions.Rot<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Shove extends BaseArrayInstructions.Shove<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class StackDepth extends BaseArrayInstructions.StackDepth<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Swap extends BaseArrayInstructions.Swap<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class Yank extends BaseArrayInstructions.Yank<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

    public static class YankDup extends BaseArrayInstructions.YankDup<Integer> {

        @Override
        public ListStack<Integer> getLiteralStack(Interpreter interpreter) {
            return interpreter.getIntListStack();
        }

        @Override
        public ListStack<List<Integer>> getArrayStack(Interpreter interpreter) {
            return interpreter.getIntArrayListStack();
        }

    }

}
