package com.ignaciomolina.flightsearcher.calculators;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ignaciomolina.flightsearcher.Passenger;
import com.ignaciomolina.flightsearcher.pojo.Airline;
import com.ignaciomolina.flightsearcher.pojo.Flight;

/**
 * 
 * @author imolina
 *
 */
public class PriceCalculatorTest {

    private static final double DISCOUNT_FACTOR1 = 0.8D;
    private static final double DISCOUNT_FACTOR2 = 1.0D;
    private static final double DISCOUNT_FACTOR3 = 1.2D;
    private static final double DISCOUNT_FACTOR4 = 1.5D;
    private static final double CHILD_DISCOUNT_FACTOR = 0.67D;

    private static final int DAY_IN_FACTOR1 = 31;
    private static final int DAY_IN_FACTOR2 = 30;
    private static final int DAY_IN_FACTOR3 = 15;
    private static final int DAY_IN_FACTOR4 = 2;

    private static final String AIRLINE_CODE = "IB";

    private Collection<Passenger> passengers;
    private Collection<Airline> airlinesWithInfantDiscount;
    private PriceCalculator calculator;

    @Mock private Flight flight;
    @Mock private Airline airline;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        given(flight.getAirline()).willReturn(AIRLINE_CODE);
        given(airline.getCode()).willReturn(AIRLINE_CODE);

        passengers = new ArrayList<>();
        airlinesWithInfantDiscount = new ArrayList<>();
        airlinesWithInfantDiscount.add(airline);
        calculator = new PriceCalculator(airlinesWithInfantDiscount);
    }

    private void checkDiscount(double factor, int days) {

        float basePrice = 1000.0F;
        double expected =  basePrice * factor;

        given(flight.getBasePrice()).willReturn(basePrice);

        double price = calculator.calculate(flight, passengers, days);

        then(price).isEqualTo(expected);
    }

    @Test
    public void shouldReturnAdultPriceWithDiscountFactorOne() {

        passengers.add(Passenger.ADULT);

        checkDiscount(DISCOUNT_FACTOR1, DAY_IN_FACTOR1);
    }

    @Test
    public void shouldReturnAdultPriceWithDiscountFactorTwo() {

        passengers.add(Passenger.ADULT);

        checkDiscount(DISCOUNT_FACTOR2, DAY_IN_FACTOR2);
    }

    @Test
    public void shouldReturnAdultPriceWithDiscountFactorThree() {

        passengers.add(Passenger.ADULT);

        checkDiscount(DISCOUNT_FACTOR3, DAY_IN_FACTOR3);
    }

    @Test
    public void shouldReturnAdultPriceWithDiscountFactorFourth() {

        passengers.add(Passenger.ADULT);

        checkDiscount(DISCOUNT_FACTOR4, DAY_IN_FACTOR4);
    }

    @Test
    public void shouldReturnChildPriceWithDiscountFactorOne() {

        passengers.add(Passenger.CHILD);
        double factor = DISCOUNT_FACTOR1 * CHILD_DISCOUNT_FACTOR;

        checkDiscount(factor, DAY_IN_FACTOR1);
    }

    @Test
    public void shouldReturnChildPriceWithDiscountFactorTwo() {

        passengers.add(Passenger.CHILD);
        double factor = DISCOUNT_FACTOR2 * CHILD_DISCOUNT_FACTOR;

        checkDiscount(factor, DAY_IN_FACTOR2);
    }

    @Test
    public void shouldReturnChildPriceWithDiscountFactorThree() {

        passengers.add(Passenger.CHILD);
        double factor = DISCOUNT_FACTOR3 * CHILD_DISCOUNT_FACTOR;

        checkDiscount(factor, DAY_IN_FACTOR3);
    }

    @Test
    public void shouldReturnChildPriceWithDiscountFactorFourth() {

        passengers.add(Passenger.CHILD);
        double factor = DISCOUNT_FACTOR4 * CHILD_DISCOUNT_FACTOR;

        checkDiscount(factor, DAY_IN_FACTOR4);
    }

    @Test
    public void shouldReturnChildPriceWhenAirlineHasNoInfantDiscount() {

        passengers.add(Passenger.INFANT);
        double factor = DISCOUNT_FACTOR1 * CHILD_DISCOUNT_FACTOR;

        given(flight.getAirline()).willReturn("U2");

        checkDiscount(factor, DAY_IN_FACTOR1);
    }
    
    @Test
    public void shouldReturnConstantPriceForInfant() {

        passengers.add(Passenger.INFANT);
        float basePrice = 100.0F;
        float expected = 15.0F;

        given(flight.getBasePrice()).willReturn(basePrice);
        given(airline.getInfantPrice()).willReturn(expected);

        double price1 = calculator.calculate(flight, passengers, DAY_IN_FACTOR1);
        double price2 = calculator.calculate(flight, passengers, DAY_IN_FACTOR2);
        double price3 = calculator.calculate(flight, passengers, DAY_IN_FACTOR3);
        double price4 = calculator.calculate(flight, passengers, DAY_IN_FACTOR4);

        then(price1).isEqualTo(expected);
        then(price2).isEqualTo(expected);
        then(price3).isEqualTo(expected);
        then(price4).isEqualTo(expected);
    }

    @Test
    public void shouldReturnZeroPriceWhenEmptypassengersList() {

        double price = calculator.calculate(flight, passengers, DAY_IN_FACTOR1);
        then(price).isZero();
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullFlightWhenCalculate() {

        calculator.calculate(null, passengers, DAY_IN_FACTOR1);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullpassengersListWhenCalculate() {

        calculator.calculate(flight, null, DAY_IN_FACTOR1);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullAirlinesCollection() {

        calculator = new PriceCalculator(null);
    }
}
