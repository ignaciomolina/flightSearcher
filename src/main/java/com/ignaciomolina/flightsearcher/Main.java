package com.ignaciomolina.flightsearcher;

import java.io.IOException;
import java.util.Collection;

import com.ignaciomolina.flightsearcher.pojo.Flight;
import com.ignaciomolina.flightsearcher.readers.FlightLoader;

/**
 * @author imolina
 *
 */
public class Main {

    public static void main(String[] args) throws IOException {

        FlightLoader loader = new FlightLoader();
        Collection <Flight> flights = loader.load("flights.csv");

        FlightSearcher searcher = new FlightSearcher();
    }

}
