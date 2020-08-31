package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.Interpreter;

/**
 * @author robin
 */
public class PrintInstructions {

    /**
     * Adds the top boolean to the output
     */
    public static class PrintBoolean extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            if (interpreter.getBooleanListStack().isEmpty()) {
                return false;
            }

            interpreter.getOutputBuilder().append(interpreter.getBooleanListStack().pop());

            return true;
        }
    }

    /**
     * Adds the top char to the output
     */
    public static class PrintChar extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            if (interpreter.getCharListStack().isEmpty()) {
                return false;
            }

            interpreter.getOutputBuilder().append(interpreter.getCharListStack().pop());

            return true;
        }
    }

    /**
     * Adds the top float to the output
     */
    public static class PrintFloat extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            if (interpreter.getFloatListStack().isEmpty()) {
                return false;
            }

            interpreter.getOutputBuilder().append(interpreter.getFloatListStack().pop());

            return true;
        }
    }

    /**
     * Adds the top int to the output
     */
    public static class PrintInt extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            if (interpreter.getIntListStack().isEmpty()) {
                return false;
            }

            interpreter.getOutputBuilder().append(interpreter.getIntListStack().pop());

            return true;
        }
    }

    /**
     * Adds the top string to the output
     */
    public static class PrintString extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            if (interpreter.getStringListStack().isEmpty()) {
                return false;
            }

            interpreter.getOutputBuilder().append(interpreter.getStringListStack().pop());

            return true;
        }
    }

    /**
     * Adds the top float array to the output
     */
    public static class PrintFloatArray extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            if (interpreter.getFloatArrayListStack().isEmpty()) {
                return false;
            }

            interpreter.getOutputBuilder().append(interpreter.getFloatArrayListStack().pop());

            return true;
        }
    }

    /**
     * Adds the int string array to the output
     */
    public static class PrintIntArray extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            if (interpreter.getIntArrayListStack().isEmpty()) {
                return false;
            }

            interpreter.getOutputBuilder().append(interpreter.getIntArrayListStack().pop());

            return true;
        }
    }

    /**
     * Adds the top string array to the output
     */
    public static class PrintStringArray extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            if (interpreter.getStringArrayListStack().isEmpty()) {
                return false;
            }

            interpreter.getOutputBuilder().append(interpreter.getStringArrayListStack().pop());

            return true;
        }
    }

    /**
     * Adds a line break to the output
     */
    public static class PrintNewLine extends BaseInstruction {

        @Override
        public boolean execute(Interpreter interpreter) {

            interpreter.getOutputBuilder().append("\n");

            return true;
        }
    }

}
