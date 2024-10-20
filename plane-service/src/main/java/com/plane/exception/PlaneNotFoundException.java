package com.plane.exception;

public class PlaneNotFoundException extends RuntimeException {
    public PlaneNotFoundException(Long id) {
        super("Plane not found with ID: " + id);
    }
}

