package com.ignaciomolina.flightsearcher.searchers;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ignaciomolina.flightsearcher.Passenger;
import com.ignaciomolina.flightsearcher.calculators.PriceCalculator;
import com.ignaciomolina.flightsearcher.pojo.Flight;

/**
 * Class that looks for flights with an specific route.
 * 
 * @author imolina
 */
public class FlightSearcher {

    PriceCalculator calculator;
    private Map<String, List<Flight>> flightsIndex;

    /**
     * Constructor of the FlightSearch class.
     * 
     * @param flights Flights to be indexed
     * @param calculator Calculator of prices
     */
    public FlightSearcher(Collection<Flight> flights, PriceCalculator calculator) {

        this.calculator = Objects.requireNonNull(calculator, "PriceCalculator cannot be null.");
        Objects.requireNonNull(flights, "Flights collection cannot be null.");

        this.flightsIndex = flights.stream()
                                .collect(groupingBy(f -> f.getOrigin() + "-" +
                                                         f.getDestination()));
    }

    /**
     * Search for all the flights that have the wished route and return a list
     * sorted by price.
     * 
     * @param origin Departure airport code
     * @param destination Arrival airport code
     * @param passengers Collection of passengers for price calculation
     * @param days Number of days left to the departure day
     * @return list of matching flights sorted by price
     */
    public List<Flight> search(String origin, String destination,
                               Collection<Passenger> passengers, int days) {

        String route = origin + "-" + destination;

        List<Flight> foundFlights = flightsIndex.getOrDefault(route,
                                                              new ArrayList<>());
        List<Flight> sorted = foundFlights.stream()
                                              .sorted(new Comparator<Flight>() {

            @Override
            public int compare(Flight flight1, Flight flight2) {

                double price1 = calculator.calculate(flight1, passengers, days);
                double price2 = calculator.calculate(flight2, passengers, days);

                return Double.compare(price1, price2);
            }
        }).collect(Collectors.toList());

        return sorted;
    }
}
