package com.parkit.parkingsystem.integration.util;

import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class InputReaderUtilTest {
    private static InputReaderUtil inputReaderUtil;

@BeforeAll
private static void setUp() throws Exception {
    inputReaderUtil = new InputReaderUtil();
}

    @Test
    public void formatToTwoDecimal() {
        //*Arrange
        double Price = 2.2222222222222;
        double PriceFormatted = 2.22;

        //*Act
        Price = inputReaderUtil.formatToTwoDecimal(Price);

        //*Assert
        try {
            assertEquals((PriceFormatted), Price);
            System.out.print("Test passed: " + PriceFormatted +" (expected) = "+ Price + " (actual)");
        }
        catch (AssertionError ae) {
            throw ae;
        }
    }

    @Test
    public void calculateFivePourcentOff() {
        //*Arrange
        double Price = 10;
        double PriceReduced = 9.5;

        //*Act
        Price = inputReaderUtil.applyFivePourcentOff(Price);
        System.out.println(Price);

        //*Assert
        try {
            assertEquals((PriceReduced), Price);
            System.out.print("Test passed: " + PriceReduced +" (expected) = "+ Price + " (actual)");
        }
        catch (AssertionError ae) {
            throw ae;
        }
    }

    @Test
    public void ReducingTimeByThirtyMinutes() {
        //*Arrange*
        double duration = 15;
        double durationMinusThirty = 14.5;

        //*Act
        duration = inputReaderUtil.LessThirtyMinutes(duration);
        System.out.println(duration);

        //*Assert
        try {
            assertEquals((durationMinusThirty), duration);
            System.out.print("Test passed: " + durationMinusThirty +" (expected) = "+ duration + " (actual)");
        }
        catch (AssertionError ae) {
            throw ae;
        }
    }
}

