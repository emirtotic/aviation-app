package com.flight.dto;

import com.flight.entity.Passenger;
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
    private String arrivalAirport;
    private Date createdAt;
    private Date departureTime;
    private Date arrivalTime;
    private CompanyDetails company;
    private BigDecimal flightDistance;
    private BigDecimal flightDuration;

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

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
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

    public BigDecimal getFlightDistance() {
        return flightDistance;
    }

    public void setFlightDistance(BigDecimal flightDistance) {
        this.flightDistance = flightDistance;
    }

    public BigDecimal getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(BigDecimal flightDuration) {
        this.flightDuration = flightDuration;
    }
}
