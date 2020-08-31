package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author robin
 */
public class StringInstructions {

    /**
     * Checks whether the top element of the string stack contains the top
     * element of the char stack and pushes the result onto the boolean stack
     */
    public static class ContainsChar extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (stringStack.isEmpty() || charStack.isEmpty()) {
                return false;
            }

            char c = charStack.pop();
            String s = stringStack.pop();
            interpreter.getBooleanListStack().push(s.indexOf(c) != -1);

            return true;
        }
    }

    /**
     * Checks whether the top element of the string stack is empty and pushes
     * the result onto the boolean stack
     */
    public static class IsEmpty extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.isEmpty()) {
                return false;
            }

            interpreter.getBooleanListStack().push(stringStack.pop().isEmpty());

            return true;
        }
    }

    /**
     * Pushes the string representation of the top element of the boolean stack
     * onto the string stack
     */
    public static class FromBoolean extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Boolean> boolstack = interpreter.getBooleanListStack();

            if (boolstack.isEmpty()) {
                return false;
            }

            interpreter.getStringListStack().push(boolstack.pop().toString());

            return true;
        }
    }

    /**
     * Checks whether the top element of the string stack is a substring of the
     * second element of the string stack and pushes the result onto the boolean
     * stack
     */
    public static class Contains extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.size() < 2) {
                return false;
            }

            String s1 = stringStack.pop();
            String s2 = stringStack.pop();

            interpreter.getBooleanListStack().push(s2.contains(s1));

            return true;
        }
    }

    /**
     * Pushes the empty string onto the string stack
     */
    public static class EmptyString extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            interpreter.getStringListStack().push("");

            return true;
        }
    }

    /**
     * Counts the number of occurences of the top character in the top string
     * and pushes the result onto the int stack
     */
    public static class OccurrencesOfChar extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (stringStack.isEmpty() || charStack.isEmpty()) {
                return false;
            }

            String s = stringStack.pop();
            char c = charStack.pop();

            interpreter.getIntListStack().push(StringUtils.countMatches(s, c));

            return true;
        }
    }

    /**
     * Sets the n-th character of the top string to the top character, where n
     * is the top integer
     */
    public static class SetNthChar extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();
            ListStack<Character> charStack = interpreter.getCharListStack();
            ListStack<Integer> intStack = interpreter.getIntListStack();

            if (stringStack.isEmpty() || charStack.isEmpty() || intStack.isEmpty() || stringStack.peek().isEmpty()) {
                return false;
            }

            String s = stringStack.pop();
            char c = charStack.pop();
            int indx = intStack.pop();

            int len = s.length();
            //Changes modulo behaviour such that -1 becomes len - 1
            indx = (((indx % len) + len) % len);

            stringStack.push(s.substring(0, indx) + c + s.substring(indx + 1));

            return true;
        }
    }

    /**
     * Pushes the n-th character of the top string onto the character stack,
     * where n is the top integer
     */
    public static class NthChar extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();
            ListStack<Integer> intStack = interpreter.getIntListStack();

            if (stringStack.isEmpty() || intStack.isEmpty() || stringStack.peek().isEmpty()) {
                return false;
            }

            String s = stringStack.pop();
            int indx = intStack.pop();

            int len = s.length();
            //Changes modulo behaviour such that -1 becomes len - 1
            indx = (((indx % len) + len) % len);

            interpreter.getCharListStack().push(s.charAt(indx));

            return true;
        }
    }

    /**
     * Pushes the the index of the first occurrence of the top character in the
     * top string onto the int stack
     */
    public static class IndexOfChar extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (stringStack.isEmpty() || charStack.isEmpty() || stringStack.peek().isEmpty()) {
                return false;
            }

            String s = stringStack.pop();
            char c = charStack.pop();

            interpreter.getIntListStack().push(s.indexOf(c));

            return true;
        }
    }

    public static class RemoveChar extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (stringStack.isEmpty() || charStack.isEmpty()) {
                return false;
            }

            String s = stringStack.pop();
            char c = charStack.pop();

            stringStack.push(s.replaceAll(Pattern.quote(String.valueOf(c)), ""));

            return true;
        }
    }

    /**
     * Replaces all occurences of second char in top string with first char
     */
    public static class ReplaceChar extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (stringStack.isEmpty() || charStack.size() < 2) {
                return false;
            }

            String s = stringStack.pop();
            char newchar = charStack.pop();
            char oldchar = charStack.pop();

            stringStack.push(s.replaceAll(Pattern.quote(String.valueOf(oldchar)), Matcher.quoteReplacement(String.valueOf(newchar))));

            return true;
        }
    }

    /**
     * Replaces first occurence of second char in top string with first char
     */
    public static class ReplaceFirstChar extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (stringStack.isEmpty() || charStack.size() < 2) {
                return false;
            }

            String s = stringStack.pop();
            char newchar = charStack.pop();
            char oldchar = charStack.pop();

            stringStack.push(s.replaceFirst(Pattern.quote(String.valueOf(oldchar)), Matcher.quoteReplacement(String.valueOf(newchar))));

            return true;
        }
    }

    /**
     * Concatenates the top character with the top string and pushes the result
     * onto the string stack
     */
    public static class ConcatChar extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (stringStack.isEmpty() || charStack.isEmpty()) {
                return false;
            }

            String s = stringStack.pop();
            char c = charStack.pop();

            stringStack.push(s + c);

            return true;
        }
    }

    /**
     * Converts the top character to string and pushes that onto the string
     * stack
     */
    public static class FromChar extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Character> charStack = interpreter.getCharListStack();

            if (charStack.isEmpty()) {
                return false;
            }

            interpreter.getStringListStack().push(String.valueOf(charStack.pop()));

            return true;
        }
    }

    /**
     * Pushes the first charactet of the top string onto the character stack
     */
    public static class First extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.isEmpty() || stringStack.peek().isEmpty()) {
                return false;
            }

            interpreter.getCharListStack().push(stringStack.pop().charAt(0));

            return true;
        }
    }

    /**
     * Pushes the last charactet of the top string onto the character stack
     */
    public static class Last extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.isEmpty() || stringStack.peek().isEmpty()) {
                return false;
            }

            String s = stringStack.pop();
            interpreter.getCharListStack().push(s.charAt(s.length() - 1));

            return true;
        }
    }

    /**
     * Pushes a string representation of the top float onto the string stack
     */
    public static class FromFloat extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Float> floatStack = interpreter.getFloatListStack();

            if (floatStack.isEmpty()) {
                return false;
            }

            interpreter.getStringListStack().push(String.valueOf(floatStack.pop()));

            return true;
        }
    }

    /**
     * Pushes the substring from startIndx (inclusive) to endIndx (exclusive) of
     * the top string to the string stack. startIndx = second integer on int
     * stack, endIndx is top integer on int stack startIndx will be adjusted to
     * range (0, stringLen) endIndx will be adjusted to range (startIndx,
     * stringLen)
     */
    public static class Substring extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();
            ListStack<Integer> intStack = interpreter.getIntListStack();

            if (stringStack.isEmpty() || intStack.size() < 2 || stringStack.peek().isEmpty()) {
                return false;
            }

            String s = stringStack.pop();
            int to = intStack.pop();
            int from = intStack.pop();

            int len = s.length();

            to = (((to % len) + len) % len);
            from = (((from % len) + len) % len);
            to = Math.max(to, from);

            interpreter.getStringListStack().push(s.substring(from, to));

            return true;
        }
    }

    /**
     * Takes the substring [0, n) of the top string and pushed it onto the
     * string stack. N is the top integer
     */
    public static class Take extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();
            ListStack<Integer> intStack = interpreter.getIntListStack();

            if (stringStack.isEmpty() || intStack.isEmpty()) {
                return false;
            }

            String s = stringStack.pop();
            int num = intStack.pop();

            int len = s.length();

            num = Math.max(0, Math.min(len, num));

            interpreter.getStringListStack().push(s.substring(0, num));

            return true;
        }
    }

    /**
     * Pushes a string representation of the top integer onto the string stack
     */
    public static class FromInt extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<Integer> intStack = interpreter.getIntListStack();

            if (intStack.isEmpty()) {
                return false;
            }

            interpreter.getStringListStack().push(String.valueOf(intStack.pop()));

            return true;
        }
    }

    /**
     * Pushes the length of the top string onto the integer stack
     */
    public static class Length extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.isEmpty()) {
                return false;
            }

            interpreter.getIntListStack().push(stringStack.pop().length());

            return true;
        }
    }

    /**
     * Pushes the top string without its first character onto the string stack
     */
    public static class Rest extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.isEmpty() || stringStack.peek().isEmpty()) {
                return false;
            }

            stringStack.push(stringStack.pop().substring(1));

            return true;
        }
    }

    /**
     * Pushes all characters of the top string onto the string stack in reverse
     * order
     */
    public static class ParseToChars extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.isEmpty() || stringStack.peek().isEmpty()) {
                return false;
            }

            char[] chars = stringStack.pop().toCharArray();
            for (int i = chars.length - 1; i >= 0; i--) {
                stringStack.push(String.valueOf(chars[i]));
            }

            return true;
        }
    }

    /**
     * Reverses the top string
     */
    public static class Reverse extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.isEmpty()) {
                return false;
            }

            stringStack.push(new StringBuilder(stringStack.pop()).reverse().toString());

            return true;
        }
    }

    /**
     * Splits the top string at whitespaces and pushes the resulting strings in
     * reverse order onto the string stack
     */
    public static class SplitOnSpace extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.isEmpty() || stringStack.peek().isBlank()) {
                return false;
            }

            String[] words = stringStack.pop().trim().split("\\s+");
            for (int i = words.length - 1; i >= 0; i--) {
                stringStack.push(words[i]);
            }

            return true;
        }
    }

    /**
     * Replaces first occurence of second string on third string with first
     * string
     */
    public static class ReplaceFirst extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.size() < 3) {
                return false;
            }

            String newString = stringStack.pop();
            String oldString = stringStack.pop();
            String repString = stringStack.pop();

            stringStack.push(repString.replaceFirst(Pattern.quote(String.valueOf(oldString)), Matcher.quoteReplacement(String.valueOf(newString))));

            return true;
        }
    }

    /**
     * Replaces all occurences of second string on third string with first
     * string
     */
    public static class Replace extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.size() < 3) {
                return false;
            }

            String newString = stringStack.pop();
            String oldString = stringStack.pop();
            String repString = stringStack.pop();

            stringStack.push(repString.replaceAll(Pattern.quote(String.valueOf(oldString)), Matcher.quoteReplacement(String.valueOf(newString))));

            return true;
        }
    }

    /**
     * Pushes the top string without its last character onto the string stack
     */
    public static class ButLast extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.isEmpty() || stringStack.peek().isEmpty()) {
                return false;
            }

            String s = stringStack.pop();
            stringStack.push(s.substring(0, s.length() - 1));

            return true;
        }
    }

    /**
     * Concatenates top two strings and pushes result onto string stack
     */
    public static class Concat extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {
            ListStack<String> stringStack = interpreter.getStringListStack();

            if (stringStack.size() < 2) {
                return false;
            }

            if (stringStack.peek().length() > interpreter.getMaxStringLength() - stringStack.get(1).length()) {
                //Every now and then a program might create a string that is way too long which results in a OutOfMemoryError.
                //We really don't want that
                return false;
            }

            String s1 = stringStack.pop();
            String s2 = stringStack.pop();

            stringStack.push(s2 + s1);

            return true;
        }
    }

    //**************************************************************************
    //                 Common Instructions from here on
    //**************************************************************************
    public static class Equal extends BaseInstructions.Equal<String> {

        @Override
        public ListStack<String> getStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }
    }

    public static class Empty extends BaseInstructions.Empty<String> {

        @Override
        public ListStack<String> getStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }
    }

    public static class Dup extends BaseInstructions.Dup<String> {

        @Override
        public ListStack<String> getStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }
    }

    public static class Flush extends BaseInstructions.Flush<String> {

        @Override
        public ListStack<String> getStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }
    }

    public static class Pop extends BaseInstructions.Pop<String> {

        @Override
        public ListStack<String> getStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }
    }

    public static class Rot extends BaseInstructions.Rot<String> {

        @Override
        public ListStack<String> getStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }
    }

    public static class Shove extends BaseInstructions.Shove<String> {

        @Override
        public ListStack<String> getStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }
    }

    public static class StackDepth extends BaseInstructions.StackDepth<String> {

        @Override
        public ListStack<String> getStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }
    }

    public static class Swap extends BaseInstructions.Swap<String> {

        @Override
        public ListStack<String> getStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }
    }

    public static class Yank extends BaseInstructions.Yank<String> {

        @Override
        public ListStack<String> getStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }
    }

    public static class YankDup extends BaseInstructions.YankDup<String> {

        @Override
        public ListStack<String> getStack(Interpreter interpreter) {
            return interpreter.getStringListStack();
        }
    }

}
