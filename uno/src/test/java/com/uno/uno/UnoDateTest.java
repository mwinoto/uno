package com.uno.uno;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UnoDateTest {

    @Test
    public void testDateCreation_correctFormat() {
        UnoDate date = new UnoDate("01.01.1990");
        assertEquals(1, date.getDay(), "Day is not 1");
        assertEquals(1, date.getMonth(), "Month is not 1");
        assertEquals(1990, date.getYear(), "Year is not 1990");

    }


}