package ru.fastdelivery.domain.coordinate;

public record Departure(Double latitude, Double longitude) {

    public Departure {
        if(latitude == null || longitude == null) {
            throw new IllegalArgumentException("Coordinate cannot be null");
        }
    }
}
