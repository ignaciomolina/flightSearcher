package com.ignaciomolina.flightsearcher.searchers;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ignaciomolina.flightsearcher.Passenger;
import com.ignaciomolina.flightsearcher.calculators.PriceCalculator;
import com.ignaciomolina.flightsearcher.pojo.Flight;

/**
 * @author imolina
 *
 */
public class FlightSearcherTest {

    private static final double  MINOR_PRICE = 100.0D;
    private static final double  MIDDLE_PRICE = 500.0D;
    private static final double  MAJOR_PRICE = 1000.0D;
    private static final int DAYS = 15;
    private static final String ORIGIN = "LHR";
    private static final String DESTINATION = "IST";

    private Collection<Flight> flights;
    private Collection<Passenger> passengers;
    private FlightSearcher searcher;

    @Mock Flight cheapFlight;
    @Mock Flight averageFlight;
    @Mock Flight expensiveFlight;
    @Mock PriceCalculator calculator;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        given(calculator.calculate(cheapFlight, passengers, DAYS)).willReturn(MINOR_PRICE);
        given(calculator.calculate(averageFlight, passengers, DAYS)).willReturn(MIDDLE_PRICE);
        given(calculator.calculate(expensiveFlight, passengers, DAYS)).willReturn(MAJOR_PRICE);

        given(cheapFlight.getOrigin()).willReturn(ORIGIN);
        given(cheapFlight.getDestination()).willReturn(DESTINATION);
        given(averageFlight.getOrigin()).willReturn(ORIGIN);
        given(averageFlight.getDestination()).willReturn(DESTINATION);
        given(expensiveFlight.getOrigin()).willReturn(ORIGIN);
        given(expensiveFlight.getDestination()).willReturn(DESTINATION);

        flights = new HashSet<>();
        flights.add(cheapFlight);
        flights.add(averageFlight);
        flights.add(expensiveFlight);

        searcher = new FlightSearcher(flights, calculator);
    }

    @Test
    public void shouldThreeFlightsSortedByPrice() {

        List<Flight> sortedResult = searcher.search(ORIGIN, DESTINATION, passengers, DAYS);

        then(sortedResult).hasSize(3);
        then(sortedResult).containsSequence(cheapFlight, averageFlight, expensiveFlight);

        given(calculator.calculate(cheapFlight, passengers, DAYS)).willReturn(MAJOR_PRICE + 1);

        List<Flight> newOrder = searcher.search(ORIGIN, DESTINATION, passengers, DAYS);

        then(newOrder).hasSize(3);
        then(newOrder).containsSequence(averageFlight, expensiveFlight, cheapFlight);
    }

    @Test
    public void shouldFoundZeroFlightsForRoute() {

        List<Flight> flights = searcher.search("BCN", "MAD", passengers, DAYS);

        then(flights).isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullFlightsCollection() {

        searcher = new FlightSearcher(null, calculator);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullCalculator() {

        searcher = new FlightSearcher(flights, null);
    }
}
