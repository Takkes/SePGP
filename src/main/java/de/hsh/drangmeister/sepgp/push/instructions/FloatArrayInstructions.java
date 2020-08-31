package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.literals.BaseArrayLiteral;
import de.hsh.drangmeister.sepgp.push.literals.FloatArrayLiteral;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.List;

/**
 * @author robin
 */
public class FloatArrayInstructions {

    public static class Contains extends BaseArrayInstructions.Contains<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class EmptyArray extends BaseArrayInstructions.EmptyArray<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Empty extends BaseArrayInstructions.Empty<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Iterate extends BaseArrayInstructions.Iterate<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

        @Override
        protected BaseArrayLiteral<Float> makeArrayLiteral(List<Float> value) {
            return new FloatArrayLiteral(value);
        }

    }

    public static class IndexOf extends BaseArrayInstructions.IndexOf<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class OccurrencesOf extends BaseArrayInstructions.OccurrencesOf<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Nth extends BaseArrayInstructions.Nth<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Set extends BaseArrayInstructions.Set<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Add extends BaseArrayInstructions.Add<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Remove extends BaseArrayInstructions.Remove<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Last extends BaseArrayInstructions.Last<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class First extends BaseArrayInstructions.First<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class ReplaceFirst extends BaseArrayInstructions.ReplaceFirst<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Replace extends BaseArrayInstructions.Replace<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class PushAll extends BaseArrayInstructions.PushAll<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class SubArray extends BaseArrayInstructions.SubArray<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Take extends BaseArrayInstructions.Take<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Length extends BaseArrayInstructions.Length<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Reverse extends BaseArrayInstructions.Reverse<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Rest extends BaseArrayInstructions.Rest<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class ButLast extends BaseArrayInstructions.ButLast<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Concat extends BaseArrayInstructions.Concat<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Equal extends BaseArrayInstructions.Equal<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Dup extends BaseArrayInstructions.Dup<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Flush extends BaseArrayInstructions.Flush<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Pop extends BaseArrayInstructions.Pop<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Rot extends BaseArrayInstructions.Rot<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Shove extends BaseArrayInstructions.Shove<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class StackDepth extends BaseArrayInstructions.StackDepth<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Swap extends BaseArrayInstructions.Swap<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class Yank extends BaseArrayInstructions.Yank<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

    public static class YankDup extends BaseArrayInstructions.YankDup<Float> {

        @Override
        public ListStack<Float> getLiteralStack(Interpreter interpreter) {
            return interpreter.getFloatListStack();
        }

        @Override
        public ListStack<List<Float>> getArrayStack(Interpreter interpreter) {
            return interpreter.getFloatArrayListStack();
        }

    }

}
