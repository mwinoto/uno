package com.uno.uno;

public class UnoDate implements Comparable<UnoDate> {
    
    private int day;
    private int month;
    private int year;

    public UnoDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public UnoDate(String date) {
        String[] parts = date.split("\\.");
        day = Integer.parseInt(parts[0]);
        month = Integer.parseInt(parts[1]);
        year = Integer.parseInt(parts[2]);
    }

    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }


	public void setDay(int day) {
        this.day = day;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int compareTo(UnoDate unoDate) {
        int sortableValue = toSortableValue();
        int unoDateSortableValue = unoDate.toSortableValue();
        return sortableValue - unoDateSortableValue;
    }

    private int toSortableValue() {
        return this.year*10000 + this.month*100 + this.day;
    }

    /**
     * 1. If the year is evenly divisible by 4, go to step 2. Otherwise, go to step 5.
     * 2. If the year is evenly divisible by 100, go to step 3. Otherwise, go to step 4.
     * 3. If the year is evenly divisible by 400, go to step 4. Otherwise, go to step 5.
     * 4. The year is a leap year (it has 366 days).
     * 5. The year is not a leap year (it has 365 days).
     *
     * Don't want to put this in UnoDate. But it should get some unit tests. 
     */
    protected boolean isLeapYear() {
        if(this.year%4 == 0) {
            if(this.year%100 == 0) {
                if(this.year%400 == 0) {
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UnoDate)) {
            return false;
        }
        UnoDate unoDate = (UnoDate) o;
        return day == unoDate.day && month == unoDate.month && year == unoDate.year;
    }

}
