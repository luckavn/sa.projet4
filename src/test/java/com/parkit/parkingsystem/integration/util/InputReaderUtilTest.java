package com.parkit.parkingsystem.integration.util;

import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class InputReaderUtilTest {
    private InputReaderUtil inputReaderUtil;

    @BeforeEach
    private void setUpForTest() throws Exception {
        inputReaderUtil = new InputReaderUtil();
    }

    @Test
    public void formatToTwoDecimal() {
        double Price = 2.2222222222222;
        double PriceFormatted = 2.22;

        Price = inputReaderUtil.formatToTwoDecimal(Price);

        try {
            assertEquals((PriceFormatted), Price);
            System.out.print("Test passed: " + PriceFormatted + " (expected) = " + Price + " (actual)");
        } catch (AssertionError ae) {
            throw ae;
        }
    }

    @Test
    public void applyFivePourcentOff() {
        double Price = 10;
        double PriceReduced = 9.5;

        Price = inputReaderUtil.applyFivePourcentOff(Price);

        try {
            assertEquals((PriceReduced), Price);
            System.out.print("Test passed: " + PriceReduced + " (expected) = " + Price + " (actual)");
        } catch (AssertionError ae) {
            throw ae;
        }
    }

    @Test
    public void LessThirtyMinutes() {
        double duration = 15;
        double durationMinusThirty = 14.5;

        duration = inputReaderUtil.LessThirtyMinutes(duration);

        try {
            assertEquals((durationMinusThirty), duration);
            System.out.print("Test passed: " + durationMinusThirty + " (expected) = " + duration + " (actual)");
        } catch (AssertionError ae) {
            throw ae;
        }
    }

}

