package ru.fastdelivery.domain.coordinate;

public record Destination(Double latitude, Double longitude)  {

    public Destination {
        if(latitude == null || longitude == null) {
            throw new IllegalArgumentException("Coordinate values cannot be null");
        }
    }
}
