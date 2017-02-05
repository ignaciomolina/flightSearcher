package com.ignaciomolina.flightsearcher.calculators;

import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import com.ignaciomolina.flightsearcher.Passenger;
import com.ignaciomolina.flightsearcher.pojo.Airline;
import com.ignaciomolina.flightsearcher.pojo.Flight;

/**
 * Class that calculates {@code Flight}s prices.
 * 
 * @author imolina
 */
public class PriceCalculator {

    // TODO: Get values from a configuration file.
    private static final double DATE_DISCOUNT_FIRST_FACTOR = 0.8D;
    private static final double DATE_DISCOUNT_SECOND_FACTOR = 1.0D;
    private static final double DATE_DISCOUNT_THIRD_FACTOR = 1.2D;
    private static final double DATE_DISCOUNT_FOURTH_FACTOR = 1.5D;

    private static final double CHILD_DISCOUNT_FACTOR = 0.67D;

    private Map<String, Airline> airlines;

    /**
     * Constructor of the PriceCalculator class.
     * 
     * @param airlines collection of airlines with infant fixed prices.
     */
    public PriceCalculator(Collection<Airline> airlines) {

        Objects.requireNonNull(airlines, "Airlines collection cannot be null.");

        this.airlines = airlines.stream()
                                    .collect(toMap(Airline::getCode,
                                                   airline -> airline));
    }

    /**
     * Method that calculates the price of a {@code Flight} according several 
     * factors as number and type of passengers and days left to the departure.
     * 
     * @param flight the flight information
     * @param passengers collection of passengers
     * @param days the number of days left to the departure day
     * @return price with all the possible discounts applied
     */
    public double calculate(Flight flight, Collection<Passenger> passengers, int days) {

        Objects.requireNonNull(flight, "Flight cannot be null.");
        Objects.requireNonNull(passengers, "Passengers collection cannot be null.");

        double result = 0.0D;

        for (Passenger passenger : passengers) {

            double price = flight.getBasePrice();

            price = applyDateDiscount(price, days);
            price = applyAgeDiscount(flight.getAirline(), price, passenger);

            result += price;
        }
        return result;
    }

    private double applyDateDiscount(double price, int days) {

        double result = price;

        if (days >= 31) {

            result *= DATE_DISCOUNT_FIRST_FACTOR;
        } else if (days >= 16) {

            result *= DATE_DISCOUNT_SECOND_FACTOR;
        } else if (days >= 3) {

            result *= DATE_DISCOUNT_THIRD_FACTOR;
        } else {

            result *= DATE_DISCOUNT_FOURTH_FACTOR;
        }

        return result;
    }

    private double applyAgeDiscount(String airlineCode, double price, Passenger passenger) {

        double result = price;

        if (Passenger.INFANT.equals(passenger)) {

            Airline airline = airlines.get(airlineCode);

            result = (airline != null ? airline.getInfantPrice() :
                                        price * CHILD_DISCOUNT_FACTOR);
        } else if (Passenger.CHILD.equals(passenger)) {

            result *= CHILD_DISCOUNT_FACTOR;
        }

        return result;
    }
}
