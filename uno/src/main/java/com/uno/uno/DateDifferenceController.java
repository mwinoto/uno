package com.uno.uno;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.uno.uno.dynamodb.UnoDateDTO;
import com.uno.uno.dynamodb.UnoDateRequestsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * "Please explicitly calculate the number of days between the two dates - do not use any built-in functions."
 */
@RestController
class DateDifferenceController {

    @Autowired
    AmazonDynamoDB amazonDynamoDB;

    @Autowired
    UnoDateRequestsRepository unoDateRequestsRepository;

    // Regex to test for date format: DD.MM.YYYY
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2,}\\.\\d{2,}\\.\\d{4,}");

    public static final int[] DAYS_IN_EACH_MONTH = {0,
                                                  31, //JAN
                                                  28, //FEB
                                                  31, //MAR
                                                  30, //APR
                                                  31, //MAY
                                                  30, //JUN
                                                  31, //JUL
                                                  31, //AUG
                                                  30, //SEP
                                                  31, //OCT
                                                  30, //NOV
                                                  31 //DEC
    };

    // Maybe I don't need this
    public static final int[] DAYS_IN_EACH_MONTH_LEAP_YEAR = {0,
                                                            31, //JAN
                                                            29, //FEB
                                                            31, //MAR
                                                            30, //APR
                                                            31, //MAY
                                                            30, //JUN
                                                            31, //JUL
                                                            31, //AUG
                                                            30, //SEP
                                                            31, //OCT
                                                            30, //NOV
                                                            31 //DEC
                                                  };
    /**
     * Given 2 dates in the format DD.MM.YYYY where the fromDate is before the toDate,
     * return the number of days between those 2 dates exclusive of the dates themselves.
     * 
     * This is a get request, but we'll be saving to the DB, so I'm not sure that's appropriate.
     * 
     * @param fromDate
     * @param toDate
     * @return
     */
    @GetMapping(value="/date/difference")
    public ResponseEntity<Integer> calculateDayDifference(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

        // No inputs 400. Regex will catch anything wrong with the strings
        if(fromDate == null || toDate == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Matcher startDateMatcher = DATE_PATTERN.matcher(fromDate);
        Matcher endDateMatcher = DATE_PATTERN.matcher(toDate);

        // Format is wrong, 422
        if (!startDateMatcher.matches() || !endDateMatcher.matches()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
        //GYOF4mVJQZnbjchorbIuvWJ3JS7p5z6crKnw/8ZM

        UnoDate fromUnoDate = new UnoDate(fromDate);
        UnoDate toUnoDate = new UnoDate(toDate);

        if(!validateDate(fromUnoDate) || 
           !validateDate(toUnoDate) || 
           !isFromDateBeforeToDate(fromUnoDate, toUnoDate)) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }

        int difference = findDifferenceInDates(fromUnoDate, toUnoDate);
       
        UnoDateDTO unoDateDTO = new UnoDateDTO(fromDate+"-"+toDate, fromDate, toDate, difference);
        unoDateRequestsRepository.save(unoDateDTO);

        // Ok, now we can find the difference
        return ResponseEntity.ok(difference);
    }

    protected int findDifferenceInDates(UnoDate fromDate, UnoDate toDate) {
        int total = 0;

        while(!fromDate.equals(toDate)) {
            total++;
            incrementUnoDate(fromDate);
        }
        return --total; // We want the count exclusive of the specified dates
    }

    private void incrementUnoDate(UnoDate date) {
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();
        
        day++;

        int[] daysInMonth;
        if(isLeapYear(year)) {
            daysInMonth = DAYS_IN_EACH_MONTH_LEAP_YEAR;
        } else {
            daysInMonth = DAYS_IN_EACH_MONTH;
        }

        if(day <= daysInMonth[month]) {
            date.setDay(day);
            return;
        }

        day = 1;
        month++;

        if(month <= 12) {
            date.setDay(day);
            date.setMonth(month);
            return;
        }

        month = 1;
        year++;

        date.setDay(day);
        date.setMonth(month);
        date.setYear(year);

    }

    /**
     * Checks if the from date is before the to date
     * 
     * If the date format is DD.MM.YYYY
     * Reversing it and creating a number will give a number that can just be compared.
     * Eg 01.01.2000 -> 20000101
     * 
     */
    protected boolean isFromDateBeforeToDate(UnoDate fromDate, UnoDate toDate) {
        int fromInt = fromDate.getYear()*10000 + fromDate.getMonth()*100 + fromDate.getDay();
        int toInt = toDate.getYear()*10000 + toDate.getMonth()*100 + toDate.getDay();
        return fromInt <= toInt;
    }


    /**
     * Validate that the date is correct.
     * Year must start from 1900
     * 
     * @param unoDate
     * @return
     */
    private boolean validateDate(UnoDate unoDate) {
        if(unoDate.getYear() < 1900) {
            return false;
        }

        if(unoDate.getMonth() < 1 || 12 < unoDate.getMonth() ) {
            return false;
        }

        if(unoDate.getDay() < 1) {
            return false;
        }

        if(isLeapYear(unoDate)) {
            return unoDate.getDay() <= DAYS_IN_EACH_MONTH_LEAP_YEAR[unoDate.getMonth()];
        } else {
            return unoDate.getDay() <= DAYS_IN_EACH_MONTH[unoDate.getMonth()];
        }
    }
    
    /**
     * Hello uni assignment
     * 
     * 1. If the year is evenly divisible by 4, go to step 2. Otherwise, go to step 5.
     * 2. If the year is evenly divisible by 100, go to step 3. Otherwise, go to step 4.
     * 3. If the year is evenly divisible by 400, go to step 4. Otherwise, go to step 5.
     * 4. The year is a leap year (it has 366 days).
     * 5. The year is not a leap year (it has 365 days).
     *
     * Don't want to put this in UnoDate. But it should get some unit tests. 
     */
    // Visible for testing.
    protected boolean isLeapYear(UnoDate unoDate) { 
        return isLeapYear(unoDate.getYear());
    }

    protected boolean isLeapYear(int year) {
        if(year%4 == 0) {
            if(year%100 == 0) {
                if(year%400 == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

}