package ru.fastdelivery.domain.common.dimension;

import java.math.BigInteger;

public record Normalize(BigInteger rawValue) implements Comparable<Normalize> {
    public static final BigInteger maxValue = BigInteger.valueOf(1500);

    public Normalize {
        if (rawValue == null || rawValue.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Dimension can not be below zero");
        }
        if (rawValue.compareTo(maxValue) > 0) {
            throw new IllegalArgumentException("Dimension cannot be above 1500");
        }
    }

    public BigInteger getNormalizeValueTo50() {
        BigInteger normUnit = BigInteger.valueOf(50);
        if (rawValue.compareTo(normUnit) < 0) {
            return normUnit;
        }
        BigInteger[] parts = rawValue.divideAndRemainder(normUnit);
        if (parts[1].compareTo(BigInteger.ZERO) > 0) {
            return parts[0].add(BigInteger.ONE).multiply(normUnit);
        }
        return rawValue;
    }

    @Override
    public int compareTo(Normalize n) {
        return rawValue.compareTo(n.rawValue);
    }
}
