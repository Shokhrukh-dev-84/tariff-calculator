package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.distance.DistancePropertiesProvider;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.coordinate.GeoPropertiesProvider;
import ru.fastdelivery.domain.coordinate.Route;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigDecimal;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;
    private final DistancePropertiesProvider distanceProvider;
    private final GeoPropertiesProvider geoProvider;

    public Price calcPerWeight(Shipment shipment) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var minimalPrice = weightPriceProvider.minimalPrice();

        return weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(minimalPrice);
    }

    public Price calcPerVolume(Shipment shipment) {
        var dimensionVolume = shipment.totalVolume();
        return volumePriceProvider.costPerVolume()
                .multiply(dimensionVolume)
                .max(minimalPrice());
    }

    public Price calculateBaseTariff(Shipment shipment) {
        var priceByWeight = calcPerWeight(shipment);
        var priceByVolume = calcPerVolume(shipment);

        return priceByWeight.max(priceByVolume);
    }

    public Price getTotalCost(Shipment shipment, Route route) {

        geoProvider.validate(route);
        var basePrice = calculateBaseTariff(shipment);
        Double distancedFactor = distanceProvider.distanceFactor(route.distance());
        return basePrice.multiply(BigDecimal.valueOf(distancedFactor)).roundToCent();
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }
}
