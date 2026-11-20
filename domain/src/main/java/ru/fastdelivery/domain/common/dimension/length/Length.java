package ru.fastdelivery.domain.common.dimension.length;

import ru.fastdelivery.domain.common.dimension.Normalize;

import java.math.BigInteger;

public record Length(BigInteger packLength) implements Comparable<Length> {

    public Length {
        if (packLength == null || packLength.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Length cannot be null or below be zero");
        }
    }

    public Length normalize() {
        BigInteger rounded = new Normalize(packLength).getNormalizeValueTo50();
        return new Length(rounded);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Length l = (Length) o;
        return packLength.compareTo(l.packLength) == 0;
    }

    @Override
    public int compareTo(Length l) {
        return packLength.compareTo(l.packLength);
    }

}
