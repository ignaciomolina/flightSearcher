package com.ignaciomolina.flightsearcher;

/**
 * 
 * @author imolina
 *
 */
public enum Passanger {

    ADULT("adult", "adults"),
    CHILD("child", "children"),
    INFANT("infant", "infants");

    private String singular;
    private String plural;

    private Passanger(String name, String plural) {

        this.singular = name;
        this.plural = plural;
    }

    public String getSingular() {

        return singular;
    }

    public String getPlural() {

        return plural;
    }

    public static Passanger byName(String name) {

        for (Passanger passanger : Passanger.values()) {

            if (passanger.singular.equals(name) || passanger.plural.equals(name)) {

                return passanger;
            }
        }

        throw new IllegalArgumentException("Unknown type of passanger: " + name);
    }
}
