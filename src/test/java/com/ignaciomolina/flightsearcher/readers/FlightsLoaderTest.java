package com.ignaciomolina.flightsearcher.readers;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.IOException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.ignaciomolina.flightsearcher.Flight;

public class FlightsLoaderTest {

    private static final String RESOURCES = "src/test/resources/";
    private static final String TWO_FLIGHTS = RESOURCES + "twoFlights.csv";
    private static final String TWO_DUPLICATED_FLIGHTS = RESOURCES + "twoDuplicatedFlights.csv";
    private static final String EMPTY_CSV = RESOURCES + "emptyflights.csv";

    private FlightLoader loader;

    @Before
    public void setup() {

        loader = new FlightLoader();
    }

    @Test
    public void shouldReturnListWithTwoFlights() throws IOException {

        Collection<Flight> flights = loader.load(TWO_FLIGHTS);

        then(flights).hasSize(2);
    }

    @Test
    public void shouldReturnListWithNonDuplicatedFlights() throws IOException {

        Collection<Flight> flights = loader.load(TWO_DUPLICATED_FLIGHTS);

        then(flights).hasSize(1);
    }

    @Test
    public void shouldReturnEmptyList() throws IOException {

        Collection<Flight> flights = loader.load(EMPTY_CSV);
        then(flights).isEmpty();
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNoRightExtention() throws IOException {

        loader.load(EMPTY_CSV + "flightscsv");
    }

    @Test(expected=IOException.class)
    public void shouldNotAcceptNonExistingFile() throws IOException {

        loader.load("non-existing.csv");
    }

    @Test(expected=NullPointerException.class)
    public void shouldNotAcceptNullFilename() throws IOException {

        loader.load(null);
    }
}
