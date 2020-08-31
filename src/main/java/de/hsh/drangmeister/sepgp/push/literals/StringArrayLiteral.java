package de.hsh.drangmeister.sepgp.push.literals;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.List;

/**
 * @author robin
 */
public class StringArrayLiteral extends BaseArrayLiteral<String> {

    public StringArrayLiteral(List<String> t) {
        super(t);
    }

    @Override
    protected ListStack<List<String>> getStack(Interpreter interpreter) {
        return interpreter.getStringArrayListStack();
    }

}
