package ru.fastdelivery.properties.provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class DistancePropertiesTest {

    private DistanceProperties distanceProperties;

    @BeforeEach
    void setUp() {
        distanceProperties = new DistanceProperties();
        distanceProperties.setBaseLimit(450.0);
    }

    @Test
    @DisplayName("Если расстояние больше базового (450 км) -> коэффицент расстояния")
    void whenDistanceGreaterThenBaseLimit_ReturnDistanceCoefficient() {
        Double distance = 900.0;
        Double expected = 2.0;

        Double actual = distanceProperties.distanceFactor(distance);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Если расстояние меньше базового -> коэффицент 1.0")
    void whenDistanceIsLessThenBaseLimit_ReturnBaseLimitCoefficient() {
        Double distance = 200.0;
        Double expected = 1.0;

        Double actual = distanceProperties.distanceFactor(distance);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Если расстояние равно базового -> коэффицент 1.0")
    void whenDistanceExactlyBaseLimit_ReturnFactorIsOne() {
        Double distance = 450.0;
        Double expected = 1.0;

        Double actual = distanceProperties.distanceFactor(distance);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Если расстояние null -> exception")
    void whenDistanceIsNull_ReturnException() {
        assertThatThrownBy(() -> distanceProperties.distanceFactor(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

}