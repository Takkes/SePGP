package de.hsh.drangmeister.sepgp.push.literals;

import de.hsh.drangmeister.sepgp.push.IExecutable;
import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.Objects;

/**
 * @param <T>
 * @author robin
 */
public abstract class BaseIndividualLiteral<T> extends BaseLiteral {

    private T value;

    protected abstract ListStack<T> getStack(Interpreter interpreter);

    public BaseIndividualLiteral(T t) {
        this.value = t;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean execute(Interpreter interpreter) {
        getStack(interpreter).push(value);
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseIndividualLiteral<?> other = (BaseIndividualLiteral<?>) obj;
        return Objects.equals(this.value, other.value);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.value);
        return hash;
    }

    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

}
