package de.hsh.drangmeister.sepgp.gp.erc;

import de.hsh.drangmeister.sepgp.push.literals.BaseLiteral;
import de.hsh.drangmeister.sepgp.push.literals.StringLiteral;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class SyllablesStringERCGenerator extends ERCGenerator {

    private int maxLen;
    private Random random;
    private List<Character> syllables;

    public SyllablesStringERCGenerator(int maxLen, int randomSeed) {
        this.maxLen = maxLen;
        this.random = new Random(randomSeed);
        this.syllables = List.of('a', 'e', 'i', 'o', 'u', 'y');
    }


    @Override
    public BaseLiteral generateLiteral() {
        return new StringLiteral(getRandomString(random.nextInt(maxLen)));
    }

    public String getRandomString(int len) {
        StringBuilder sb = new StringBuilder();
        int asciiRange = 'Z' - ' ' + 1;
        for (int i = 0; i < len; i++) {
            if (random.nextFloat() < 0.2f) {
                sb.append(syllables.get(random.nextInt(syllables.size())));
            } else {
                int asciiValue = ' ' + random.nextInt(asciiRange);

                //upper case to lower case
                if (asciiValue >= 'A')
                    asciiValue += 'a' - 'A';

                sb.append((char) (asciiValue));
            }
        }
        return sb.toString();
    }
}
