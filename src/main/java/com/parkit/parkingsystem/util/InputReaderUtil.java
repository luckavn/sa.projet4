package com.parkit.parkingsystem.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class InputReaderUtil {

    private static Scanner scan = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger("InputReaderUtil");

    public int readSelection() {
        try {
            int input = Integer.parseInt(scan.nextLine());
            return input;
        }catch(Exception e){
            logger.error("Error while reading user input from Shell", e);
            System.out.println("Error reading input. Please enter valid number for proceeding further");
            return -1;
        }
    }

    public String readVehicleRegistrationNumber() throws Exception {
        try {
            String vehicleRegNumber= scan.nextLine();
            if(vehicleRegNumber == null || vehicleRegNumber.trim().length()==0) {
                throw new IllegalArgumentException("Invalid input provided");
            }
            return vehicleRegNumber;
        }catch(Exception e){
            logger.error("Error while reading user input from Shell", e);
            System.out.println("Error reading input. Please enter a valid string for vehicle registration number");
            throw e;
        }
    }

    public double LessThirtyMinutes (double duration) {
        if (duration <= 0.5) {
            duration = 0;
        } else {
            duration = duration - 0.5;
        }
        return duration;
    }

    public double formatToTwoDecimal(double Price) {
        Price = Math.round(Price * (1.0/0.01)) / (1.0/0.01);
        return Price;
    }

    public double applyFivePourcentOff (double Price) {
        Price = Price * 0.95;
        return Price;
    }
}
