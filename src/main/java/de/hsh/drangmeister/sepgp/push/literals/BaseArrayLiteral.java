package de.hsh.drangmeister.sepgp.push.literals;

import de.hsh.drangmeister.sepgp.push.IExecutable;
import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.List;
import java.util.Objects;

/**
 * @author robin
 */
public abstract class BaseArrayLiteral<T> extends BaseLiteral{

    private List<T> list;

    protected abstract ListStack<List<T>> getStack(Interpreter interpreter);

    public BaseArrayLiteral(List<T> t) {
        this.list = t;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public boolean execute(Interpreter interpreter) {
        getStack(interpreter).push(list);
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
        final BaseArrayLiteral<?> other = (BaseArrayLiteral<?>) obj;
        return Objects.equals(this.list, other.list);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.list);
        return hash;
    }

    /**
     * @return the value
     */
    public List<T> getValue() {
        return list;
    }
}
