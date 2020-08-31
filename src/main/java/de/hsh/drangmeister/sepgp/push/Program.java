package de.hsh.drangmeister.sepgp.push;

import de.hsh.drangmeister.sepgp.push.util.ListStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author robin
 */
public class Program implements IExecutable {

    protected List<IExecutable> executables;

    protected Program parentProgram;

    //Indicates whether this program has already been processed during conversion from plush to push
    private boolean processed = false;

    public Program(){
        this(new ArrayList<>());
    }

    public Program(Program parentProgramm) {
        executables = new ArrayList<>();
        this.parentProgram = parentProgramm;
    }

    public Program(List<IExecutable> executables) {
        this.executables = executables;
    }

    public void addExecutable(IExecutable executable) {
        executables.add(executable);
    }

    @Override
    public boolean execute(Interpreter interpreter) {
        ListStack<IExecutable> execStackList = interpreter.getExecListStack();
        for (int i = executables.size() - 1; i >= 0; i--) {
            execStackList.push(executables.get(i));
        }
        return true;
    }

    @Override
    public String toString() {

        String[] individualStrings = new String[executables.size()];
        int indx = 0;
        for (IExecutable e : executables) {
            individualStrings[indx++] = e.toString();
        }

        return "( " + String.join(" ", individualStrings) + " )";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.executables);
        return hash;
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
        final Program other = (Program) obj;
        return Objects.equals(this.executables, other.executables);
    }

    public Program getParentProgram() {
        return parentProgram;
    }

    public List<IExecutable> getExecutables() {
        return executables;
    }

    public boolean hasBeenProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
