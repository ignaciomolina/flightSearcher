package com.ignaciomolina.flightsearcher.readers;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author imolina
 *
 */
public class CSVLoaderTest {

    private static final String EMPTY_CSV = "emptyflights.csv";

    private HelperLoader loader;

    @Before
    public void setup() {

        loader = new HelperLoader();
    }

    @Test
    public void shouldReturnEmptyList() throws IOException {

        Collection<String> flights = loader.load(EMPTY_CSV);

        then(flights).isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNoRightExtention() throws IOException {

        loader.load("flightscsv");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptNonExistingFile() throws IOException {

        loader.load("non-existing.csv");
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullFilename() throws IOException {

        loader.load(null);
    }

    public class HelperLoader extends CSVLoader<String> {

        protected Set<String> parseLines(List<String> lines) {

            return new HashSet<>(lines);
        }
    }
}
