package com.ignaciomolina.flightsearcher;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.ignaciomolina.flightsearcher.calculators.PriceCalculator;
import com.ignaciomolina.flightsearcher.pojo.Airline;
import com.ignaciomolina.flightsearcher.pojo.Flight;
import com.ignaciomolina.flightsearcher.readers.AirlineLoader;
import com.ignaciomolina.flightsearcher.readers.FlightLoader;
import com.ignaciomolina.flightsearcher.searchers.FlightSearcher;

/**
 * Main class of the project.
 * 
 * @author imolina
 */
public class Main {

    private static final String USEAGE_EXAMPLE = "FlightSearcher MAD " +
                                                "BCN 40 \"2 adult, 2 children" +
                                                ", 1 infants\"";
    private static final String RESOURCE = "/com/ignaciomolina/flightsearcher/";
    private static final String AIRLINES_CSV = RESOURCE + "airlines.csv";
    private static final String FLIGHTS_CSV = RESOURCE + "flights.csv";

    /**
     * Method that reads request params and activate search.
     * 
     * @param args requests params
     */
     public void start(String[] args) {

        String origin = validateAirport(args[0]);
        String destination = validateAirport(args[1]);;
        int days = Integer.parseInt(args[2]);
        Collection <Passenger> Passengers = parsePassengers(args[3]);

        FlightLoader flightLoader = new FlightLoader();
        AirlineLoader airlineLoader = new AirlineLoader();

        Collection <Flight> flights = flightLoader.load(FLIGHTS_CSV);
        Collection <Airline> airlines = airlineLoader.load(AIRLINES_CSV);

        PriceCalculator calculator = new PriceCalculator(airlines);
        FlightSearcher searcher = new FlightSearcher(flights, calculator);

        List<Flight> result = searcher.search(origin, destination, Passengers, days);

        System.out.println(PassengersToText(Passengers) + ", " +
                           days + " day" + (days != 1 ? "s" : "") + " to the departure date, " +
                           "flyaing " + origin + " -> " + destination);
        result.stream()
                    .map(f -> f.getCode() + ", " + 
                              String.format("%.02f",
                                            calculator.calculate(f, Passengers,
                                                                 days)) + " €")
                    .forEach(System.out::println);
    }

    private String validateAirport(String airport) {

        Pattern pattern = Pattern.compile("([A-Z]{3})");
        Matcher matcher = pattern.matcher(airport);

        if (!matcher.matches()) {

            throw new IllegalArgumentException("Illegal Airport code, Code " +
                                               "should be composed of three " +
                                               "capital letters.");
        }

        return airport;
    }

    private Collection<Passenger> parsePassengers(String request) {

        List<Passenger> Passengers = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\d+) ([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(request);

        while (matcher.find()) {

            int number = Integer.parseInt(matcher.group(1));
            Passenger passenger = Passenger.byName(matcher.group(2).toLowerCase());

            Passengers.addAll(Collections.nCopies(number, passenger));
        }

        if (Passengers.isEmpty()) {

            throw new IllegalArgumentException("It should be at least one Passenger.");
        }

        return Passengers;
    }

    private String PassengersToText(Collection<Passenger> Passengers) {

        Map<Passenger, Long> index = Passengers.stream()
                                                    .collect(groupingBy(Function.identity(),
                                                                        Collectors.counting()));

        StringJoiner message = new StringJoiner(", ");

        index.entrySet().stream().forEach(e -> {

            long number = e.getValue();
            Passenger p = e.getKey();

            message.add(number + " " + (number != 1 ? p.getPlural() : p.getSingular()));
        });

        return message.toString();
    }

    /**
     * Main method of the project.
     * 
     * @param args service parameters
     */
    public static void main(String[] args) {

        if (args.length != 4) {

            System.err.println("FlightSearcher <origin> <destination> <days " +
                               "to departure> \"<passengers>\"\n" +
                               "Example: " + USEAGE_EXAMPLE);
        } else {

            Main main = new Main();

            try {

                main.start(args);
            } catch (Exception e) {

                System.err.println("Error due to: " + e.getMessage() + "\n" +
                                   "Example: " + USEAGE_EXAMPLE);
            }
        }
    }
}
