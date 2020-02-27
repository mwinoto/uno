package com.uno.uno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UnoDateTest {

    @Test
    public void testDateCreation_correctFormat() {
        UnoDate date = new UnoDate("01.01.1990");
        assertEquals(1, date.getDay(), "Day is not 1");
        assertEquals(1, date.getMonth(), "Month is not 1");
        assertEquals(1990, date.getYear(), "Year is not 1990");
    }

    @Test
    public void testLeapYear_1900() {
        UnoDate date = new UnoDate("01.01.1990");
        assertFalse(date.isLeapYear(), "1900 is not a leap year");
    }

    @Test
    public void testLeapYear_1901() {
        UnoDate date = new UnoDate("01.01.1991");
        assertFalse(date.isLeapYear(), "1901 is not a leap year");
    }

    @Test
    public void testLeapYear_2000() {
        UnoDate date = new UnoDate("01.01.2000");
        assertTrue(date.isLeapYear(), "2000 is a leap year");
    }

    @Test
    public void testLeapYear_1996() {
        UnoDate date = new UnoDate("01.01.1996");
        assertTrue(date.isLeapYear(), "1996 is a leap year");
    }


    @Test
    public void testFromIsBeforeTo_equals() {
        assertTrue(
                new UnoDate("01.01.1980").compareTo(new UnoDate("01.01.1980")) == 0,
                "4th of Jan is after 1st of Jan");
    }

    @Test
    public void testFromIsBeforeTo_daysApart() {
        assertTrue(
                new UnoDate("01.01.1980").compareTo(new UnoDate("04.01.1980")) < 0,
                "4th of Jan is after 1st of Jan");
    }

    @Test
    public void testFromIsBeforeTo_monthsApart() {
        assertTrue(
                new UnoDate("01.01.1980").compareTo(new UnoDate("01.03.1980")) < 0,
                "March is after Jan");
    }

    @Test
    public void testFromIsNotBeforeTo_daysApart() {
        assertTrue(
                new UnoDate("02.01.1980").compareTo(new UnoDate("01.01.1980")) > 0,
                "2/1 is after 1/1");
    }

    @Test
    public void testFromIsNotBeforeTo_monthsApart() {
        assertTrue(
                new UnoDate("02.04.1980").compareTo(new UnoDate("02.03.1980")) > 0,
                "2/4 is after 2/3");
    }

}