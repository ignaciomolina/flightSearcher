package com.ignaciomolina.flightsearcher.readers;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.IOException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.ignaciomolina.flightsearcher.pojo.Flight;

/**
 * 
 * @author imolina
 *
 */
public class FlightsLoaderTest {

    private static final String OTHER_CSV = "twoAirlines.csv";
    private static final String TWO_FLIGHTS = "twoFlights.csv";
    private static final String TWO_DUPLICATED_FLIGHTS = "twoDuplicatedFlights.csv";

    private FlightLoader loader;

    @Before
    public void setup() {

        loader = new FlightLoader();
    }

    @Test
    public void shouldReturnListWithNonDuplicatedFlights() throws IOException {

        Collection<Flight> flights = loader.load(TWO_DUPLICATED_FLIGHTS);

        then(flights).hasSize(1);
        then(flights).extractingResultOf("getOrigin", String.class).contains("CPH");
        then(flights).extractingResultOf("getDestination", String.class).contains("FRA");
        then(flights).extractingResultOf("getAirline", String.class).contains("IB");
        then(flights).extractingResultOf("getCode", String.class).contains("IB2818");
        then(flights).extractingResultOf("getBasePrice", Float.class).contains(186.0F);
    }

    @Test
    public void shouldReturnListWithTwoFlights() throws IOException {

        Collection<Flight> flights = loader.load(TWO_FLIGHTS);

        then(flights).hasSize(2);
    }

    @Test
    public void shouldReturnListEmptyListWhenNoMatchFound() throws IOException {

        Collection<Flight> flights = loader.load(OTHER_CSV);

        then(flights).isEmpty();
    }
}
