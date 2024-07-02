package com.example.travelly.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FlightsDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "flights.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_FLIGHTS = "Flights";
    private static final String COL_ID_FLIGHTS = "FlightId";
    private static final String COL_DEPARTURE_CITY = "DepartureCity";
    private static final String COL_ARRIVAL_CITY = "ArrivalCity";
    private static final String COL_FLIGHT_DAY = "FlightDay";
    private static final String COL_FLIGHT_MONTH = "FlightMonth";
    private static final String COL_FLIGHT_YEAR = "FlightYear";
    private static final String COL_DEPARTURE_TIME = "DepartureTime";
    private static final String COL_PRICE = "TicketPrice";
    private static final String COL_FLIGHT_NUMBER = "FlightNumber";

    public FlightsDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FLIGHTS_TABLE = "CREATE TABLE " + TABLE_FLIGHTS + "("
                + COL_ID_FLIGHTS + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_DEPARTURE_CITY + " TEXT,"
                + COL_ARRIVAL_CITY + " TEXT,"
                + COL_FLIGHT_DAY + " INTEGER,"
                + COL_FLIGHT_MONTH + " INTEGER,"
                + COL_FLIGHT_YEAR + " INTEGER,"
                + COL_DEPARTURE_TIME + " TEXT,"
                + COL_PRICE + " REAL,"
                + COL_FLIGHT_NUMBER + " TEXT"
                + ")";
        db.execSQL(CREATE_FLIGHTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHTS);
        onCreate(db);
    }

    public boolean addFlight(String departureCity, String arrivalCity, int flightDay, int flightMonth, int flightYear, String departureTime, double ticketPrice, String flightNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DEPARTURE_CITY, departureCity);
        contentValues.put(COL_ARRIVAL_CITY, arrivalCity);
        contentValues.put(COL_FLIGHT_DAY, flightDay);
        contentValues.put(COL_FLIGHT_MONTH, flightMonth);
        contentValues.put(COL_FLIGHT_YEAR, flightYear);
        contentValues.put(COL_DEPARTURE_TIME, departureTime);
        contentValues.put(COL_PRICE, ticketPrice);
        contentValues.put(COL_FLIGHT_NUMBER, flightNumber);
        long result = db.insert(TABLE_FLIGHTS, null, contentValues);
        return result != -1;
    }

    public Cursor getAllFlights() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FLIGHTS, null);
    }

    public boolean updateFlight(int flightId, String departureCity, String arrivalCity, int flightDay, int flightMonth, int flightYear, String departureTime, double ticketPrice, String flightNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DEPARTURE_CITY, departureCity);
        contentValues.put(COL_ARRIVAL_CITY, arrivalCity);
        contentValues.put(COL_FLIGHT_DAY, flightDay);
        contentValues.put(COL_FLIGHT_MONTH, flightMonth);
        contentValues.put(COL_FLIGHT_YEAR, flightYear);
        contentValues.put(COL_DEPARTURE_TIME, departureTime);
        contentValues.put(COL_PRICE, ticketPrice);
        contentValues.put(COL_FLIGHT_NUMBER, flightNumber);

        int result = db.update(TABLE_FLIGHTS, contentValues, COL_ID_FLIGHTS + " = ?", new String[]{String.valueOf(flightId)});
        return result > 0;
    }


    public boolean deleteFlight(int flightId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_FLIGHTS, COL_ID_FLIGHTS + " = ?", new String[]{String.valueOf(flightId)});
        return result > 0;
    }
}
