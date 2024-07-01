package com.example.travelly;

public class Date {
    private String dayOfWeek;
    private int day;

    Date(String dayOfWeek, int day) {
        this.dayOfWeek = dayOfWeek;
        this.day = day;
    }

    String getDayOfWeek() {
        return dayOfWeek;
    }

    String getDay() {
        return Integer.toString(day);
    }
}
