package de.hsh.drangmeister.sepgp;

import de.hsh.drangmeister.sepgp.push.Interpreter;
import de.hsh.drangmeister.sepgp.push.Program;
import de.hsh.drangmeister.sepgp.push.instructions.IntInstructions;
import de.hsh.drangmeister.sepgp.push.instructions.PrintInstructions;
import de.hsh.drangmeister.sepgp.push.literals.IntLiteral;

import java.util.List;

public class PushStandalone {

    public static void main(String[] args){

        Interpreter interpreter = new Interpreter(100);

        Program simpleAddition = new Program(List.of(new IntLiteral(10), new IntLiteral(32), new IntInstructions.Plus(), new PrintInstructions.PrintInt()));

        interpreter.execute(simpleAddition);

        System.out.println(String.format("The answer is: %s", interpreter.getOuput()));

    }

}
