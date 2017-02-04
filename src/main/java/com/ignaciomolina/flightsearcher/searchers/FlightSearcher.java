package com.ignaciomolina.flightsearcher.searchers;

import static java.util.stream.Collectors.groupingBy;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ignaciomolina.flightsearcher.Passanger;
import com.ignaciomolina.flightsearcher.calculators.PriceCalculator;
import com.ignaciomolina.flightsearcher.pojo.Flight;

/**
 * @author imolina
 *
 */
public class FlightSearcher {

    private PriceCalculator calculator;
    private Map<String, List<Flight>> flightsIndex;

    public FlightSearcher(Collection<Flight> flights, PriceCalculator calculator) {

        this.calculator = Objects.requireNonNull(calculator, "PriceCalculator cannot be null.");
        Objects.requireNonNull(flights, "Flights collection cannot be null.");

        this.flightsIndex = flights.stream()
                                        .collect(groupingBy(f -> f.getOrigin() + "-" +
                                                                 f.getDestination()));
    }

    public List<Flight> search(String origin, String destination, Collection<Passanger> passangers, int days) {

        List<Flight> flights = flightsIndex.get(origin + "-" + destination);

        flights.stream().sorted(new Comparator<Flight>() {

            @Override
            public int compare(Flight flight1, Flight flight2) {

                double price1 = calculator.calculate(flight1, passangers, days);
                double price2 = calculator.calculate(flight2, passangers, days);

                return Double.compare(price1, price2);
            }
        });

        return flights;
    }
}
