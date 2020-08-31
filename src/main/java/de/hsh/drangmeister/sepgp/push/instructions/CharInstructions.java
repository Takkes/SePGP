package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

/**
 * @author robin
 */
public class CharInstructions extends BaseInstructions {

    /**
     * Pushes onto the boolean stack whether top char is whitespace
     */
    public static class IsWhitespace extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (charStack.isEmpty()) {
                return false;
            }

            interpreter.getBooleanListStack().push(Character.isWhitespace(charStack.pop()));

            return true;
        }
    }

    /**
     * Pushes onto the boolean stack whether top char is a letter
     */
    public static class IsLetter extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (charStack.isEmpty()) {
                return false;
            }

            interpreter.getBooleanListStack().push(Character.isLetter(charStack.pop()));

            return true;
        }
    }

    /**
     * Pushes onto the boolean stack whether top char is a digit
     */
    public static class IsDigit extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (charStack.isEmpty()) {
                return false;
            }

            interpreter.getBooleanListStack().push(Character.isDigit(charStack.pop()));

            return true;
        }
    }

    /**
     * Casts the top float value to int, brings it into range [0, 127] and
     * pushes it onto char stack
     */
    public static class FromFloat extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Float> floatStack = interpreter.getFloatListStack();

            if (floatStack.isEmpty()) {
                return false;
            }

            //Bring float value in range [0, 127]
            char c = (char) (((((int) floatStack.pop().floatValue()) % 128) + 128) % 128);
            interpreter.getCharListStack().push(c);

            return true;
        }
    }

    /**
     * Brings the top int vallue into range [0, 127] and pushes it onto char
     * stack
     */
    public static class FromInt extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Integer> intStack = interpreter.getIntListStack();

            if (intStack.isEmpty()) {
                return false;
            }

            //Bring float value in range [0, 127]
            char c = (char) (((intStack.pop().floatValue() % 128) + 128) % 128);
            interpreter.getCharListStack().push(c);

            return true;
        }
    }

    /**
     * Pushes all characters of the top string onto the char stack in reverse
     * order
     */
    public static class AllFromString extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (stringStack.isEmpty() || stringStack.peek().isEmpty()) {
                return false;
            }

            char[] chars = stringStack.pop().toCharArray();
            for (int i = chars.length - 1; i >= 0; i--) {
                charStack.push(chars[i]);
            }

            return true;
        }
    }

    //**************************************************************************
    //                 Common Instructions from here on
    //**************************************************************************
    public static class Equal extends BaseInstructions.Equal<Character> {

        @Override
        public ListStack<Character> getStack(Interpreter interpreter) {
            return interpreter.getCharListStack();
        }
    }

    public static class Empty extends BaseInstructions.Empty<Character> {

        @Override
        public ListStack<Character> getStack(Interpreter interpreter) {
            return interpreter.getCharListStack();
        }
    }

    public static class Dup extends BaseInstructions.Dup<Character> {

        @Override
        public ListStack<Character> getStack(Interpreter interpreter) {
            return interpreter.getCharListStack();
        }
    }

    public static class Flush extends BaseInstructions.Flush<Character> {

        @Override
        public ListStack<Character> getStack(Interpreter interpreter) {
            return interpreter.getCharListStack();
        }
    }

    public static class Pop extends BaseInstructions.Pop<Character> {

        @Override
        public ListStack<Character> getStack(Interpreter interpreter) {
            return interpreter.getCharListStack();
        }
    }

    public static class Rot extends BaseInstructions.Rot<Character> {

        @Override
        public ListStack<Character> getStack(Interpreter interpreter) {
            return interpreter.getCharListStack();
        }
    }

    public static class Shove extends BaseInstructions.Shove<Character> {

        @Override
        public ListStack<Character> getStack(Interpreter interpreter) {
            return interpreter.getCharListStack();
        }
    }

    public static class StackDepth extends BaseInstructions.StackDepth<Character> {

        @Override
        public ListStack<Character> getStack(Interpreter interpreter) {
            return interpreter.getCharListStack();
        }
    }

    public static class Swap extends BaseInstructions.Swap<Character> {

        @Override
        public ListStack<Character> getStack(Interpreter interpreter) {
            return interpreter.getCharListStack();
        }
    }

    public static class Yank extends BaseInstructions.Yank<Character> {

        @Override
        public ListStack<Character> getStack(Interpreter interpreter) {
            return interpreter.getCharListStack();
        }
    }

    public static class YankDup extends BaseInstructions.YankDup<Character> {

        @Override
        public ListStack<Character> getStack(Interpreter interpreter) {
            return interpreter.getCharListStack();
        }
    }

}
