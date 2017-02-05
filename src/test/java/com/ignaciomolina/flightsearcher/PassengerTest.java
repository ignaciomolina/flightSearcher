package com.ignaciomolina.flightsearcher;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PassengerTest {

    @Parameter(0) public Passenger passenger;
    @Parameter(1) public String singular;
    @Parameter(2) public String plural;

    @Parameters(name="passenger={0}, singular={1}, plural={2}")
    public static Object[][] data() {

        return new Object[][] {
            {Passenger.ADULT, "adult", "adults"},
            {Passenger.CHILD, "child", "children"},
            {Passenger.INFANT, "infant", "infants"}
        };
    }

    @Test
    public void shouldReturnRightpassengerBasedOnSingular() {

        then(Passenger.byName(singular)).isEqualTo(passenger);
    }

    @Test
    public void shouldReturnRightpassengerBasedOnPlural() {

        then(Passenger.byName(plural)).isEqualTo(passenger);
    }

    @Test
    public void shouldReturnSingularName() {

        then(passenger.getSingular()).isEqualTo(singular);
    }

    @Test
    public void shouldReturnPluralName() {

        then(passenger.getPlural()).isEqualTo(plural);
    }

    @Test
    public void shouldCoverageValueOfAndValues() {

        for (Passenger passenger : Passenger.values()) {

            then(Passenger.valueOf(passenger.name())).isSameAs(passenger);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNameDoesNotMatch() {

        then(Passenger.byName("wrong")).isEqualTo(passenger);
    }
}
