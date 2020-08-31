package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.IExecutable;

import java.util.Objects;

/**
 * @author robin
 */
public abstract class BaseInstruction implements IExecutable {

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        return this.getClass() == other.getClass();
    }

    public int numRequiredCodeBlocks() {
        return 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass());
    }

    @Override
    public String toString() {

        return this.getClass().getEnclosingClass().getSimpleName().replace("Instructions", "").toUpperCase()
                + "_"
                + this.getClass().getSimpleName().toUpperCase();

    }

}
