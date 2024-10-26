package com.company.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String input) {
        super("Company not found for: " + input);
    }
}

