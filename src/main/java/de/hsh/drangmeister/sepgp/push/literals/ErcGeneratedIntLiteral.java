package de.hsh.drangmeister.sepgp.push.literals;

import java.security.SecureRandom;

public class ErcGeneratedIntLiteral extends IntLiteral implements ErcGeneratedLiteral {

    private int minValue;
    private int maxValue;
    private SecureRandom random;

    public ErcGeneratedIntLiteral(int value, int minValue, int maxValue) {
        super(value);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.random = new SecureRandom();
    }


    @Override
    public BaseLiteral mutatedLiteral(float stdDevRangePercentage) {
        int newValue = (int) (this.getValue() + (random.nextGaussian() * (maxValue - minValue) * stdDevRangePercentage));
        newValue = Math.max(minValue, Math.min(maxValue, newValue));
        return new ErcGeneratedIntLiteral(newValue, minValue, maxValue);
    }
}
