package com.ignaciomolina.flightsearcher.pojo;

import static org.assertj.core.api.BDDAssertions.then;

import org.assertj.core.api.BDDSoftAssertions;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author imolina
 *
 */
public class AirlineTest {

    private static final float INFANT_PRICE = 18;
    private static final String NAME = "Iberia";
    private static final String CODE = "IB";

    private Airline airline;

    @Before
    public void setup() {

        airline = new Airline(CODE, NAME, INFANT_PRICE);
    }

    @Test
    public void shouldCreateValidAirline() {

        BDDSoftAssertions softly = new BDDSoftAssertions();

        softly.then(airline.getCode()).as("Code").isEqualTo(CODE);
        softly.then(airline.getName()).as("Name").isEqualTo(NAME);
        softly.then(airline.getInfantPrice()).as("Infant Price").isEqualTo(INFANT_PRICE);

        softly.assertAll();
    }

    private void checkEquality(Airline airline, Airline other, boolean areEquals) {

        BDDSoftAssertions softly = new BDDSoftAssertions();

        then(airline.equals(other)).as("Equal method").isEqualTo(areEquals);
        then(airline.hashCode() == other.hashCode()).as("hashCode method").isEqualTo(areEquals);

        softly.assertAll();

    }

    @Test
    public void shouldAirlineBeEqualToItself() {

        checkEquality(airline, airline, true);
    }

    @Test
    public void shouldAirlineBeEqualToOtherAirlineWithSameFields() {

        Airline other = new Airline(CODE, NAME, INFANT_PRICE);

        checkEquality(airline, other, true);
    }

    @Test
    public void shouldAirlineBeNotEqualToOtherAirlineWithDifferentCode() {

        Airline other = new Airline("XX", NAME, INFANT_PRICE);

        checkEquality(airline, other, false);
    }

    @Test
    public void shouldAirlineBeNotEqualToOtherAirlineWithDifferentNamee() {

        Airline other = new Airline(CODE, "MyAirline", INFANT_PRICE);

        checkEquality(airline, other, false);
    }

    @Test
    public void shouldAirlineBeNotEqualToOtherAirlineWithDifferentInfantPrice() {

        Airline other = new Airline(CODE, NAME, 100.0F);

        checkEquality(airline, other, false);
    }

    @Test
    public void shouldAirlineBeNotEqualToOtherObject() {

        then(airline).isNotEqualTo(new Object());
    }

    @Test
    public void shouldAirlineBeNotEqualToNull() {

        then(airline).isNotEqualTo(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullCode() {

        airline = new Airline(null, NAME, INFANT_PRICE);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullName() {

        airline = new Airline(CODE, null, INFANT_PRICE);
    }
}
