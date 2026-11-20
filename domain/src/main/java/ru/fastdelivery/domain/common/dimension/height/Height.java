package ru.fastdelivery.domain.common.dimension.height;

import ru.fastdelivery.domain.common.dimension.Normalize;

import java.math.BigInteger;

public record Height(BigInteger packHeight) implements Comparable<Height> {

    public Height {
        if (packHeight == null || packHeight.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Height cannot be null or below be zero");
        }
    }

    public Height normalize() {
        BigInteger rounded = new Normalize(packHeight).getNormalizeValueTo50();
        return new Height(rounded);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Height height = (Height) o;
        return packHeight.compareTo(height.packHeight) == 0;
    }

    @Override
    public int compareTo(Height h) {
        return this.packHeight.compareTo(h.packHeight);
    }

}
