package com.ignaciomolina.flightsearcher;

/**
 * Class that contains types of passengers.
 * 
 * @author imolina
 */
public enum Passenger {

    ADULT("adult", "adults"),
    CHILD("child", "children"),
    INFANT("infant", "infants");

    private String singular;
    private String plural;

    private Passenger(String name, String plural) {

        this.singular = name;
        this.plural = plural;
    }

    /**
     * Method that gets the type of passenger.
     * 
     * @return singular name of passenger type
     */
    public String getSingular() {

        return singular;
    }

    /**
     * Method that gets the plural of the passenger type.
     * 
     * @return plural name of passenger type
     */
    public String getPlural() {

        return plural;
    }

    /**
     * Method that look for a passenger type according to its name.
     * 
     * @param name passenger type's name
     * @return a passenger type if any. 
     */
    public static Passenger byName(String name) {

        for (Passenger passenger : Passenger.values()) {

            if (passenger.singular.equals(name) || passenger.plural.equals(name)) {

                return passenger;
            }
        }

        throw new IllegalArgumentException("Unknown type of passenger: " + name);
    }
}
