package de.hsh.drangmeister.sepgp.push.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a list-backed stack-implementation to augment the usage of a stack to
 * the client but also have O(1) access to arbitrary elements. This
 * implementations is designed to fail gracefully if any operation can't be
 * performed.
 *
 * @param <T>
 * @author robin
 */
public class ListStack<T> {

    private List<T> list;

    public ListStack() {
        this.list = new ArrayList<>();
    }

    public ListStack(int initialCapacity) {
        this.list = new ArrayList<>(initialCapacity);
    }

    /**
     * Removes and returns top element
     *
     * @return
     */
    public T pop() {
        T t = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        return t;
    }

    /**
     * Removes all elements
     */
    public void flush() {
        list.clear();
    }

    /**
     * Changes order of last three elements. Example: [..., x, y, z] => [..., y,
     * z, x];
     *
     * @return true if successful, false if there are less than three elements
     */
    public boolean rot() {
        int n = list.size();
        if (n < 3) {
            return false;
        }
        T third = list.get(n - 3);
        list.set(n - 3, list.get(n - 2));
        list.set(n - 2, list.get(n - 1));
        list.set(n - 1, third);
        return true;
    }

    /**
     * Moves the top element of stackList to index given by intStack
     *
     * @param indx will be adjusted if out of range
     */
    public void shove(int indx) {

        if(list.isEmpty())
            return;

        int n = list.size() - 1;
        if (indx > n) {
            indx = n;
        } else if (indx < 0) {
            indx = 0;
        }

        T elem = pop();
        list.add(n - indx, elem);
    }

    /**
     * Swaps top two elements
     *
     * @return true if succesful, false if there are less than two elements
     */
    public boolean swap() {
        int n = list.size();
        if (n < 2) {
            return false;
        }

        T tmp = list.get(n - 1);
        list.set(n - 1, list.get(n - 2));
        list.set(n - 2, tmp);

        return true;
    }

    /**
     * Removes element of stackList at index specified by int stack and adds it
     * at the top of stacklist
     *
     * @param indx will be adjusted if out of range
     * @return true if succesful, false if stack is empty
     */
    public boolean yank(int indx) {

        return yankInternal(indx, true);
    }

    /**
     * Copies element of stackList at index specified by int stack and adds it
     * at the top of stacklist
     *
     * @param indx will be adjusted if out of range
     * @return true if succesful, false if stack is empty
     */
    public boolean yankDup(int indx) {

        return yankInternal(indx, false);
    }

    /**
     * Called by yank and yankdup - only difference between the two is that
     * yankdup doesnt remove the element that is added to the top of the stack
     *
     * @param indx   will be adjusted if out of range
     * @param remove
     * @return true if succesful, false if stack is empty
     */
    private boolean yankInternal(int indx, boolean remove) {

        if (list.isEmpty()) {
            return false;
        }

        int n = list.size();

        if (indx > n - 1) {
            indx = n - 1;
        } else if (indx < 0) {
            indx = 0;
        }

        int rmIndx = n - 1 - indx;
        T elem = list.get(rmIndx);
        if (remove) {
            list.remove(rmIndx);
        }
        list.add(elem);

        return true;
    }

    /**
     * Duplicates top element
     *
     * @return true if succesful, false if stack is empty
     */
    public boolean dup() {
        if (list.isEmpty()) {
            return false;
        }

        list.add(list.get(list.size() - 1));

        return true;
    }

    /**
     * Returns size of stack
     *
     * @return size of stack - what else?
     */
    public int size() {
        return list.size();
    }

    /**
     * Pushes element on top of stack
     *
     * @param t
     */
    public void push(T t) {
        list.add(t);
    }

    /**
     * Returns element at specified index - CAUTION: Index starts with top
     * element of stack
     *
     * @param indx starting from the top of the stack; will be adjusted if out
     *             of range
     * @return
     */
    public T get(int indx) {
        if (indx > list.size() - 1) {
            indx = list.size() - 1;
        } else if (indx < 0) {
            indx = 0;
        }

        return list.get(list.size() - 1 - indx);
    }

    /**
     * Returns the top element of the stack without popping it
     *
     * @return
     */
    public T peek() {
        return list.get(list.size() - 1);
    }

    /**
     * Sets the element at specified index - CAUTION: Index starts with top
     * element of stack
     *
     * @param indx starting from the top of the stack; will be adjusted if out
     *             of range
     * @param t
     */
    public void set(int indx, T t) {
        if (indx > list.size() - 1) {
            indx = list.size() - 1;
        } else if (indx < 0) {
            indx = 0;
        }

        list.set(list.size() - 1 - indx, t);
    }

    /**
     * Removes the element at the given index from the stack. Index starts with
     * top element of the stack.
     *
     * @param indx starting from the top of the stack; will be adjusted if out
     *             of range
     */
    public void remove(int indx) {
        if (indx > list.size() - 1) {
            indx = list.size() - 1;
        } else if (indx < 0) {
            indx = 0;
        }

        list.remove(list.size() - 1 - indx);
    }

    /**
     * Returns element at specified index starting from bottom of stack
     *
     * @param indx starting from bottom of stack; will be adjusted if out of
     *             range
     * @return
     */
    public T getFromBottom(int indx) {
        if (indx > list.size() - 1) {
            indx = list.size() - 1;
        } else if (indx < 0) {
            indx = 0;
        }

        return list.get(indx);
    }

    /**
     * @return whether stack is empty or not
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns whether the given element is present in the stack
     *
     * @param t
     * @return
     */
    public boolean contains(T t) {
        return list.contains(t);
    }

}
