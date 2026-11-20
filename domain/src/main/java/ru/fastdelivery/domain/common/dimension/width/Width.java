package ru.fastdelivery.domain.common.dimension.width;

import ru.fastdelivery.domain.common.dimension.Normalize;

import java.math.BigInteger;

public record Width(BigInteger packWidth) implements Comparable<Width> {

    public Width {
        if (packWidth == null || packWidth.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Width cannot be null or below be zero");
        }
    }

    public Width normalize() {
        BigInteger rounded = new Normalize(packWidth).getNormalizeValueTo50();
        return new Width(rounded);
    }

    @Override
    public int compareTo(Width w) {
        return packWidth.compareTo(w.packWidth);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Width width = (Width) o;
        return packWidth.compareTo(width.packWidth) == 0;
    }

}
