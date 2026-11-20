package ru.fastdelivery.properties.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.distance.DistancePropertiesProvider;

@Configuration
@ConfigurationProperties("distance")
@Getter
@Setter
public class DistanceProperties implements DistancePropertiesProvider {
    private Double baseLimit;

    @Override
    public Double distanceFactor(Double distance) {
        if (distance == null) {
            throw new IllegalArgumentException("Distance cannot be null");
        }
        return distance > baseLimit ? distance / baseLimit : 0.1;
    }
}
