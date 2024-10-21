package com.plane.enums;

public enum PlaneType {

    PASSENGER("passenger"),
    REGIONAL("regional");

    private final String type;

    PlaneType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}


