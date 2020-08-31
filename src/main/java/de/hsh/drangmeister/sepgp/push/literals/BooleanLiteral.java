package de.hsh.drangmeister.sepgp.push.literals;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

/**
 * @author robin
 */
public class BooleanLiteral extends BaseIndividualLiteral<Boolean> {

    public BooleanLiteral(boolean value) {
        super(value);
    }

    @Override
    protected ListStack<Boolean> getStack(Interpreter interpreter) {
        return interpreter.getBooleanListStack();
    }

}
