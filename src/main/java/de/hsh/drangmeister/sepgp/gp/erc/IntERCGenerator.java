package de.hsh.drangmeister.sepgp.gp.erc;

import de.hsh.drangmeister.sepgp.push.literals.BaseLiteral;
import de.hsh.drangmeister.sepgp.push.literals.ErcGeneratedIntLiteral;

import java.security.SecureRandom;

public class IntERCGenerator extends ERCGenerator {

    private int min;
    private int max;
    private SecureRandom random;

    public IntERCGenerator(int min, int max) {
        this.min = min;
        this.max = max;
        this.random = new SecureRandom();
    }

    @Override
    public BaseLiteral generateLiteral() {
        return new ErcGeneratedIntLiteral(min + random.nextInt(max - min), min, max);
    }
}
