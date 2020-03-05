package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FareCalculatorServiceTest {

    private static FareCalculatorService fareCalculatorService;
    private static Ticket ticket;
    private static InputReaderUtil inputReaderUtil;

    @Mock
    private static TicketDAO ticketDAO;

    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
        inputReaderUtil = new InputReaderUtil();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
        ticket.setVehicleRegNumber("AAA");
    }

    /** Testing exceptions */
    @Test
    public void calculateFareUnknownType() {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        try {
            assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
        }
        catch (AssertionError ae) {
            throw ae;
        }
        System.out.print("Test passed = NullPointerException: parking type not provided");
    }

    @Test
    public void calculateFareBikeWithFutureInTime() {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() + (60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        try {
            assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));

        }
        catch (AssertionError ae) {
            throw ae;
        }
        System.out.print("Test passed = IllegalArgumentException: you cannot park in future time");
    }

    /**Testing Functional */
    @Test
    public void calculateFareCarForOneHourAndNotRegularCustomer() {
        when(ticketDAO.getHistory(anyString())).thenReturn(false);

        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (60 * 60 * 1000) ); // Setting ticket time
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        fareCalculatorService.setTicketDAO(ticketDAO);
        fareCalculatorService.calculateFare(ticket);

        double duration = 1; // Setting time for assert
        duration = inputReaderUtil.LessThirtyMinutes(duration);
        double Price = (duration) * Fare.CAR_RATE_PER_HOUR;
        Price = inputReaderUtil.formatToTwoDecimal(Price);

        try {
            assertEquals((Price), ticket.getPrice() );
            System.out.print("Test passed: " + Price +" (expected) = "+ ticket.getPrice() + " (actual)");
        }
        catch (AssertionError ae) {
            throw ae;
        }
    }

    @Test
    public void calculateFareCarWithOneHourParkingTimeAndRegularCustomer(){
        when(ticketDAO.getHistory(anyString())).thenReturn(true);

        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (60 * 60 * 1000) ); // Setting one hour parking time for ticket
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        fareCalculatorService.setTicketDAO(ticketDAO);
        fareCalculatorService.calculateFare(ticket);

        double duration = 1; // Setting 1 hour parking time for assert
        duration = inputReaderUtil.LessThirtyMinutes(duration);
        double Price = ((duration) * Fare.CAR_RATE_PER_HOUR) * 0.95; // -5% Off regular customer
        Price = inputReaderUtil.formatToTwoDecimal(Price);
        assertEquals((Price) , ticket.getPrice());

        try {
            assertEquals((Price), ticket.getPrice() );
            System.out.print("Test passed: " + Price +" (expected) = "+ ticket.getPrice() + " (actual)");
        }
        catch (AssertionError ae) {
            throw ae;
        }
    }

    @Test
    public void calculateFareBikeForOneHourAndNotRegularCustomer() {
        when(ticketDAO.getHistory(anyString())).thenReturn(false);

        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        fareCalculatorService.setTicketDAO(ticketDAO);
        fareCalculatorService.calculateFare(ticket);

        double duration = 1; // Setting time for assert
        duration = inputReaderUtil.LessThirtyMinutes(duration);
        double Price = (duration) * Fare.BIKE_RATE_PER_HOUR;
        Price = inputReaderUtil.formatToTwoDecimal(Price);

        try {
            assertEquals((Price), ticket.getPrice() );
            System.out.print("Test passed: " + Price +" (expected) = "+ ticket.getPrice() + " (actual)");
        }
        catch (AssertionError ae) {
            throw ae;
        }
    }

    @Test
    public void calculateFareBikeForOneHourAndRegularCustomer() {
        when(ticketDAO.getHistory(anyString())).thenReturn(true);

        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        fareCalculatorService.setTicketDAO(ticketDAO);
        fareCalculatorService.calculateFare(ticket);

        double duration = 1; // Setting time for assert
        duration = inputReaderUtil.LessThirtyMinutes(duration);
        double Price = (duration) * Fare.BIKE_RATE_PER_HOUR * 0.95;
        Price = inputReaderUtil.formatToTwoDecimal(Price);

        try {
            assertEquals((Price), ticket.getPrice() );
            System.out.print("Test passed: " + Price +" (expected) = "+ ticket.getPrice() + " (actual)");
        }
        catch (AssertionError ae) {
            throw ae;
        }
    }

    @Test
    public void calculateFareBikeWithLessThanThirtyMinutesParkingTime() {
        when(ticketDAO.getHistory(anyString())).thenReturn(true);

        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (20 * 60 * 1000) ); // Setting 15 minutes parking time for ticket
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        fareCalculatorService.setTicketDAO(ticketDAO);
        fareCalculatorService.calculateFare(ticket);

        double duration = 0.25; // Setting 15 minutes parking time for assert
        duration = inputReaderUtil.LessThirtyMinutes(duration);
        double Price = (duration) * Fare.BIKE_RATE_PER_HOUR;
        Price = inputReaderUtil.formatToTwoDecimal(Price);

        try {
            assertEquals((Price) , ticket.getPrice());
            System.out.print("Test passed: " + Price +" (expected) = "+ ticket.getPrice() + " (actual)");
        }
        catch (AssertionError ae) {
            throw ae;
        }
    }

    @Test
    public void calculateFareCarWithLessThanThirtyMinutesParkingTime() {
        when(ticketDAO.getHistory(anyString())).thenReturn(true);

        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (15 * 60 * 1000) ); // Setting 15 minutes parking time for ticket
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        fareCalculatorService.setTicketDAO(ticketDAO);
        fareCalculatorService.calculateFare(ticket);

        double duration = 0.25; // Setting 45 minutes parking time for assert
        duration = inputReaderUtil.LessThirtyMinutes(duration);
        double Price = (duration) * Fare.CAR_RATE_PER_HOUR;
        Price = inputReaderUtil.formatToTwoDecimal(Price);

        try {
            assertEquals((Price) , ticket.getPrice());
            System.out.print("Test passed: " + Price +" (expected) = "+ ticket.getPrice() + " (actual)");
        }
        catch (AssertionError ae) {
            throw ae;
        }
    }

    @Test
    public void calculateDurationForMoreThanADayParkingTimeNotRegularCustomer(){
        when(ticketDAO.getHistory(anyString())).thenReturn(false);

        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (25 * 60 * 60 * 1000) ); //Setting 25 hours parking time for ticket
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        fareCalculatorService.setTicketDAO(ticketDAO);
        fareCalculatorService.calculateFare(ticket);

        double duration = 25; //Setting 25 hours parking time for assert
        duration = inputReaderUtil.LessThirtyMinutes(duration);
        double Price = (duration) * Fare.CAR_RATE_PER_HOUR;
        Price = inputReaderUtil.formatToTwoDecimal(Price);

        try {
            assertEquals((Price) , ticket.getPrice());
            System.out.print("Test passed: " + Price +" (expected) = "+ ticket.getPrice() + " (actual)");
        }
        catch (AssertionError ae) {
            throw ae;
        }
    }
}
