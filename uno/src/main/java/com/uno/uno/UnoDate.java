package com.uno.uno;

public class UnoDate {
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
