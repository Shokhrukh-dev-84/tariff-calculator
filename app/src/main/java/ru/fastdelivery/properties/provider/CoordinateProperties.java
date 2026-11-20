package ru.fastdelivery.properties.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("coordinate")
@Setter
@Getter
public class CoordinateProperties {
    private Double maxLatitude;
    private Double maxLongitude;
    private Double minLatitude;
    private Double minLongitude;
}
