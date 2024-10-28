package com.flight.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightDto {

    private PassengerDTO passenger;
    private String planeModel;
    private int averagePlaneSpeed;
    private String departureAirport;
    private String departureAirportIata;
    private String departureAirportCountry;
    private String arrivalAirport;
    private String arrivalAirportIata;
    private String arrivalAirportCountry;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date departureTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arrivalTime;
    private CompanyDetails company;
    private BigDecimal flightDistanceKm;
    private BigDecimal flightDurationInMinutes;


    public PassengerDTO getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerDTO passenger) {
        this.passenger = passenger;
    }

    public String getPlaneModel() {
        return planeModel;
    }

    public void setPlaneModel(String planeModel) {
        this.planeModel = planeModel;
    }

    public int getAveragePlaneSpeed() {
        return averagePlaneSpeed;
    }

    public void setAveragePlaneSpeed(int averagePlaneSpeed) {
        this.averagePlaneSpeed = averagePlaneSpeed;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getDepartureAirportIata() {
        return departureAirportIata;
    }

    public void setDepartureAirportIata(String departureAirportIata) {
        this.departureAirportIata = departureAirportIata;
    }

    public String getDepartureAirportCountry() {
        return departureAirportCountry;
    }

    public void setDepartureAirportCountry(String departureAirportCountry) {
        this.departureAirportCountry = departureAirportCountry;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getArrivalAirportIata() {
        return arrivalAirportIata;
    }

    public void setArrivalAirportIata(String arrivalAirportIata) {
        this.arrivalAirportIata = arrivalAirportIata;
    }

    public String getArrivalAirportCountry() {
        return arrivalAirportCountry;
    }

    public void setArrivalAirportCountry(String arrivalAirportCountry) {
        this.arrivalAirportCountry = arrivalAirportCountry;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public CompanyDetails getCompany() {
        return company;
    }

    public void setCompany(CompanyDetails company) {
        this.company = company;
    }

    public BigDecimal getFlightDistanceKm() {
        return flightDistanceKm;
    }

    public void setFlightDistanceKm(BigDecimal flightDistanceKm) {
        this.flightDistanceKm = flightDistanceKm;
    }

    public BigDecimal getFlightDurationInMinutes() {
        return flightDurationInMinutes;
    }

    public void setFlightDurationInMinutes(BigDecimal flightDurationInMinutes) {
        this.flightDurationInMinutes = flightDurationInMinutes;
    }
}
