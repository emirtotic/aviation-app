package com.plane.entity;

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


