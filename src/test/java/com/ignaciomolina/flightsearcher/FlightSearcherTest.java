package com.ignaciomolina.flightsearcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
