package de.hsh.drangmeister.sepgp.push.literals;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.List;

/**
 * @author robin
 */
public class FloatArrayLiteral extends BaseArrayLiteral<Float> {

    public FloatArrayLiteral(List<Float> t) {
        super(t);
    }

    @Override
    protected ListStack<List<Float>> getStack(Interpreter interpreter) {
        return interpreter.getFloatArrayListStack();
    }

}
