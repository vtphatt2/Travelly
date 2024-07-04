package com.example.travelly.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FlightsDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "flights.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_FLIGHTS = "Flights";
    public static final String COL_ID_FLIGHTS = "FlightId";
    public static final String COL_DEPARTURE_CITY = "DepartureCity";
    public static final String COL_ARRIVAL_CITY = "ArrivalCity";
    public static final String COL_FLIGHT_DAY = "FlightDay";
    public static final String COL_FLIGHT_MONTH = "FlightMonth";
    public static final String COL_FLIGHT_YEAR = "FlightYear";
    public static final String COL_DEPARTURE_TIME = "DepartureTime";
    public static final String COL_PRICE = "TicketPrice";
    public static final String COL_FLIGHT_NUMBER = "FlightNumber";


    public void clearAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FLIGHTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHTS);
        onCreate(db);
    }

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

    public Cursor getFlightsByDateAndCities(Integer flightDay, Integer flightMonth, Integer flightYear, String departureCity, String arrivalCity) {
        SQLiteDatabase db = this.getWritableDatabase();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM " + TABLE_FLIGHTS + " WHERE 1=1");
        List<String> selectionArgsList = new ArrayList<>();

        if (flightDay != null) {
            queryBuilder.append(" AND ").append(COL_FLIGHT_DAY).append(" = ?");
            selectionArgsList.add(String.valueOf(flightDay));
        }
        if (flightMonth != null) {
            queryBuilder.append(" AND ").append(COL_FLIGHT_MONTH).append(" = ?");
            selectionArgsList.add(String.valueOf(flightMonth));
        }
        if (flightYear != null) {
            queryBuilder.append(" AND ").append(COL_FLIGHT_YEAR).append(" = ?");
            selectionArgsList.add(String.valueOf(flightYear));
        }
        if (departureCity != null) {
            queryBuilder.append(" AND ").append(COL_DEPARTURE_CITY).append(" = ?");
            selectionArgsList.add(departureCity);
        }
        if (arrivalCity != null) {
            queryBuilder.append(" AND ").append(COL_ARRIVAL_CITY).append(" = ?");
            selectionArgsList.add(arrivalCity);
        }

        String[] selectionArgs = selectionArgsList.toArray(new String[0]);
        return db.rawQuery(queryBuilder.toString(), selectionArgs);
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

    public void insertSampleData() {
        addFlight("Amsterdam (AMS)", "Amsterdam (AMS)", 4, 7, 2024, "9:00 AM", 150.0, "AA-101");
        addFlight("Amsterdam (AMS)", "Amsterdam (AMS)", 4, 7, 2024, "10:00 AM", 150.0, "AA-102");
        addFlight("Amsterdam (AMS)", "Amsterdam (AMS)", 4, 7, 2024, "12:00 AM", 150.0, "AA-103");

        addFlight("Amsterdam (AMS)", "Athens (ATH)", 2, 7, 2024, "9:00 AM", 150.0, "AA-101");
        addFlight("Atlanta (ATL)", "Auckland (AKL)", 2, 7, 2024, "10:00 AM", 400.0, "AT-202");
        addFlight("Bangkok (BKK)", "Barcelona (BCN)", 2, 7, 2024, "11:00 AM", 350.0, "BB-303");
        addFlight("Beijing (PEK)", "Berlin (BER)", 2, 7, 2024, "12:00 PM", 300.0, "BP-404");
        addFlight("Boston (BOS)", "Buenos Aires (EZE)", 2, 7, 2024, "1:00 PM", 450.0, "BB-505");
        addFlight("Budapest (BUD)", "Cairo (CAI)", 2, 7, 2024, "2:00 PM", 250.0, "BC-606");
        addFlight("Calgary (YYC)", "Chicago (ORD)", 2, 7, 2024, "3:00 PM", 200.0, "CC-707");
        addFlight("Dallas (DFW)", "Delhi (DEL)", 3, 7, 2024, "9:00 AM", 500.0, "DD-808");
        addFlight("Dubai (DXB)", "Frankfurt (FRA)", 3, 7, 2024, "10:00 AM", 350.0, "DF-909");
        addFlight("Hong Kong (HKG)", "Istanbul (IST)", 3, 7, 2024, "11:00 AM", 450.0, "HI-010");
        addFlight("Jakarta (CGK)", "Johannesburg (JNB)", 3, 7, 2024, "12:00 PM", 550.0, "JJ-111");
        addFlight("Kuala Lumpur (KUL)", "Las Vegas (LAS)", 3, 7, 2024, "1:00 PM", 600.0, "KL-212");
        addFlight("London (LDN)", "Los Angeles (LAX)", 3, 7, 2024, "2:00 PM", 700.0, "LL-313");
        addFlight("Madrid (MAD)", "Miami (MIA)", 3, 7, 2024, "3:00 PM", 350.0, "MM-414");
        addFlight("Milan (MXP)", "Mexico City (MEX)", 4, 7, 2024, "9:00 AM", 400.0, "MM-515");
        addFlight("Moscow (DME)", "Mumbai (BOM)", 4, 7, 2024, "10:00 AM", 450.0, "MM-616");
        addFlight("New York (NYC)", "Paris (PAR)", 4, 7, 2024, "11:00 AM", 500.0, "NP-717");
        addFlight("Prague (PRG)", "Rio de Janeiro (GIG)", 4, 7, 2024, "12:00 PM", 550.0, "PR-818");
        addFlight("Rome (FCO)", "Saint Petersburg (LED)", 4, 7, 2024, "1:00 PM", 300.0, "RS-919");
        addFlight("San Francisco (SFO)", "São Paulo (GRU)", 4, 7, 2024, "2:00 PM", 600.0, "SS-020");
        addFlight("Seattle (SEA)", "Seoul (ICN)", 4, 7, 2024, "3:00 PM", 700.0, "SS-121");
        addFlight("Shanghai (PVG)", "Singapore (SIN)", 5, 7, 2024, "9:00 AM", 800.0, "SS-222");
        addFlight("Toronto (YYZ)", "Tokyo (TYO)", 5, 7, 2024, "10:00 AM", 750.0, "TT-323");
        addFlight("Vancouver (YVR)", "Vienna (VIE)", 5, 7, 2024, "11:00 AM", 600.0, "VV-424");
        addFlight("Warsaw (WAW)", "Washington D.C. (DCA)", 5, 7, 2024, "12:00 PM", 500.0, "WW-525");
        addFlight("Amsterdam (AMS)", "Athens (ATH)", 5, 7, 2024, "1:00 PM", 150.0, "AA-626");
        addFlight("Atlanta (ATL)", "Auckland (AKL)", 5, 7, 2024, "2:00 PM", 400.0, "AT-727");
        addFlight("Bangkok (BKK)", "Barcelona (BCN)", 5, 7, 2024, "3:00 PM", 350.0, "BB-828");
        addFlight("Beijing (PEK)", "Berlin (BER)", 6, 7, 2024, "9:00 AM", 300.0, "BP-929");
        addFlight("Boston (BOS)", "Buenos Aires (EZE)", 6, 7, 2024, "10:00 AM", 450.0, "BB-030");
        addFlight("Budapest (BUD)", "Cairo (CAI)", 6, 7, 2024, "11:00 AM", 250.0, "BC-131");
        addFlight("Calgary (YYC)", "Chicago (ORD)", 6, 7, 2024, "12:00 PM", 200.0, "CC-232");
        addFlight("Dallas (DFW)", "Delhi (DEL)", 6, 7, 2024, "1:00 PM", 500.0, "DD-333");
        addFlight("Dubai (DXB)", "Frankfurt (FRA)", 6, 7, 2024, "2:00 PM", 350.0, "DF-434");
        addFlight("Hong Kong (HKG)", "Istanbul (IST)", 6, 7, 2024, "3:00 PM", 450.0, "HI-535");
        addFlight("Jakarta (CGK)", "Johannesburg (JNB)", 7, 7, 2024, "9:00 AM", 550.0, "JJ-636");
        addFlight("Kuala Lumpur (KUL)", "Las Vegas (LAS)", 7, 7, 2024, "10:00 AM", 600.0, "KL-737");
        addFlight("London (LDN)", "Los Angeles (LAX)", 7, 7, 2024, "11:00 AM", 700.0, "LL-838");
        addFlight("Madrid (MAD)", "Miami (MIA)", 7, 7, 2024, "12:00 PM", 350.0, "MM-939");
        addFlight("Milan (MXP)", "Mexico City (MEX)", 7, 7, 2024, "1:00 PM", 400.0, "MM-040");
        addFlight("Moscow (DME)", "Mumbai (BOM)", 7, 7, 2024, "2:00 PM", 450.0, "MM-141");
        addFlight("New York (NYC)", "Paris (PAR)", 7, 7, 2024, "3:00 PM", 500.0, "NP-242");
        addFlight("Prague (PRG)", "Rio de Janeiro (GIG)", 8, 7, 2024, "9:00 AM", 550.0, "PR-343");
        addFlight("Rome (FCO)", "Saint Petersburg (LED)", 8, 7, 2024, "10:00 AM", 300.0, "RS-444");
        addFlight("San Francisco (SFO)", "São Paulo (GRU)", 8, 7, 2024, "11:00 AM", 600.0, "SS-545");
        addFlight("Seattle (SEA)", "Seoul (ICN)", 8, 7, 2024, "12:00 PM", 700.0, "SS-646");
        addFlight("Shanghai (PVG)", "Singapore (SIN)", 8, 7, 2024, "1:00 PM", 800.0, "SS-747");
        addFlight("Toronto (YYZ)", "Tokyo (TYO)", 8, 7, 2024, "2:00 PM", 750.0, "TT-848");
        addFlight("Vancouver (YVR)", "Vienna (VIE)", 8, 7, 2024, "3:00 PM", 600.0, "VV-949");
        addFlight("Warsaw (WAW)", "Washington D.C. (DCA)", 9, 7, 2024, "9:00 AM", 500.0, "WW-050");
        addFlight("Amsterdam (AMS)", "Athens (ATH)", 9, 7, 2024, "10:00 AM", 150.0, "AA-151");
        addFlight("Atlanta (ATL)", "Auckland (AKL)", 9, 7, 2024, "11:00 AM", 400.0, "AT-252");
        addFlight("Bangkok (BKK)", "Barcelona (BCN)", 9, 7, 2024, "12:00 PM", 350.0, "BB-353");
        addFlight("Beijing (PEK)", "Berlin (BER)", 9, 7, 2024, "1:00 PM", 300.0, "BP-454");
        addFlight("Boston (BOS)", "Buenos Aires (EZE)", 9, 7, 2024, "2:00 PM", 450.0, "BB-555");
        addFlight("Budapest (BUD)", "Cairo (CAI)", 9, 7, 2024, "3:00 PM", 250.0, "BC-656");
        addFlight("Calgary (YYC)", "Chicago (ORD)", 10, 7, 2024, "9:00 AM", 200.0, "CC-757");
        addFlight("Dallas (DFW)", "Delhi (DEL)", 10, 7, 2024, "10:00 AM", 500.0, "DD-858");
        addFlight("Dubai (DXB)", "Frankfurt (FRA)", 10, 7, 2024, "11:00 AM", 350.0, "DF-959");
        addFlight("Hong Kong (HKG)", "Istanbul (IST)", 10, 7, 2024, "12:00 PM", 450.0, "HI-060");
        addFlight("Jakarta (CGK)", "Johannesburg (JNB)", 10, 7, 2024, "1:00 PM", 550.0, "JJ-161");
        addFlight("Kuala Lumpur (KUL)", "Las Vegas (LAS)", 10, 7, 2024, "2:00 PM", 600.0, "KL-262");
        addFlight("London (LDN)", "Los Angeles (LAX)", 10, 7, 2024, "3:00 PM", 700.0, "LL-363");
        addFlight("Madrid (MAD)", "Miami (MIA)", 11, 7, 2024, "9:00 AM", 350.0, "MM-464");
        addFlight("Milan (MXP)", "Mexico City (MEX)", 11, 7, 2024, "10:00 AM", 400.0, "MM-565");
        addFlight("Moscow (DME)", "Mumbai (BOM)", 11, 7, 2024, "11:00 AM", 450.0, "MM-666");
        addFlight("New York (NYC)", "Paris (PAR)", 11, 7, 2024, "12:00 PM", 500.0, "NP-767");
        addFlight("Prague (PRG)", "Rio de Janeiro (GIG)", 11, 7, 2024, "1:00 PM", 550.0, "PR-868");
        addFlight("Rome (FCO)", "Saint Petersburg (LED)", 11, 7, 2024, "2:00 PM", 300.0, "RS-969");
        addFlight("San Francisco (SFO)", "São Paulo (GRU)", 11, 7, 2024, "3:00 PM", 600.0, "SS-070");
        addFlight("Seattle (SEA)", "Seoul (ICN)", 12, 7, 2024, "9:00 AM", 700.0, "SS-171");
        addFlight("Shanghai (PVG)", "Singapore (SIN)", 12, 7, 2024, "10:00 AM", 800.0, "SS-272");
        addFlight("Toronto (YYZ)", "Tokyo (TYO)", 12, 7, 2024, "11:00 AM", 750.0, "TT-373");
        addFlight("Vancouver (YVR)", "Vienna (VIE)", 12, 7, 2024, "12:00 PM", 600.0, "VV-474");
        addFlight("Warsaw (WAW)", "Washington D.C. (DCA)", 12, 7, 2024, "1:00 PM", 500.0, "WW-575");
        addFlight("Amsterdam (AMS)", "Athens (ATH)", 12, 7, 2024, "2:00 PM", 150.0, "AA-676");
        addFlight("Atlanta (ATL)", "Auckland (AKL)", 12, 7, 2024, "3:00 PM", 400.0, "AT-777");
        addFlight("Bangkok (BKK)", "Barcelona (BCN)", 13, 7, 2024, "9:00 AM", 350.0, "BB-878");
        addFlight("Beijing (PEK)", "Berlin (BER)", 13, 7, 2024, "10:00 AM", 300.0, "BP-979");
        addFlight("Boston (BOS)", "Buenos Aires (EZE)", 13, 7, 2024, "11:00 AM", 450.0, "BB-080");
        addFlight("Budapest (BUD)", "Cairo (CAI)", 13, 7, 2024, "12:00 PM", 250.0, "BC-181");
        addFlight("Calgary (YYC)", "Chicago (ORD)", 13, 7, 2024, "1:00 PM", 200.0, "CC-282");
        addFlight("Dallas (DFW)", "Delhi (DEL)", 13, 7, 2024, "2:00 PM", 500.0, "DD-383");
        addFlight("Dubai (DXB)", "Frankfurt (FRA)", 13, 7, 2024, "3:00 PM", 350.0, "DF-484");
        addFlight("Hong Kong (HKG)", "Istanbul (IST)", 14, 7, 2024, "9:00 AM", 450.0, "HI-585");
        addFlight("Jakarta (CGK)", "Johannesburg (JNB)", 14, 7, 2024, "10:00 AM", 550.0, "JJ-686");
        addFlight("Kuala Lumpur (KUL)", "Las Vegas (LAS)", 14, 7, 2024, "11:00 AM", 600.0, "KL-787");
        addFlight("London (LDN)", "Los Angeles (LAX)", 14, 7, 2024, "12:00 PM", 700.0, "LL-888");
        addFlight("Madrid (MAD)", "Miami (MIA)", 14, 7, 2024, "1:00 PM", 350.0, "MM-989");
        addFlight("Milan (MXP)", "Mexico City (MEX)", 14, 7, 2024, "2:00 PM", 400.0, "MM-090");
        addFlight("Moscow (DME)", "Mumbai (BOM)", 14, 7, 2024, "3:00 PM", 450.0, "MM-191");
        addFlight("New York (NYC)", "Paris (PAR)", 15, 7, 2024, "9:00 AM", 500.0, "NP-292");
        addFlight("Prague (PRG)", "Rio de Janeiro (GIG)", 15, 7, 2024, "10:00 AM", 550.0, "PR-393");
        addFlight("Rome (FCO)", "Saint Petersburg (LED)", 15, 7, 2024, "11:00 AM", 300.0, "RS-494");
        addFlight("San Francisco (SFO)", "São Paulo (GRU)", 15, 7, 2024, "12:00 PM", 600.0, "SS-595");
        addFlight("Seattle (SEA)", "Seoul (ICN)", 15, 7, 2024, "1:00 PM", 700.0, "SS-696");
        addFlight("Shanghai (PVG)", "Singapore (SIN)", 15, 7, 2024, "2:00 PM", 800.0, "SS-797");
        addFlight("Toronto (YYZ)", "Tokyo (TYO)", 15, 7, 2024, "3:00 PM", 750.0, "TT-898");
    }
}

