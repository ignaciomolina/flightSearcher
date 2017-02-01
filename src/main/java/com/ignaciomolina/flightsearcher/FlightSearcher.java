package com.ignaciomolina.flightsearcher;

import java.util.Objects;

/**
 * @author imolina
 *
 */
public class FlightSearcher {

    public FlightSearcher(PriceCalculator calculator) {

        Objects.requireNonNull(calculator, "PriceCalculator cannot be null.");
    }
}
