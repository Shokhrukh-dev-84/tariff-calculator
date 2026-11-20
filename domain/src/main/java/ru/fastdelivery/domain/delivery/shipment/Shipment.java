package ru.fastdelivery.domain.delivery.shipment;

import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.dimension.BoxDimensions;
import ru.fastdelivery.domain.common.dimension.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.util.List;

public record Shipment(
        List<Pack> packages,
        Currency currency
) {
    public Weight weightAllPackages() {
        return packages.stream()
                .map(Pack::weight)
                .reduce(Weight.zero(), Weight::add);
    }

    public BigDecimal totalVolume() {
        return packages.stream()
                .map(pack -> BoxDimensions.volumeInCubicMetres(
                        pack.length(),
                        pack.width(),
                        pack.height()))
                .map(BoxDimensions::volume)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
