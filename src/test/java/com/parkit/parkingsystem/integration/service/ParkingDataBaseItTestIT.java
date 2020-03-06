package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseItTestIT {
    private static DataBaseTestConfig dataBaseConfigTest = new DataBaseTestConfig();
    private static DataBasePrepareServiceTestIT dataBasePrepareServiceTest;
    private static Ticket ticket;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;

    @BeforeAll
    private static void setUp() throws Exception {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseConfigTest;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseConfigTest;
        dataBasePrepareServiceTest = new DataBasePrepareServiceTestIT();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        Ticket ticket = new Ticket();
        when(inputReaderUtil.readSelection()).thenReturn(1);
        dataBasePrepareServiceTest.clearDataBaseEntries();
    }

    @Test
    public void testParkingACar() throws Exception {
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("EN-0T0-ER");
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();

        ticket = ticketDAO.getTicket("EN-0T0-ER");
        assertNotNull(ticket.getInTime());
        assertNull(ticket.getOutTime());
    }

    @Test
    public void testParkingLotExit() throws Exception {
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("EX-00-IT");
        ParkingService parkingService2 = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService2.processIncomingVehicle();
        Thread.sleep(3000);
        parkingService2.processExitingVehicle();

        ticket = ticketDAO.getTicket("EX-00-IT");
        assertEquals(0, ticket.getPrice());
        assertNotNull(ticket.getInTime());
        assertNotNull(ticket.getOutTime());
    }

}