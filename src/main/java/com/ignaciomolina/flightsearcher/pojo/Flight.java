package com.ignaciomolina.flightsearcher.pojo;

import java.util.Objects;

/**
 * Class that contains the flight information.
 * 
 * @author imolina
 */
public class Flight {

    private String origin;
    private String destination;
    private String airline;
    private String code;
    private float basePrice;

    /**
     * Constructor of the Flight class.
     * 
     * @param origin Departure airport code
     * @param destination Arrival airport code
     * @param airline Airline owner of the flight
     * @param code Code that identifies the flight
     * @param basePrice price of the flight without any discount applied.
     */
    public Flight(String origin, String destination, String airline, String code, float basePrice) {

        this.origin = Objects.requireNonNull(origin, "Origin cannot be null.");
        this.destination = Objects.requireNonNull(destination, "Destination cannot be null.");
        this.airline = Objects.requireNonNull(airline, "Airline cannot be null.");
        this.code = Objects.requireNonNull(code, "Code cannot be null.");
        this.basePrice = basePrice;
    }

    /**
     * Method that gets the departure airport code.
     * 
     * @return departure airport code
     */
    public String getOrigin() {

        return origin;
    }

    /**
     * Method that gets the arrival airport code.
     * 
     * @return departure airport code
     */
    public String getDestination() {

        return destination;
    }

    /**
     * Method that gets the airline that owns the flight.
     * 
     * @return airline identification code
     */
    public String getAirline() {

        return airline;
    }

    /**
     * Method that gets the flight code.
     * 
     * @return code that identifies the flight
     */
    public String getCode() {

        return code;
    }

    /**
     * Method that gets the flight price without any discount.
     * 
     * @return base price of the flight
     */
    public float getBasePrice() {

        return basePrice;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;

        result = prime * result + Float.floatToIntBits(basePrice);
        result = prime * result + code.hashCode();
        result = prime * result + airline.hashCode();
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
            !code.equals(other.code) ||
            basePrice != other.basePrice) {

            return false;
        }

        return true;
    }
}
