package de.hsh.drangmeister.sepgp.push.literals;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

/**
 * @author robin
 */
public class IntLiteral extends BaseIndividualLiteral<Integer> {

    public IntLiteral(int t) {
        super(t);
    }

    @Override
    protected ListStack<Integer> getStack(Interpreter interpreter) {
        return interpreter.getIntListStack();
    }

}
