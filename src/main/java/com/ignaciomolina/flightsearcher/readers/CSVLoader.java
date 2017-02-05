package com.ignaciomolina.flightsearcher.readers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Abstract class that reads CSV files and delegate line parsing.
 * 
 * @param <T> Type of the objects that will contain the load collection.
 * @author imolina
 */
public abstract class CSVLoader<T> {

    /**
     * Validates CSV files and load a collection according to an abstract method.
     * 
     * @param filename Route of the file inside the jar file scope. 
     * @return A collection of {@code T}.
     */
    public Collection<T> load(String filename) {

        Objects.requireNonNull(filename, "Filename cannot be null.");

        Pattern pattern = Pattern.compile(".*\\.csv$");
        Matcher matcher = pattern.matcher(filename);

        if (!matcher.matches()) {

            throw new IllegalArgumentException("File has wrong extention. " +
                                               "Only CSV is accepted: " +
                                               filename);
        }

        try (InputStream is = this.getClass().getResourceAsStream(filename);
             BufferedReader br = new BufferedReader(new InputStreamReader(is,
                                                     StandardCharsets.UTF_8))) {

            List<String> lines = br.lines().collect(Collectors.toList());

            return parseLines(lines);

        } catch (Exception e) {

            throw new IllegalArgumentException("File " + filename + " cannot " +
                                               "be found in jar.", e);
        }
    }

    protected abstract Collection<T> parseLines(List<String> lines);
}
