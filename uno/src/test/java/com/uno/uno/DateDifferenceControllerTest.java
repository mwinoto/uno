package com.uno.uno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class DateDifferenceControllerTest {

    DateDifferenceController dateDifferenceController;

    @BeforeEach
    public void setup() {
        dateDifferenceController = new DateDifferenceController();
    }

    @Test
    public void testLeapYear_1900() {
        assertFalse(dateDifferenceController.isLeapYear(1900), "1900 is not a leap year");
    }

    @Test
    public void testLeapYear_1901() {
        assertFalse(dateDifferenceController.isLeapYear(1901), "1901 is not a leap year");
    }

    @Test
    public void testLeapYear_2000() {
        assertTrue(dateDifferenceController.isLeapYear(2000), "2000 is a leap year");
    }

    @Test
    public void testLeapYear_1996() {
        assertTrue(dateDifferenceController.isLeapYear(1996), "1996 is a leap year");
    }

    @Test
    public void testCorrectInput_oneYear() {
        ResponseEntity<Integer> response = dateDifferenceController.calculateDayDifference("01.01.1900", "01.01.1901");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Controller thinks from date can be processed");
        assertEquals(364, response.getBody(), "Expected 364 to be returned");
    }

    @Test
    public void testCorrectInput_oneDay() {
        ResponseEntity<Integer> response = dateDifferenceController.calculateDayDifference("01.01.1900", "03.01.1900");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Controller thinks from date can be processed");
        assertEquals(1, response.getBody(), "Expected 1 to be returned");
    }


    @Test
    public void testCorrectInput_zeroDays() {
        ResponseEntity<Integer> response = dateDifferenceController.calculateDayDifference("01.01.1900", "02.01.1900");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Controller thinks from date can be processed");
        assertEquals(0, response.getBody(), "Expected 0 to be returned");
    }


    @Test
    public void testCorrectInput_example1() {
        ResponseEntity<Integer> response = dateDifferenceController.calculateDayDifference("01.01.2020", "03.01.2020");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Controller thinks from date can be processed");
        assertEquals(1, response.getBody(), "Expected 1 to be returned");
    }


    @Test
    public void testCorrectInput_example2() {
        ResponseEntity<Integer> response = dateDifferenceController.calculateDayDifference("01.01.2020", "01.01.2030");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Controller thinks from date can be processed");
        assertEquals(3652, response.getBody(), "Expected 3652 to be returned");
    }

    @Test
    public void testIncorrectInput_start() {
        ResponseEntity<Integer> response = dateDifferenceController.calculateDayDifference("0.1.1900", "01.01.1901");
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode(), "Controller thinks from date can be processed");
    }

    @Test
    public void testIncorrectInput_end() {
        ResponseEntity<Integer> response = dateDifferenceController.calculateDayDifference("01.01.1900", "1.1.1901");
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode(), "Controller thinks end date can be processed");
    }

    @Test
    public void testIncorrectInput_null() {
        ResponseEntity<Integer> response = dateDifferenceController.calculateDayDifference(null, null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response was not bad request");
    }

    @Test
    public void testIncorrectInput_notARealDay() {
        ResponseEntity<Integer> response = dateDifferenceController.calculateDayDifference("99.01.1900", "01.01.1901");
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode(), "Controller thinks 99th of Jan exists");
    }

    @Test
    public void testIncorrectInput_notARealMonth() {
        ResponseEntity<Integer> response = dateDifferenceController.calculateDayDifference("01.18.1900", "01.01.1901");
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode(), "Controller thinks 01/18 exists");
    }

    @Test
    public void testIncorrectInput_notALeapYear() {
        ResponseEntity<Integer> response = dateDifferenceController.calculateDayDifference("01.01.1900", "29.02.1991");
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode(), "Controller thinks 1991 is a leap year");
    }

    @Test
    public void testIncorrectInput_zeroDay() {
        ResponseEntity<Integer> response = dateDifferenceController.calculateDayDifference("00.01.1900", "29.02.1991");
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode(), "Controller thinks there is a day 0");
    }

    @Test
    public void testFromIsBeforeTo_daysApart() {
        assertTrue(
            dateDifferenceController.isFromDateBeforeToDate(new UnoDate("01.01.1980"), new UnoDate("04.01.1980")),
            "4th of Jan is after 1st of Jan"
        );
    }

    @Test
    public void testFromIsBeforeTo_monthsApart() {
        assertTrue(
            dateDifferenceController.isFromDateBeforeToDate(new UnoDate("01.01.1980"), new UnoDate("01.03.1980")),
            "March is after Jan"
        );
    }

    @Test
    public void testFromIsNotBeforeTo_daysApart() {
        assertFalse(
            dateDifferenceController.isFromDateBeforeToDate(new UnoDate("02.01.1980"), new UnoDate("01.01.1980")),
            "2/1 is after 1/1"
        );
    }

    @Test
    public void testFromIsNotBeforeTo_monthsApart() {
        assertFalse(
            dateDifferenceController.isFromDateBeforeToDate(new UnoDate("02.04.1980"), new UnoDate("02.03.1980")),
            "2/4 is after 2/3"
        );
    }

}
