package com.flight.enums;

public enum Gender {

    MALE("male"),
    FEMALE("female"),
    UNKNOWN("Unknown");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return gender;
    }

}
