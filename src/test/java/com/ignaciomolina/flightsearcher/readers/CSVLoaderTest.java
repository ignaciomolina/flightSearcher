package com.ignaciomolina.flightsearcher.readers;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * @author imolina
 *
 */
public class CSVLoaderTest {

    private static final String TWO_ROWS_CSV = "twoRows.csv";
    private static final String EMPTY_CSV = "emptyflights.csv";

    private HelperLoader loader;

    @Before
    public void setup() {

        loader = new HelperLoader();
    }

    @Test
    public void shouldReturnListWithTwoElements() throws IOException {

        Collection<String> collection = loader.load(TWO_ROWS_CSV);

        then(collection).hasSize(2);
    }

    @Ignore
    @Test
    public void shouldReturnEmptyList() throws IOException {

        Collection<String> collection = loader.load(EMPTY_CSV);

        then(collection).isEmpty();
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

        protected List<String> parseLines(List<String> lines) {

            return new ArrayList<>(lines);
        }
    }
}
