package ru.fastdelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.domain.common.distance.DistancePropertiesProvider;
import ru.fastdelivery.domain.coordinate.GeoPropertiesProvider;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.VolumePriceProvider;
import ru.fastdelivery.usecase.WeightPriceProvider;

@Configuration
public class Beans {

    @Bean
    public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
        return new CurrencyFactory(currencyProperties);
    }

    @Bean
    public TariffCalculateUseCase tariffCalculateUseCase(WeightPriceProvider weightPriceProvider,
                                                         VolumePriceProvider volumePriceProvider,
                                                         DistancePropertiesProvider distanceProvider,
                                                         GeoPropertiesProvider geoProvider) {
        return new TariffCalculateUseCase(
                weightPriceProvider,
                volumePriceProvider,
                distanceProvider,
                geoProvider);
    }
}
