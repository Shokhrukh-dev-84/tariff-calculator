package ru.fastdelivery.domain.common.dimension;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.dimension.height.Height;
import ru.fastdelivery.domain.common.dimension.length.Length;
import ru.fastdelivery.domain.common.dimension.width.Width;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BoxDimensionsTest {

    @Test
    @DisplayName("Подсчёт объема груза по переданным параметрам")
    void volumeInCubicMetres_ReturnVolumeValue() {
        var height = new Height(new BigInteger("1000"));
        var width = new Width(new BigInteger("1000"));
        var length = new Length(new BigInteger("1000"));
        var expected = new BigDecimal("1.0000");

        BigDecimal actual = BoxDimensions.volumeInCubicMetres(length, width, height).volume();

        assertThat(actual).isEqualTo(expected);
    }

}