package de.hsh.drangmeister.sepgp.push.literals;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

/**
 * @author robin
 */
public class FloatLiteral extends BaseIndividualLiteral<Float> {

    public FloatLiteral(float t) {
        super(t);
    }

    @Override
    protected ListStack<Float> getStack(Interpreter interpreter) {
        return interpreter.getFloatListStack();
    }

}
