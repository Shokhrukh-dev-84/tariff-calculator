package ru.fastdelivery.domain.common;


import org.assertj.core.api.ThrowableAssert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BaseTestDimension {

    void assertThrow_WhenParamIsNull(ThrowableAssert.ThrowingCallable constructorCall) {

        assertThatThrownBy(constructorCall).isInstanceOf(IllegalArgumentException.class);
    }

    protected <T extends Comparable<T>> void assertTrue_WhenFirstParamEqualsSeconds(T first, T second) {
        assertThat(first).isEqualTo(second).hasSameHashCodeAs(second);
    }
}
