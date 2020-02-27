package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.text.DecimalFormat;

public class FareCalculatorService {
    private static DecimalFormat df = new DecimalFormat("#.##");

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        long diff = ticket.getOutTime().getTime() - ticket.getInTime().getTime();
        double diffMin = (double) (diff / (60*1000));
        double duration = diffMin / 60;

        //TODO: Some tests are failing here. Need to check if this logic is correct

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                double Price = (duration * Fare.CAR_RATE_PER_HOUR);
                ticket.setPrice(Price);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unknown Parking Type");
        }
    }
}