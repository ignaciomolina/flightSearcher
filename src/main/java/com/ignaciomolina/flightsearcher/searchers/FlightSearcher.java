package com.ignaciomolina.flightsearcher.searchers;

import java.util.Objects;

import com.ignaciomolina.flightsearcher.calculators.PriceCalculator;

/**
 * @author imolina
 *
 */
public class FlightSearcher {

    public FlightSearcher(PriceCalculator calculator) {

        Objects.requireNonNull(calculator, "PriceCalculator cannot be null.");
    }
}
