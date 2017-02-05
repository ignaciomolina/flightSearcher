package com.ignaciomolina.flightsearcher.pojo;

import java.util.Objects;

/**
 * 
 * @author imolina
 *
 */
public class Airline {

    private String code;
    private String name;
    private float infantPrice;

    public Airline(String code, String name, float infantPrice) {

        this.code = Objects.requireNonNull(code, "Code cannot be null.");
        this.name = Objects.requireNonNull(name, "Name cannot be null.");
        this.infantPrice = infantPrice;
    }

    public String getCode() {

        return code;
    }

    public String getName() {

        return name;
    }

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
