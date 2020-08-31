package de.hsh.drangmeister.sepgp.gp.erc;

import de.hsh.drangmeister.sepgp.push.literals.BaseLiteral;
import de.hsh.drangmeister.sepgp.push.literals.BooleanLiteral;

import java.security.SecureRandom;

public class BooleanERCGenerator extends ERCGenerator {

    private SecureRandom random;

    public BooleanERCGenerator() {
        this.random = new SecureRandom();
    }

    @Override
    public BaseLiteral generateLiteral() {
        return new BooleanLiteral(random.nextBoolean());
    }
}
