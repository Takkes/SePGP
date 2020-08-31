package de.hsh.drangmeister.sepgp.push.instructions;

import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.function.Supplier;

/**
 * @author robin
 */
public class CommonInstructions {

    /**
     * Executes the given supplier only if the given stack is not empty.
     *
     * @param <T>
     * @param stack
     * @param sup
     * @return false if given stack is empty, result of supplier otherweise
     */
    public static <T> boolean safeExecute(ListStack<T> stack, Supplier<Boolean> sup) {
        if (stack.isEmpty())
            return false;
        return sup.get();
    }
}
