package com.ignaciomolina.flightsearcher.searchers;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ignaciomolina.flightsearcher.calculators.PriceCalculator;
import com.ignaciomolina.flightsearcher.pojo.Flight;

/**
 * @author imolina
 *
 */
public class FlightSearcherTest {

    private Collection<Flight> flights;
    private FlightSearcher searcher;

    @Mock PriceCalculator calculator;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        flights = new HashSet<>();
    }

    @Test
    public void shouldCreateFlightSearcher() {

        searcher = new FlightSearcher(flights, calculator);
    }
}
