package de.hsh.drangmeister.sepgp.gp.util;

import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.Random;

public class RandomBinDistribution {

    private int closeCountBinDistributionN;
    private float closeCountBinDistributionP;
    private Random random;

    public RandomBinDistribution(int closeCountBinDistributionN, float closeCountBinDistributionP) {
        this.closeCountBinDistributionN = closeCountBinDistributionN;
        this.closeCountBinDistributionP = closeCountBinDistributionP;
        random = new Random();
    }

    public int getNext() {
        float total = 0;
        float rand = random.nextFloat();
        for (int i = 0; i <= closeCountBinDistributionN; i++) {
            total += Math.pow(closeCountBinDistributionP, i) *
                    Math.pow(1.f - closeCountBinDistributionP, closeCountBinDistributionN - i) *
                    CombinatoricsUtils.binomialCoefficient(closeCountBinDistributionN, i);

            if (rand < total)
                return i;
        }
        return closeCountBinDistributionN;
    }

}
