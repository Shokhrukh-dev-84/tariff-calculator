package ru.fastdelivery.domain.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.fastdelivery.domain.common.dimension.height.Height;
import ru.fastdelivery.domain.common.dimension.length.Length;
import ru.fastdelivery.domain.common.dimension.width.Width;

import java.math.BigInteger;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DimensionsTest extends BaseTestDimension {

    static Stream<Arguments> dimensionNormalizeCases() {
        UnaryOperator<BigInteger> heightNormalizer = val -> new Height(val).normalize().packHeight();
        UnaryOperator<BigInteger> widthNormalizer = val -> new Width(val).normalize().packWidth();
        UnaryOperator<BigInteger> lengthNormalizer = val -> new Length(val).normalize().packLength();

        return Stream.of(
                Arguments.of("Height < 50", heightNormalizer,
                        new BigInteger("38"),
                        new BigInteger("50")),
                Arguments.of("Height == 50",heightNormalizer,
                        new BigInteger("50"),
                        new BigInteger("50")),
                Arguments.of("Height > 50",heightNormalizer,
                        new BigInteger("75"),
                        new BigInteger("100")),

                Arguments.of("Length < 50", lengthNormalizer,
                        new BigInteger("15"),
                        new BigInteger("50")),
                Arguments.of("Length == 50", lengthNormalizer,
                        new BigInteger("50"),
                        new BigInteger("50")),
                Arguments.of("Length > 50", lengthNormalizer,
                        new BigInteger("125"),
                        new BigInteger("150")),

                Arguments.of("Width < 50", widthNormalizer,
                        new BigInteger("7"),
                        new BigInteger("50")),
                Arguments.of("Width == 50", widthNormalizer,
                        new BigInteger("50"),
                        new BigInteger("50")),
                Arguments.of("Width > 50", widthNormalizer,
                        new BigInteger("178"),
                        new BigInteger("200"))
        );
    }

    @ParameterizedTest(name = "{0}: {2} -> {3}")
    @MethodSource("dimensionNormalizeCases")
    void normalize_ReturnExpectedNormalizedValue(
            String caseName,
            UnaryOperator<BigInteger> normalizer,
            BigInteger rawValue,
            BigInteger expected
            ) {
        BigInteger actual = normalizer.apply(rawValue);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("Если параматр габарита null -> exception")
    void whenDimensionsParamIsNull_ThrowException() {
        assertThrow_WhenParamIsNull(() -> new Height(null));
        assertThrow_WhenParamIsNull(() -> new Width(null));
        assertThrow_WhenParamIsNull(() -> new Length(null));
    }

    @Test
    @DisplayName("Если объекты одинаковы -> true")
    void whenFirstParamEqualsSeconds_ReturnTrue() {
        assertTrue_WhenFirstParamEqualsSeconds(new Height(new BigInteger("2")),
                new Height(new BigInteger("2")));
        assertTrue_WhenFirstParamEqualsSeconds(new Width(new BigInteger("5")),
                new Width(new BigInteger("5")));
        assertTrue_WhenFirstParamEqualsSeconds(new Length(new BigInteger("26")),
                new Length(new BigInteger("26")));
    }
}
