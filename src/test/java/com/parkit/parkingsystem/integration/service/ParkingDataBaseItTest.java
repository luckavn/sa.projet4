package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseConfigTest;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseItTest {

    private static DataBaseConfigTest dataBaseConfigTest = new DataBaseConfigTest();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareServiceTest dataBasePrepareServiceTest;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseConfigTest;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseConfigTest;
        dataBasePrepareServiceTest = new DataBasePrepareServiceTest();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareServiceTest.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown(){
    }

//    @Test
//    public void testParkingACarAndExit() {
//        //Testing entering a car and exit itself. Saves intime, outime and fees to pay
//        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
//        parkingService.processIncomingVehicle();
//        Ticket ticket = new Ticket();
//        ticket.setInTime(new Date(System.currentTimeMillis() - (60*60*1000)));
//        parkingService.processExitingVehicle();
//    }

    @Test
    public void testParkingACar(){
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
    }

//    @Test
//    public void testParkingLotExit(){
//        testParkingACar();
//        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
//        parkingService.processExitingVehicle();
//        //TODO: check that the fare generated and out time are populated correctly in the database
//    }
}
