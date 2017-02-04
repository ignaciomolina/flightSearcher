package com.ignaciomolina.flightsearcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    private static final String RESOURCE = "/com/ignaciomolina/flightsearcher/";
    private static final String AIRLINES_CSV = RESOURCE + "airlines.csv";
    private static final String FLIGHTS_CSV = RESOURCE + "flights.csv";

    public static void main(String[] args) throws IOException {

        FlightLoader flightLoader = new FlightLoader();
        Collection <Flight> flights = flightLoader.load(FLIGHTS_CSV);

        AirlineLoader airlineLoader = new AirlineLoader();
        Collection <Airline> airlines = airlineLoader.load(AIRLINES_CSV);

        PriceCalculator calculator = new PriceCalculator(airlines);

        FlightSearcher searcher = new FlightSearcher(flights, calculator);

        Collection <Passanger> passangers = new ArrayList<>();

        passangers.add(Passanger.ADULT);
//        passangers.add(Passanger.ADULT);
        passangers.add(Passanger.CHILD);
        passangers.add(Passanger.CHILD);
        int days = 2;

        List<Flight> result = searcher.search("BCN", "MAD", passangers, days);

        result.stream()
                    .map(f -> f.getCode() + ", " + String.format("%.02f", calculator.calculate(f, passangers, days)) + " €")
                    .forEach(System.out::println);
    }
}
