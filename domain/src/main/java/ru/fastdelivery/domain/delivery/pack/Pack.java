package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.dimension.height.Height;
import ru.fastdelivery.domain.common.dimension.length.Length;
import ru.fastdelivery.domain.common.dimension.weight.Weight;
import ru.fastdelivery.domain.common.dimension.width.Width;

import java.math.BigInteger;

public record Pack(Weight weight,
                   Length length,
                   Width width,
                   Height height) {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));

    public Pack {
        if (weight.greaterThan(maxWeight)) {
            throw new IllegalArgumentException("Package can't be more than " + maxWeight);
        }
    }
}
