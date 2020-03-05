package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;

public class FareCalculatorService {
    InputReaderUtil inputReaderUtil = new InputReaderUtil();
    TicketDAO ticketDAO = new TicketDAO();

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        long diff = ticket.getOutTime().getTime() - ticket.getInTime().getTime();
        double diffMin = (double) (diff / (60*1000));
        double duration = diffMin / 60;

        duration = inputReaderUtil.LessThirtyMinutes(duration);

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                double Price = (duration * Fare.CAR_RATE_PER_HOUR);

                boolean regularCustomer = ticketDAO.getHistory(ticket.getVehicleRegNumber());
                if (regularCustomer) {
                    inputReaderUtil.applyFivePourcentOff(Price);
                }

                Price = inputReaderUtil.formatToTwoDecimal(Price);
                ticket.setPrice(Price);
                break;
            }
            case BIKE: {
                double Price = (duration * Fare.BIKE_RATE_PER_HOUR);

                boolean regularCustomer = ticketDAO.getHistory(ticket.getVehicleRegNumber());
                if (regularCustomer) {
                    inputReaderUtil.applyFivePourcentOff(Price);
                }

                Price = inputReaderUtil.formatToTwoDecimal(Price);
                ticket.setPrice(Price);
                break;
            }
            default: throw new IllegalArgumentException("Unknown Parking Type");
        }
    }

    public void setTicketDAO(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }
}