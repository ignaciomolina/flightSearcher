package com.ignaciomolina.flightsearcher.readers;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ignaciomolina.flightsearcher.pojo.Flight;

/**
 * 
 * @author imolina
 */
public class FlightLoader extends CSVLoader<Flight> {

    private static final String PATTERN = "^(.{3}),(.{3}),((.{2}).{4}),([0-9]+(?:\\.[0-9]*)?)$";

    @Override
    protected Collection<Flight> parseLines(List<String> lines) {

        Set<Flight> flights = new HashSet<>();
        Pattern pattern = Pattern.compile(PATTERN);

        for (String line : lines) {

            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {

                String origin = matcher.group(1);
                String destination = matcher.group(2);
                String airline = matcher.group(4);
                String code = matcher.group(3);
                float basePrice = Float.parseFloat(matcher.group(5));

                flights.add(new Flight(origin, destination, airline,
                                       code, basePrice));
            }
        }

        return flights;
    }
}
