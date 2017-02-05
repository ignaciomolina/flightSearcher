package com.ignaciomolina.flightsearcher.pojo;

import static org.assertj.core.api.BDDAssertions.then;

import org.assertj.core.api.BDDSoftAssertions;
import org.junit.Before;
import org.junit.Test;

import com.ignaciomolina.flightsearcher.pojo.Flight;

/**
 * 
 * @author imolina
 *
 */
public class FlightTest {

    private static final float PRICE = 186.0F;
    private static final String CODE = "IB2818";
    private static final String AIRLINE = "IB";
    private static final String DESTINATION = "FRA";
    private static final String ORIGIN = "CPH";

    private Flight flight;

    @Before
    public void setup() {

        flight = new Flight(ORIGIN, DESTINATION, AIRLINE, CODE, PRICE);
    }

    @Test
    public void shouldCreateValidFlight() {

        BDDSoftAssertions softly = new BDDSoftAssertions();

        softly.then(flight.getOrigin()).as("Origin").isEqualTo(ORIGIN);
        softly.then(flight.getDestination()).as("Destination").isEqualTo(DESTINATION);
        softly.then(flight.getAirline()).as("Airline").isEqualTo(AIRLINE);
        softly.then(flight.getCode()).as("Code").isEqualTo(CODE);
        softly.then(flight.getBasePrice()).as("Price").isEqualTo(PRICE);

        softly.assertAll();
    }

    private void checkEquality(Flight flight, Flight other, boolean areEquals) {

        BDDSoftAssertions softly = new BDDSoftAssertions();

        then(flight.equals(other)).as("Equal method").isEqualTo(areEquals);
        then(flight.hashCode() == other.hashCode()).as("hashCode method").isEqualTo(areEquals);

        softly.assertAll();

    }

    @Test
    public void shouldFlightBeEqualToItself() {

        checkEquality(flight, flight, true);
    }

    @Test
    public void shouldFlightBeEqualToOtherFlightWithSameFields() {

        Flight other = new Flight(ORIGIN, DESTINATION, AIRLINE, CODE, PRICE);

        checkEquality(flight, other, true);
    }

    @Test
    public void shouldFlightBeNotEqualToOtherFlightWithDifferentOrigin() {

        Flight other = new Flight("FSH", DESTINATION, AIRLINE, CODE, PRICE);

        checkEquality(flight, other, false);
    }

    @Test
    public void shouldFlightBeNotEqualToOtherFlightWithDifferentDestination() {

        Flight other = new Flight(ORIGIN, "FSH", AIRLINE, CODE, PRICE);

        checkEquality(flight, other, false);
    }

    @Test
    public void shouldFlightBeNotEqualToOtherFlightWithDifferentAirline() {

        Flight other = new Flight(ORIGIN, DESTINATION, "U2", CODE, PRICE);

        checkEquality(flight, other, false);
    }

    @Test
    public void shouldFlightBeNotEqualToOtherFlightWithDifferentCode() {

        Flight other = new Flight(ORIGIN, DESTINATION, AIRLINE, "IB1234", PRICE);

        checkEquality(flight, other, false);
    }

    @Test
    public void shouldFlightBeNotEqualToOtherFlightWithDifferentPrice() {

        Flight other = new Flight(ORIGIN, DESTINATION, AIRLINE, CODE, 100.0F);

        checkEquality(flight, other, false);
    }

    @Test
    public void shouldFlightBeNotEqualToNull() {

        then(flight).isNotEqualTo(null);
    }

    @Test(expected=NullPointerException.class)
    public void shouldNotAcceptNullOrigin() {

        flight = new Flight(null, DESTINATION, AIRLINE, CODE, PRICE);
    }

    @Test(expected=NullPointerException.class)
    public void shouldNotAcceptNullDestination() {

        flight = new Flight(ORIGIN, null, AIRLINE, CODE, PRICE);
    }

    @Test(expected=NullPointerException.class)
    public void shouldNotAcceptNullAirline() {

        flight = new Flight(ORIGIN, DESTINATION, null, CODE, PRICE);
    }

    @Test(expected=NullPointerException.class)
    public void shouldNotAcceptNullCode() {

        flight = new Flight(ORIGIN, DESTINATION, AIRLINE, null, PRICE);
    }
}
