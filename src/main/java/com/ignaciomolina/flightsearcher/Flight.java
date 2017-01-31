package com.ignaciomolina.flightsearcher;

import java.util.Objects;

public class Flight {

    private String origin;
    private String destination;
    private String airline;
    private float basePrice;

    public Flight(String origin, String destination, String airline, float basePrice) {

        this.origin = Objects.requireNonNull(origin, "Origin cannot be null.");
        this.destination = Objects.requireNonNull(destination, "Destination cannot be null.");
        this.airline = Objects.requireNonNull(airline, "Airline cannot be null.");
        this.basePrice = basePrice;
    }

    public String getOrigin() {

        return origin;
    }

    public String getDestination() {

        return destination;
    }

    public String getAirline() {

        return airline;
    }

    public float getBasePrice() {

        return basePrice;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;

        result = prime * result + airline.hashCode();
        result = prime * result + Float.floatToIntBits(basePrice);
        result = prime * result + destination.hashCode();
        result = prime * result + origin.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {

            return true;
        }

        if (obj == null) {

            return false;
        }

        if (getClass() != obj.getClass()) {

            return false;
        }

        Flight other = (Flight) obj;

        if (!origin.equals(other.origin) ||
            !destination.equals(other.destination) ||
            !airline.equals(other.airline) ||
            basePrice != other.basePrice) {

            return false;
        }

        return true;
    }
}
