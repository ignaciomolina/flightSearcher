package com.ignaciomolina.flightsearcher.searchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ignaciomolina.flightsearcher.calculators.PriceCalculator;
import com.ignaciomolina.flightsearcher.searchers.FlightSearcher;

/**
 * @author imolina
 *
 */
public class FlightSearcherTest {

    private FlightSearcher searcher;

    @Mock PriceCalculator calculator;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateFlightSearcher() {

        searcher = new FlightSearcher(calculator);
    }
}
