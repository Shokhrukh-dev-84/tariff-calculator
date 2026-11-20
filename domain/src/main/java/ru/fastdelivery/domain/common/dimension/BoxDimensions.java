package ru.fastdelivery.domain.common.dimension;

import ru.fastdelivery.domain.common.dimension.height.Height;
import ru.fastdelivery.domain.common.dimension.length.Length;
import ru.fastdelivery.domain.common.dimension.width.Width;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public record BoxDimensions(BigDecimal volume) {

    public static BigDecimal BILLION = new BigDecimal(1_000_000_000);

    public static BoxDimensions volumeInCubicMetres(Length length, Width width, Height height) {
        BigInteger volumeBI = length.normalize().packLength().multiply(width.normalize().packWidth()
                .multiply(height.normalize().packHeight()));
        BigDecimal transDecimal = new BigDecimal(volumeBI);
        return new BoxDimensions(transDecimal.divide(BILLION, 4, RoundingMode.HALF_UP));
    }

    public static BoxDimensions zero() {
        return new BoxDimensions(BigDecimal.ZERO);
    }

}
