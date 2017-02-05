package com.ignaciomolina.flightsearcher;

import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;
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
 * @author imolina
 *
 */
public class Main {

    private static final String USEAGE_EXAMPLE = "MAD BCN 40 \"2 adult, 2 children, 1 infants\"";

    private static final String RESOURCE = "/com/ignaciomolina/flightsearcher/";
    private static final String AIRLINES_CSV = RESOURCE + "airlines.csv";
    private static final String FLIGHTS_CSV = RESOURCE + "flights.csv";

    private void start(String[] args) throws IOException {

        String origin = validateAirport(args[0]);
        String destination = validateAirport(args[1]);;
        int days = Integer.parseInt(args[2]);
        Collection <Passanger> passangers = parsePassangers(args[3]);

        FlightLoader flightLoader = new FlightLoader();
        AirlineLoader airlineLoader = new AirlineLoader();

        Collection <Flight> flights = flightLoader.load(FLIGHTS_CSV);
        Collection <Airline> airlines = airlineLoader.load(AIRLINES_CSV);

        PriceCalculator calculator = new PriceCalculator(airlines);
        FlightSearcher searcher = new FlightSearcher(flights, calculator);

        List<Flight> result = searcher.search(origin, destination, passangers, days);

        System.out.println(PassangersToText(passangers) + ", " +
                           days + " day" + (days != 1 ? "s" : "") + " to the departure date, " +
                           "flyaing " + origin + " -> " + destination);
        result.stream()
                    .map(f -> f.getCode() + ", " + 
                              String.format("%.02f",
                                            calculator.calculate(f, passangers,
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

    private Collection<Passanger> parsePassangers(String request) {

        List<Passanger> passangers = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\d+) ([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(request);

        while (matcher.find()) {

            int number = Integer.parseInt(matcher.group(1));
            Passanger passanger = Passanger.byName(matcher.group(2).toLowerCase());

            passangers.addAll(Collections.nCopies(number, passanger));
        }

        if (passangers.isEmpty()) {

            throw new IllegalArgumentException("It should be at least one passanger.");
        }

        return passangers;
    }

    private String PassangersToText(Collection<Passanger> passangers) {

        Map<Passanger, Long> index = passangers.stream()
                                                    .collect(groupingBy(Function.identity(),
                                                                        Collectors.counting()));

        StringJoiner message = new StringJoiner(", ");

        index.entrySet().stream().forEach(e -> {

            long number = e.getValue();
            Passanger p = e.getKey();

            message.add(number + " " + (number != 1 ? p.getPlural() : p.getSingular()));
        });

        return message.toString();
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 4) {

            System.err.println("FlightSearcher <origin> <destination> <days " +
                               "to departure> \"<passangers>\"");
            System.exit(0);
        }

        Main main = new Main();

        try {

            main.start(args);
        } catch (Exception e) {

            System.err.println("Error due to: " + e.getMessage() + "\n" +
                               "Example: " + USEAGE_EXAMPLE);
        }
    }
}
