package de.hsh.drangmeister.sepgp.gp.erc;

import de.hsh.drangmeister.sepgp.push.literals.BaseLiteral;
import de.hsh.drangmeister.sepgp.push.literals.CharLiteral;

import java.security.SecureRandom;

public class VisibleCharacterERCGenerator extends ERCGenerator {

    private SecureRandom random;

    public VisibleCharacterERCGenerator() {
        random = new SecureRandom();
    }

    @Override
    public BaseLiteral generateLiteral() {
        int asciiValue = (random.nextBoolean() ? 'a' : 'A') + random.nextInt(26);
        return new CharLiteral((char) (asciiValue));
    }
}
