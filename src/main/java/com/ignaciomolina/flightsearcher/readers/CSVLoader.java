package com.ignaciomolina.flightsearcher.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author imolina
 *
 */
public abstract class CSVLoader<T> {

    public Set<T> load(String filename) throws IOException {

        Objects.requireNonNull(filename, "Filename cannot be null.");

        Pattern pattern = Pattern.compile(".*\\.csv$");
        Matcher matcher = pattern.matcher(filename);

        if (!matcher.matches()) {

            throw new IllegalArgumentException("File has wrong extention. " +
                                               "Only CSV is accepted: " + filename);
        }

        List<String> lines = new ArrayList<>();

        try (InputStream is = this.getClass().getResourceAsStream(filename);
             BufferedReader br = new BufferedReader(new InputStreamReader(is,
                                                     StandardCharsets.UTF_8))) {

             br.lines().forEach(e -> lines.add(e));
        } catch (Exception e) {

            throw new IllegalArgumentException("File " + filename + " cannot " +
                                               "be found in jar.");
        }

        return parseLines(lines);
    }

    protected abstract Set<T> parseLines(List<String> lines);
}
