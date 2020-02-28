package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;

public class FareCalculatorService {
    InputReaderUtil inputReaderUtil = new InputReaderUtil();

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        //*Calculating duration between outTime and inTime*/
        long diff = ticket.getOutTime().getTime() - ticket.getInTime().getTime();
        double diffMin = (double) (diff / (60*1000));
        double duration = diffMin / 60; //*duration variable give result in hours*/

        //*Applying first 30 minutes free fees*/
        duration = ticket.LessThirtyMinutes(duration);

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                double Price = (duration * Fare.CAR_RATE_PER_HOUR);
                //*Format Price to two decimals*/
                Price = inputReaderUtil.formatToTwoDecimal(Price);
                //*Set price in database*/
                ticket.setPrice(Price);
                break;
            }
            case BIKE: {
                double Price = (duration * Fare.BIKE_RATE_PER_HOUR);
                //*Format Price to two decimals*/
                Price = inputReaderUtil.formatToTwoDecimal(Price);
                //*Set price in database*/
                ticket.setPrice(Price);
                break;
            }
            default: throw new IllegalArgumentException("Unknown Parking Type");
        }
    }
}