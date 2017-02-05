package com.ignaciomolina.flightsearcher.calculators;

import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import com.ignaciomolina.flightsearcher.Passanger;
import com.ignaciomolina.flightsearcher.pojo.Airline;
import com.ignaciomolina.flightsearcher.pojo.Flight;

/**
 * 
 * @author imolina
 *
 */
public class PriceCalculator {

    // TODO: Get values from a configuration file.
    private static final double DATE_DISCOUNT_FIRST_FACTOR = 0.8D;
    private static final double DATE_DISCOUNT_SECOND_FACTOR = 1.0D;
    private static final double DATE_DISCOUNT_THIRD_FACTOR = 1.2D;
    private static final double DATE_DISCOUNT_FOURTH_FACTOR = 1.5D;

    private static final double CHILD_DISCOUNT_FACTOR = 0.67D;

    private Map<String, Airline> airlines;

    public PriceCalculator(Collection<Airline> airlines) {

        Objects.requireNonNull(airlines, "Airlines collection cannot be null.");

        this.airlines = airlines.stream()
                                    .collect(toMap(Airline::getCode,
                                                   airline -> airline));
    }

    public double calculate(Flight flight, Collection<Passanger> passangers, int days) {

        double result = 0.0D;

        for (Passanger passanger : passangers) {

            double price = flight.getBasePrice();

            price = applyDateDiscount(price, days);
            price = applyAgeDiscount(flight.getAirline(), price, passanger);

            result += price;
        }
        return result;
    }

    private double applyDateDiscount(double price, int days) {

        double result = price;

        if (days >= 31) {

            result *= DATE_DISCOUNT_FIRST_FACTOR;
        } else if (days <= 30 && days >= 16) {

            result *= DATE_DISCOUNT_SECOND_FACTOR;
        } else if (days <= 15 && days >= 3) {

            result *= DATE_DISCOUNT_THIRD_FACTOR;
        } else {

            result *= DATE_DISCOUNT_FOURTH_FACTOR;
        }

        return result;
    }

    private double applyAgeDiscount(String airlineCode, double price, Passanger passanger) {

        double result = price;

        if (Passanger.INFANT.equals(passanger)) {

            Airline airline = airlines.get(airlineCode);

            result = (airline != null ? airline.getInfantPrice() :
                                        price * CHILD_DISCOUNT_FACTOR);
        } else if (Passanger.CHILD.equals(passanger)) {

            result *= CHILD_DISCOUNT_FACTOR;
        }

        return result;
    }
}
