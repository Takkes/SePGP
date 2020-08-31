package de.hsh.drangmeister.sepgp.push.literals;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

/**
 * @author robin
 */
public class CharLiteral extends BaseIndividualLiteral<Character> {

    public CharLiteral(char t) {
        super(t);
    }

    @Override
    protected ListStack<Character> getStack(Interpreter interpreter) {
        return interpreter.getCharListStack();
    }

    @Override
    public String toString() {
        return "\'" + (this.getValue() == '\n' ? "\\n" : this.getValue()) + "\'";
    }

}
