package com.ignaciomolina.flightsearcher;

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

    public static Passanger byName(String name) {

        for (Passanger passanger : Passanger.values()) {

            if (passanger.singular.equals(name) || passanger.plural.equals(name)) {

                return passanger;
            }
        }

        throw new IllegalArgumentException("Unknown name: " + name);
    }
}
