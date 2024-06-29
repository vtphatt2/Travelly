package com.example.travelly;

public class BookingItem {
    private String title;
    private int imageResId;

    public BookingItem(String title, int imageResId) {
        this.title = title;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }
}
