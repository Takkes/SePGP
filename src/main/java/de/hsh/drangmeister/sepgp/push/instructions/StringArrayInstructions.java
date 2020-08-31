package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.literals.BaseArrayLiteral;
import de.hsh.drangmeister.sepgp.push.literals.StringArrayLiteral;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.List;

/**
 * @author robin
 */
public class StringArrayInstructions {

    public static class Contains extends BaseArrayInstructions.Contains<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class EmptyArray extends BaseArrayInstructions.EmptyArray<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Empty extends BaseArrayInstructions.Empty<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Iterate extends BaseArrayInstructions.Iterate<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

        @Override
        protected BaseArrayLiteral<String> makeArrayLiteral(List<String> value) {
            return new StringArrayLiteral(value);
        }

    }

    public static class IndexOf extends BaseArrayInstructions.IndexOf<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class OccurrencesOf extends BaseArrayInstructions.OccurrencesOf<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Nth extends BaseArrayInstructions.Nth<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Set extends BaseArrayInstructions.Set<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Add extends BaseArrayInstructions.Add<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Remove extends BaseArrayInstructions.Remove<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Last extends BaseArrayInstructions.Last<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class First extends BaseArrayInstructions.First<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class ReplaceFirst extends BaseArrayInstructions.ReplaceFirst<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Replace extends BaseArrayInstructions.Replace<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class PushAll extends BaseArrayInstructions.PushAll<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class SubArray extends BaseArrayInstructions.SubArray<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Take extends BaseArrayInstructions.Take<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Length extends BaseArrayInstructions.Length<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Reverse extends BaseArrayInstructions.Reverse<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Rest extends BaseArrayInstructions.Rest<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class ButLast extends BaseArrayInstructions.ButLast<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Concat extends BaseArrayInstructions.Concat<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Equal extends BaseArrayInstructions.Equal<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Dup extends BaseArrayInstructions.Dup<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Flush extends BaseArrayInstructions.Flush<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Pop extends BaseArrayInstructions.Pop<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Rot extends BaseArrayInstructions.Rot<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Shove extends BaseArrayInstructions.Shove<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class StackDepth extends BaseArrayInstructions.StackDepth<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Swap extends BaseArrayInstructions.Swap<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class Yank extends BaseArrayInstructions.Yank<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

    public static class YankDup extends BaseArrayInstructions.YankDup<String> {

        @Override
        public ListStack<String> getLiteralStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }

        @Override
        public ListStack<List<String>> getArrayStack(Interpreter interpreter) {
            return interpreter.getStringArrayListStack();
        }

    }

}
