package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimension.height.Height;
import ru.fastdelivery.domain.common.dimension.length.Length;
import ru.fastdelivery.domain.common.dimension.weight.Weight;
import ru.fastdelivery.domain.common.dimension.width.Width;
import ru.fastdelivery.domain.common.distance.DistancePropertiesProvider;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.coordinate.Departure;
import ru.fastdelivery.domain.coordinate.Destination;
import ru.fastdelivery.domain.coordinate.GeoPropertiesProvider;
import ru.fastdelivery.domain.coordinate.Route;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TariffCalculateUseCaseTest {

    final DistancePropertiesProvider distanceProvider = distance -> {
        if (distance == null) {
            throw new IllegalArgumentException("Distance cannot be null");
        }
        return distance > 450 ? distance / 450 : 1.0;
    };

    final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    final VolumePriceProvider volumePriceProvider = mock(VolumePriceProvider.class);
    final GeoPropertiesProvider geoProvider = mock(GeoPropertiesProvider.class);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");

    final TariffCalculateUseCase tariffCalculateUseCase = new TariffCalculateUseCase(
            weightPriceProvider,
            volumePriceProvider,
            distanceProvider,
            geoProvider);

    @Test
    @DisplayName("Расчет стоимости доставки -> успешно")
    void whenCalculatePrice_thenSuccess() {
        var destination = new Destination(41.2995, 69.2401);
        var departure = new Departure(45.7961, 69.2401);
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var costPerVolume = new Price(BigDecimal.valueOf(345), currency);
        var route = new Route(destination, departure);

        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(volumePriceProvider.costPerVolume()).thenReturn(costPerVolume);
        var shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(800)),
                new Length(BigInteger.valueOf(1000)), new Width(BigInteger.valueOf(1000)),
                new Height(BigInteger.valueOf(1000)))),
                new CurrencyFactory(code -> true).create("RUB"));
        var expectedPrice = new Price(new BigDecimal("383.34"), currency);
        var actualPrice = tariffCalculateUseCase.getTotalCost(shipment, route);
        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
        verify(geoProvider).validate(any());
    }


    @ParameterizedTest
    @ValueSource(doubles = {43.0981, 45.3464})
    @DisplayName("Если расстояние меньше или равно 450 км -> базовая стоимость")
    void whenDistanceIsLessBaseLimit_CalculateTotalCostByBaseLimit(double departureLatitude) {
        var destination = new Destination(41.2995, 69.2401);
        var departure = new Departure(departureLatitude, 69.2401);
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var perKg = new Price(BigDecimal.valueOf(783), currency);
        var costPerVolume = new Price(BigDecimal.valueOf(100), currency);
        var route = new Route(destination, departure);

        Shipment shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(1000)),
                new Length(BigInteger.valueOf(100)), new Width(BigInteger.valueOf(100)),
                new Height(BigInteger.valueOf(200)))), currency);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(perKg);
        when(volumePriceProvider.costPerVolume()).thenReturn(costPerVolume);

        var expected = new Price(BigDecimal.valueOf(783), currency);
        Price actual = tariffCalculateUseCase.getTotalCost(shipment, route);

        assertThat(actual).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expected);
        verify(geoProvider).validate(any());
    }

    @Test
    @DisplayName("Получение минимальной стоимости -> успешно")
    void whenMinimalPrice_thenSuccess() {
        BigDecimal minimalValue = BigDecimal.TEN;
        var minimalPrice = new Price(minimalValue, currency);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);

        var actual = tariffCalculateUseCase.minimalPrice();

        assertThat(actual).isEqualTo(minimalPrice);
    }
}