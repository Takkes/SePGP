package de.hsh.drangmeister.sepgp.gp.erc;

import de.hsh.drangmeister.sepgp.push.literals.BaseLiteral;
import de.hsh.drangmeister.sepgp.push.literals.FloatLiteral;

import java.security.SecureRandom;

public class FloatERCGenerator extends ERCGenerator {

    private float min;
    private float max;
    private SecureRandom random;

    public FloatERCGenerator(int min, int max) {
        this.min = min;
        this.max = max;
        this.random = new SecureRandom();
    }

    @Override
    public BaseLiteral generateLiteral() {
        return new FloatLiteral(min + random.nextFloat() * (max - min));
    }

}
