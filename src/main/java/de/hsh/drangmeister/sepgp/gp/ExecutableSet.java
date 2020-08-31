package de.hsh.drangmeister.sepgp.gp;

import de.hsh.drangmeister.sepgp.gp.erc.ERCGenerator;
import de.hsh.drangmeister.sepgp.push.IExecutable;
import de.hsh.drangmeister.sepgp.push.literals.BaseLiteral;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class ExecutableSet {

    private List<IExecutable> instructionList;
    private List<BaseLiteral> literals;
    private List<ERCGenerator> ercGenerators;

    private int instructionCount;
    private int instructionAndLiteralCount;
    private int instructionLiteralAndErcCount;

    private SecureRandom random;

    public ExecutableSet(List<IExecutable> instructionList, List<BaseLiteral> literals, List<ERCGenerator> ercGenerators) {
        this.instructionList = instructionList;
        this.literals = literals;
        this.ercGenerators = ercGenerators;

        instructionCount = instructionList.size();
        instructionAndLiteralCount = instructionCount + literals.size();
        instructionLiteralAndErcCount = instructionAndLiteralCount + ercGenerators.size();

        this.random = new SecureRandom();
    }

    public IExecutable getRandomInstruction() {

        int r = random.nextInt(instructionLiteralAndErcCount);
        if (r < instructionCount)
            return instructionList.get(r);
        else if (r < instructionAndLiteralCount)
            return literals.get(r - instructionCount);
        else
            return ercGenerators.get(r - instructionAndLiteralCount).generateLiteral();

    }

    public List<IExecutable> getRandomInstructions(int count) {
        List<IExecutable> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++)
            list.add(getRandomInstruction());

        return list;
    }
    public int getNumInstructions(){
        return instructionLiteralAndErcCount;
    }

}
