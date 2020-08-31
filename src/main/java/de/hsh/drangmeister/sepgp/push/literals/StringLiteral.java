package de.hsh.drangmeister.sepgp.push.literals;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

/**
 * @author robin
 */
public class StringLiteral extends BaseIndividualLiteral<String> {

    public StringLiteral(String t) {
        super(t);
    }

    @Override
    protected ListStack<String> getStack(Interpreter interpreter) {
        return interpreter.getStringListStack();
    }

    @Override
    public String toString() {
        return "\"" + this.getValue() + "\"";
    }

}
