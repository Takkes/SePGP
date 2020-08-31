package de.hsh.drangmeister.sepgp.gp.erc;

import de.hsh.drangmeister.sepgp.push.literals.BaseLiteral;
import de.hsh.drangmeister.sepgp.push.literals.StringLiteral;

import java.util.Random;

public class ReplaceSpaceWithNewLineStringERCGenerator extends ERCGenerator {

    private Random random;
    private int maxStringLength;
    private float spaceProbability;

    public ReplaceSpaceWithNewLineStringERCGenerator(int maxStringLength, float spaceProbability, int randomSeed) {
        this.maxStringLength = maxStringLength;
        this.spaceProbability = spaceProbability;
        random = new Random(randomSeed);
    }

    @Override
    public BaseLiteral generateLiteral() {
        return new StringLiteral(generateString());
    }

    public String generateString() {
        StringBuilder sb = new StringBuilder();
        int length = random.nextInt(maxStringLength);
        for (int i = 0; i < length; i++) {
            if (random.nextFloat() < spaceProbability)
                sb.append(' ');
            else
                sb.append((char) ('a' + random.nextInt(26)));
        }
        return sb.toString();
    }
}
