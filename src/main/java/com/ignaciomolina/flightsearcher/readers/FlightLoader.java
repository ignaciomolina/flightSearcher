package com.ignaciomolina.flightsearcher.readers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ignaciomolina.flightsearcher.Flight;

public class FlightLoader {

    public Set<Flight> load(String filename) throws IOException {

        Objects.requireNonNull(filename, "Filename cannot be null.");

        Pattern patternForExtention = Pattern.compile(".*\\.csv$");
        Matcher matcherForExtention = patternForExtention.matcher(filename);

        if (!matcherForExtention.matches()) {

            throw new IllegalArgumentException("File has wrong exception. " +
                                               "Only CSV is accepted: " + filename);
        }

        String csv = new String(Files.readAllBytes(Paths.get(filename)));

        Pattern patternForCSV = Pattern.compile("(.{3}),(.{3}),(.{6}),([0-9]+(?:\\.[0-9]*)?)");
        Matcher matcherForCSV = patternForCSV.matcher(csv);

        Set<Flight> flights = new HashSet<>();

        while (matcherForCSV.find()) {

            String origin = matcherForCSV.group(1);
            String destination = matcherForCSV.group(2);
            String airline = matcherForCSV.group(3);
            float basePrice = Float.parseFloat(matcherForCSV.group(4));

            flights.add(new Flight(origin, destination, airline, basePrice));
        }

        return flights;
    }
}
