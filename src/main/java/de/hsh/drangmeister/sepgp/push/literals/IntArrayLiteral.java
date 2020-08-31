package de.hsh.drangmeister.sepgp.push.literals;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.List;

/**
 * @author robin
 */
public class IntArrayLiteral extends BaseArrayLiteral<Integer> {

    public IntArrayLiteral(List<Integer> t) {
        super(t);
    }

    @Override
    protected ListStack<List<Integer>> getStack(Interpreter interpreter) {
        return interpreter.getIntArrayListStack();
    }

}
