package com.ignaciomolina.flightsearcher.readers;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.IOException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.ignaciomolina.flightsearcher.pojo.Airline;

/**
 * 
 * @author imolina
 *
 */
public class AirlineLoaderTest {

    private static final String OTHER_CSV = "twoFlights.csv";
    private static final String TWO_AIRLINES = "twoAirlines.csv";
    private static final String TWO_DUPLICATED_AIRLINES = "twoDuplicatedAirlines.csv";

    private AirlineLoader loader;

    @Before
    public void setup() {

        loader = new AirlineLoader();
    }


    @Test
    public void shouldReturnListWithNonDuplicatedAirlines() throws IOException {

        Collection<Airline> airlines = loader.load(TWO_DUPLICATED_AIRLINES);

        then(airlines).hasSize(1);
        then(airlines).extractingResultOf("getCode", String.class).contains("IB");
        then(airlines).extractingResultOf("getName", String.class).contains("Iberia");
        then(airlines).extractingResultOf("getInfantPrice", Float.class).contains(10.0F);
    }

    @Test
    public void shouldReturnListWithTwoAirlines() throws IOException {

        Collection<Airline> airlines = loader.load(TWO_AIRLINES);

        then(airlines).hasSize(2);
    }

    @Test
    public void shouldReturnListEmptyListWhenNoMatchFound() throws IOException {

        Collection<Airline> airlines = loader.load(OTHER_CSV);

        then(airlines).isEmpty();
    }
}
