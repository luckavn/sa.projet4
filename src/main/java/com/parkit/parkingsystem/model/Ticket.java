package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.dao.TicketDAO;

import java.util.Date;

public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private Date inTime;
    private Date outTime;
    TicketDAO ticketDAO = new TicketDAO();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public boolean getHistory(String vehicleRegNumber) {
        return ticketDAO.getHistory(vehicleRegNumber);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getInTime() {
        Date inDate = inTime;
        return inDate;
    }

    public void setInTime(Date inTime) {
        this.inTime = new Date (inTime.getTime());
    }

    public Date getOutTime() {
        Date outDate = outTime;
        return outDate;
    }

    public void setOutTime(Date outTime) {
        if (outTime == null) {
            this.outTime = null;
        } else
        this.outTime = new Date (outTime.getTime());
    }
}
