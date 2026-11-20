package ru.fastdelivery.domain.common.dimension;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class NormalizeTest {


    private Normalize normalize;

    @Test
    @DisplayName("Если передан пустой параметр -> exception")
    void whenDimensionIsNull_ThrowException() {
        assertThatThrownBy(() -> new Normalize(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Если параметр ниже нуля -> exception")
    void whenDimensionBelowZero() {
        var param = new BigInteger("-1");
        assertThatThrownBy(() -> new Normalize(param)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "Если один из параметров габаритов больше 1500 -> exception")
    @CsvSource({
            "1600", "1501", "2000"})
    void whenDimensionMoreThenMaxValue_ThrowException(BigInteger param) {
        assertThatThrownBy(() -> new Normalize(param)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Если переданный параметр меньше 50 -> return 50")
    void getNormalizeValueTo50_WhenNormUnitGreaterThenRawValue_ReturnNormUnit() {
        var raw = new BigInteger("40");
        var normalize = new Normalize(raw);
        var expected = new BigInteger("50");
        var actual = normalize.getNormalizeValueTo50();
        assertThat(actual).isEqualTo(expected);
    }

}