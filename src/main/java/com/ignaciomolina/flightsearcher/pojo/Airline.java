package com.ignaciomolina.flightsearcher.pojo;

import java.util.Objects;

/**
 * Class that contains the airlines information.
 * 
 * @author imolina
 */
public class Airline {

    private String code;
    private String name;
    private float infantPrice;

    /**
     * Constructor of the Airline class.
     * 
     * @param code The code that identify the airline
     * @param name Name of the airline
     * @param infantPrice Fixed flight price for infant passengers
     */
    public Airline(String code, String name, float infantPrice) {

        this.code = Objects.requireNonNull(code, "Code cannot be null.");
        this.name = Objects.requireNonNull(name, "Name cannot be null.");
        this.infantPrice = infantPrice;
    }

    /**
     * Method that gets the airline code.
     * 
     * @return airline code
     */
    public String getCode() {

        return code;
    }

    /**
     * Method that gets the airline name.
     * 
     * @return airline name
     */
    public String getName() {

        return name;
    }

    /**
     * Method that gets the airline fixed flight price for infants passangers.
     * 
     * @return fixed flight for infants
     */
    public float getInfantPrice() {

        return infantPrice;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;

        result = prime * result + code.hashCode();
        result = prime * result + Float.floatToIntBits(infantPrice);
        result = prime * result + name.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {

            return true;
        }

        if (obj == null) {

            return false;
        }

        if (getClass() != obj.getClass()) {

            return false;
        }

        Airline other = (Airline) obj;

        if (!code.equals(other.code) ||
            !name.equals(other.name) ||
            infantPrice != other.infantPrice) {

            return false;
        }

        return true;
    }
}
