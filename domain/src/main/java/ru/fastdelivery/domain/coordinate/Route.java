package ru.fastdelivery.domain.coordinate;

public record Route(Destination destination, Departure departure) {

    private static final double RADIUS = 6371.0;

    public Double distance() {
        double radLatDestination = Math.toRadians(destination.latitude());
        double radLonDestination = Math.toRadians(destination.longitude());
        double radLatDeparture = Math.toRadians(departure.latitude());
        double radLonDeparture = Math.toRadians(departure.longitude());

        double latSin = (radLatDeparture - radLatDestination);
        double LonSin = (radLonDeparture - radLonDestination);

        double insSquare = Math.pow(Math.sin(latSin / 2), 2) + Math.cos(radLatDestination)
                * Math.cos(radLatDeparture) * Math.pow(Math.sin(LonSin / 2), 2);

        return 2 * RADIUS * Math.asin(Math.sqrt(insSquare));
    }

}
