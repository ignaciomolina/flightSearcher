package com.ignaciomolina.flightsearcher.calculators;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ignaciomolina.flightsearcher.Passanger;
import com.ignaciomolina.flightsearcher.pojo.Airline;
import com.ignaciomolina.flightsearcher.pojo.Flight;

public class PriceCalculator {

    private static final float DATE_DISCOUNT_FIRST_FACTOR = 0.8F;
    private static final float DATE_DISCOUNT_SECOND_FACTOR = 1.0F;
    private static final float DATE_DISCOUNT_THIRD_FACTOR = 1.2F;
    private static final float DATE_DISCOUNT_FOURTH_FACTOR = 1.5F;

    private static final float CHILD_DISCOUNT_FACTOR = 0.66F;

    private Map<String, Airline> airlines;

    public PriceCalculator(Collection<Airline> airlines) {

        Objects.requireNonNull(airlines, "Airlines collection cannot be null.");

        this.airlines = airlines.stream()
                                    .collect(Collectors.toMap(Airline::getCode,
                                                              airline -> airline));
    }

    public float calculate(Flight flight, List<Passanger> passangers, int days) {

        float result = 0.0F;

        for (Passanger passanger : passangers) {

            float price = flight.getBasePrice();

            price = applyDateDiscount(price, days);
            price = applyAgeDiscount(flight.getAirline(), price, passanger);

            result += price;
        }
        return result;
    }

    private float applyDateDiscount(float price, int days) {

        float result = price;

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

    private float applyAgeDiscount(String airlineCode, float price, Passanger passanger) {

        float result = price;

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
