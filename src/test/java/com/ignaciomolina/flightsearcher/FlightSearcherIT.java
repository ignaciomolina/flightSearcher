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

    private static final String USEAGE_EXAMPLE = "Example: FlightSearcher MAD " +
                                                "BCN 40 \"2 adult, 2 children" +
                                                ", 1 infants\"\n";

    private ByteArrayOutputStream outCaptor = new ByteArrayOutputStream();
    private ByteArrayOutputStream errCaptor = new ByteArrayOutputStream();

    @Before
    public void setup() {

        System.setOut(new PrintStream(outCaptor));
        System.setErr(new PrintStream(errCaptor));
    }

    @After
    public void tearDown() {

        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void shouldPrintFlightsList() throws IOException {

        String [] args = {"LHR", "IST", "15", "2 adults, 1 child, 1 infant"};
        String expected = "2 adults, 1 child, 1 infant, " +
                          "15 days to the departure date, " +
                          "flyaing LHR -> IST\n"+
                          "LH1085, 481,19 €\n" +
                          "TK8891, 806,00 €\n" ;

        checkOutputMessage(args, expected);
    }

    @Test
    public void shouldPrintErrorMessageWhenNopassengers() throws IOException {

        String [] args = {"LHR", "IST", "15", "0 adults"};
        String expected = "Error due to: It should be at least one passenger.\n" +
                          USEAGE_EXAMPLE;

        checkErrorMessage(args, expected);
    }

    @Test
    public void shouldPrintErrorMessageWhenBadFormatAirportCode() throws IOException {

        String [] args = {"XXXX", "IST", "15", "1 adult"};
        String expected = "Error due to: Illegal Airport code, Code should be " +
                          "composed of three capital letters.\n" +
                          USEAGE_EXAMPLE;

        checkErrorMessage(args, expected);
    }

    @Test
    public void shouldPrintErrorMessageWhenNotEnoughParams() throws IOException {

        String [] args = {"LHR", "IST", "15"};
        String expected = "FlightSearcher <origin> <destination> <days " +
                           "to departure> \"<passengers>\"\n" +
                           USEAGE_EXAMPLE;

        checkErrorMessage(args, expected);
    }

    private void checkOutputMessage(String[] args, String expected) throws IOException {

        checkMessage(args, expected, outCaptor);
    }

    private void checkErrorMessage(String[] args, String expected) throws IOException {

        checkMessage(args, expected, errCaptor);
    }

    private void checkMessage(String[] args, String expected,
                              ByteArrayOutputStream captor) throws IOException {

        Main.main(args);

        assertEquals(expected, captor.toString());
    }
}
