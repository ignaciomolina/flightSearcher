package com.ignaciomolina.flightsearcher;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PassangerTest {

    @Parameter(0) public Passanger passanger;
    @Parameter(1) public String singular;
    @Parameter(2) public String plural;

    @Parameters(name="Passanger={0}, singular={1}, plural={2}")
    public static Object[][] data() {

        return new Object[][] {
            {Passanger.ADULT, "adult", "adults"},
            {Passanger.CHILD, "child", "children"},
            {Passanger.INFANT, "infant", "infants"}
        };
    }

    @Test
    public void shouldReturnRightPassangerBasedOnSingular() {

        then(Passanger.byName(singular)).isEqualTo(passanger);
    }

    @Test
    public void shouldReturnRightPassangerBasedOnPlural() {

        then(Passanger.byName(plural)).isEqualTo(passanger);
    }

    @Test
    public void shouldReturnSingularName() {

        then(passanger.getSingular()).isEqualTo(singular);
    }

    @Test
    public void shouldReturnPluralName() {

        then(passanger.getPlural()).isEqualTo(plural);
    }

    @Test
    public void shouldCoverageValueOfAndValues() {

        for (Passanger passanger : Passanger.values()) {

            then(Passanger.valueOf(passanger.name())).isSameAs(passanger);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNameDoesNotMatch() {

        then(Passanger.byName("wrong")).isEqualTo(passanger);
    }
}
