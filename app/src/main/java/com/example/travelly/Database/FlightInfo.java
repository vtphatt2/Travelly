package com.example.travelly.Database;

import com.example.travelly.Date;

public class FlightInfo {
    private int id;
    private String departureCity;
    private String arrivalCity;
    private int day;
    private int month;
    private int year;
    private String departureTime;
    private float price;
    private String number;

    public FlightInfo(int id, String departureCity, String arrivalCity, int day, int month, int year, String departureTime, float price, String number) {
        this.id = id;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.day = day;
        this.month = month;
        this.year = year;
        this.departureTime = departureTime;
        this.price = price;
        this.number = number;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public String getDate() {
        return String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public float getPrice() {
        return price;
    }

    public String getNumber() {
        return number;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
