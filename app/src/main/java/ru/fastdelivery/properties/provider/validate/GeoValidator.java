package ru.fastdelivery.properties.provider.validate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fastdelivery.domain.coordinate.GeoPropertiesProvider;
import ru.fastdelivery.domain.coordinate.Route;
import ru.fastdelivery.properties.provider.CoordinateProperties;

@Service
@RequiredArgsConstructor
public class GeoValidator implements GeoPropertiesProvider {
    private final CoordinateProperties properties;

    @Override
    public void validate(Route route) {
        if(route == null || route.departure() == null || route.destination() == null) {
            throw new IllegalArgumentException("Route and its coordinate cannot be null");
        }
        validatePoint(route.destination().latitude(), route.destination().longitude());
        validatePoint(route.departure().latitude(), route.departure().longitude());
    }

    private void validatePoint(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Latitude or longitude cannot be null");
        }
        if (latitude > properties.getMaxLatitude()
                || latitude < properties.getMinLatitude()
                || longitude > properties.getMaxLongitude()
                || longitude < properties.getMinLatitude()) {
            throw new IllegalArgumentException("Coordinates are out of valid range");
        }
    }
}
