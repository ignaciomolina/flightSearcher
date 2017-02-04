package com.ignaciomolina.flightsearcher;

import java.io.IOException;
import java.util.Collection;

import com.ignaciomolina.flightsearcher.calculators.PriceCalculator;
import com.ignaciomolina.flightsearcher.pojo.Airline;
import com.ignaciomolina.flightsearcher.pojo.Flight;
import com.ignaciomolina.flightsearcher.readers.AirlineLoader;
import com.ignaciomolina.flightsearcher.readers.FlightLoader;
import com.ignaciomolina.flightsearcher.searchers.FlightSearcher;

/**
 * @author imolina
 *
 */
public class Main {

    private static final String AIRLINES_CSV = "airlines.csv";
    private static final String FLIGHTS_CSV = "flights.csv";

    public static void main(String[] args) throws IOException {

        FlightLoader flightLoader = new FlightLoader();
        Collection <Flight> flights = flightLoader.load(FLIGHTS_CSV);

        AirlineLoader airlineLoader = new AirlineLoader();
        Collection <Airline> airlines = airlineLoader.load(AIRLINES_CSV);

        PriceCalculator calculator = new PriceCalculator(airlines);

        FlightSearcher searcher = new FlightSearcher(calculator);
    }
}
