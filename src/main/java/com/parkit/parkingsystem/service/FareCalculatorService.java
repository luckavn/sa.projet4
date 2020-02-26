package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        int inDay = ticket.getInTime().getDay();
        int outDay = ticket.getOutTime().getDay();
        int inHour = ticket.getInTime().getHours();
        int outHour = ticket.getOutTime().getHours();
        int inMinutes  = ticket.getInTime().getMinutes();
        int outMinutes  = ticket.getOutTime().getMinutes();
        int minutesDuration;

        if (inMinutes > outMinutes) {
            minutesDuration = (60 - inMinutes) + outMinutes;
        } else {
            minutesDuration = outMinutes - inMinutes;
        }

        int hourDurationInMinutes = (outHour - inHour) * 60;
        int dayInMinutes = ((outDay - inDay) * 24) * 60;
        int totalDuration = minutesDuration + hourDurationInMinutes + dayInMinutes;

        //TODO: Some tests are failing here. Need to check if this logic is correct

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                double calculatingTicketPrice = totalDuration * Fare.CAR_RATE_PER_MINUTES;
                double ticketPrice =  Math.round(calculatingTicketPrice * (1.0/0.01)) / (1.0/0.01);
                ticket.setPrice(ticketPrice);
                break;
            }
            case BIKE: {
                double calculatingTicketPrice = totalDuration * Fare.BIKE_RATE_PER_MINUTES;
                double ticketPrice =  Math.round(calculatingTicketPrice * (1.0/0.01)) / (1.0/0.01);
                ticket.setPrice(ticketPrice);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}