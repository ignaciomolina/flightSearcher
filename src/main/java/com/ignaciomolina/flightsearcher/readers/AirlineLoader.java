package com.ignaciomolina.flightsearcher.readers;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ignaciomolina.flightsearcher.pojo.Airline;

public class AirlineLoader extends CSVLoader<Airline> {

    private static final String PATTERN = "^((?:[0-9]|[A-Z]){2}),(.*),([0-9]+(?:\\.[0-9]*)?)$";

    @Override
    protected Collection<Airline> parseLines(List<String> lines) {

        Set<Airline> airlines = new HashSet<>();
        Pattern pattern = Pattern.compile(PATTERN);

        for (String line : lines) {

            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {

                String code = matcher.group(1);
                String name = matcher.group(2);
                float infantPrice = Float.parseFloat(matcher.group(4));

                airlines.add(new Airline(code, name, infantPrice));
            }
        }

        return airlines;
    }

}
