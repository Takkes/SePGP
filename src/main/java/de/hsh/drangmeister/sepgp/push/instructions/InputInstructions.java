package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.literals.BaseIndividualLiteral;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

/**
 * @author robin
 */
public class InputInstructions {

    /**
     * Pushes a pre-defined input value onto the corresponding stack
     */
    public static class InputValue extends BaseInstruction {

        private int indx;

        public InputValue(int indx) {
            this.indx = indx;
        }

        @Override
        public boolean execute(Interpreter interpreter) {

            //Execute it instead of pushing it onto the exec stack. This way the
            //value directly ends up on the corresponding stack. If we pushed it
            //onto the exec stack first the "unboxing" would take up
            //another instruction which would make
            //"pushing an input value" a 2-instruction action
            interpreter.getInputListStack().get(indx).execute(interpreter);

            return true;
        }

        @Override
        public String toString() {

            return "INPUT_" + indx;

        }
    }

}
