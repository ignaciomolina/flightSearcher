package com.ignaciomolina.flightsearcher;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author imolina
 *
 */
public class FlightSearcherIT {

    private ByteArrayOutputStream outCaptor = new ByteArrayOutputStream();

    @Before
    public void setup() {

        System.setOut(new PrintStream(outCaptor));
    }

    @After
    public void tearDown() {

        System.setOut(null);
    }

    @Test
    public void shouldPrintFlightsList() throws IOException {

        String [] args = {"LHR", "IST", "15", "2 adults, 1 child, 1 infant"};
        Main.main(args);

        String expected = "2 adults, 1 child, 1 infant, " +
                          "15 days to the departure date, " +
                          "flyaing LHR -> IST\n"+
                          "LH1085, 481,19 €\n" +
                          "TK8891, 806,00 €\n" ;

        assertEquals(expected, outCaptor.toString());
    }
}
