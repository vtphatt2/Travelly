package com.example.travelly.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlightsDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "flights.db";
    private static final int DATABASE_VERSION = 1;

    // City table
    private static final String TABLE_CITY = "City";
    public static final String COL_ID_CITY = "CityId";
    public static final String COL_CITY_NAME = "CityName";
    public static final String COL_CITY_CODE = "CityCode";
    private static final String CREATE_TABLE_CITY = "CREATE TABLE " + TABLE_CITY + " ("
            + COL_ID_CITY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_CITY_NAME + " TEXT NOT NULL, "
            + COL_CITY_CODE + " TEXT NOT NULL);";

    // Flights table
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
    private static final String CREATE_TABLE_FLIGHTS = "CREATE TABLE " + TABLE_FLIGHTS + " ("
            + COL_ID_FLIGHTS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_DEPARTURE_CITY + " TEXT, "
            + COL_ARRIVAL_CITY + " TEXT, "
            + COL_FLIGHT_DAY + " INTEGER, "
            + COL_FLIGHT_MONTH + " INTEGER, "
            + COL_FLIGHT_YEAR + " INTEGER, "
            + COL_DEPARTURE_TIME + " TEXT, "
            + COL_PRICE + " REAL, "
            + COL_FLIGHT_NUMBER + " TEXT NOT NULL);";

    public FlightsDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CITY);
        db.execSQL(CREATE_TABLE_FLIGHTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHTS);
        onCreate(db);
    }

    public void addCity(String cityName, String cityCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CITY_NAME, cityName);
        values.put(COL_CITY_CODE, cityCode);
        db.insert(TABLE_CITY, null, values);
        db.close();
    }

    public void addFamousCities() {
        String[][] cities = {
                {"London", "LDN"},
                {"New York", "NYC"},
                {"Paris", "PAR"},
                {"Tokyo", "TYO"},
                {"Rome", "ROM"},
                {"Dubai", "DXB"},
                {"Singapore", "SIN"},
                {"Barcelona", "BCN"},
                {"Istanbul", "IST"},
                {"Amsterdam", "AMS"},
                {"Hong Kong", "HKG"},
                {"Bangkok", "BKK"},
                {"Sydney", "SYD"},
                {"Los Angeles", "LAX"},
                {"Las Vegas", "LAS"},
                {"Miami", "MIA"},
                {"Toronto", "YYZ"},
                {"San Francisco", "SFO"},
                {"Chicago", "CHI"},
                {"Moscow", "MOW"},
                {"Berlin", "BER"},
                {"Vienna", "VIE"},
                {"Madrid", "MAD"},
                {"Prague", "PRG"},
                {"Dublin", "DUB"},
                {"Copenhagen", "CPH"},
                {"Stockholm", "STO"},
                {"Zurich", "ZRH"},
                {"Venice", "VCE"},
                {"Munich", "MUC"},
                {"Buenos Aires", "BUE"},
                {"Mexico City", "MEX"},
                {"Cape Town", "CPT"},
                {"Rio de Janeiro", "RIO"},
                {"Sao Paulo", "SAO"},
                {"Athens", "ATH"},
                {"Lisbon", "LIS"},
                {"Budapest", "BUD"},
                {"Warsaw", "WAW"},
                {"Seoul", "SEL"},
                {"Osaka", "OSA"},
                {"Kuala Lumpur", "KUL"},
                {"Jakarta", "JKT"},
                {"Manila", "MNL"},
                {"Taipei", "TPE"},
                {"Shanghai", "SHA"},
                {"Beijing", "BJS"},
                {"Mumbai", "BOM"},
                {"Delhi", "DEL"}
        };

        for (String[] city : cities) {
            addCity(city[0], city[1]);
        }
    }

    public List<String> getAllCities() {
        List<String> cityList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_CITY_NAME + ", " + COL_CITY_CODE + " FROM " + TABLE_CITY + " ORDER BY " + COL_CITY_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                String cityName = cursor.getString(cursor.getColumnIndexOrThrow(COL_CITY_NAME));
                String cityCode = cursor.getString(cursor.getColumnIndexOrThrow(COL_CITY_CODE));
                cityList.add(cityName + " (" + cityCode + ")");
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return cityList;
    }

    public void addFlight(String departureCity, String arrivalCity, int flightDay, int flightMonth, int flightYear,
                             String departureTime, double ticketPrice, String flightNumber) {
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
        db.insert(TABLE_FLIGHTS, null, contentValues);
        db.close();
    }

    public void generateFlights() {
        List<String> cities = getAllCities();
        int numberOfCities = cities.size();
        Random random = new Random();
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                String departureCity = cities.get(i);
                String arrivalCity = cities.get(j);

                int denstiy = 0;
                while (denstiy < 9) {
                    // Generate a random date within the next 30 days
                    LocalDate date = currentDate.plusDays(random.nextInt(30));
                    int flightDay = date.getDayOfMonth();
                    int flightMonth = date.getMonthValue();
                    int flightYear = date.getYear();

                    // Generate random departure time, ticket price, and flight number
                    String departureTime = String.format("%02d:%02d %s", random.nextInt(12) + 1, random.nextInt(60), random.nextBoolean() ? "AM" : "PM");
                    double ticketPrice = 100 + (1000 - 100) * random.nextDouble(); // Random ticket price between 100 and 1000
                    String flightNumber = String.format("%s-%03d", departureCity.substring(0, 2) + arrivalCity.substring(0, 2), random.nextInt(900) + 100);

                    // Add the generated flight to the database
                    addFlight(departureCity, arrivalCity, flightDay, flightMonth, flightYear, departureTime, ticketPrice, flightNumber);
                    Log.i("Generate Flights", "Created flight: " + departureCity + " to " + arrivalCity + " on " + flightDay + "/" + flightMonth + "/" + flightYear +
                            " at " + departureTime + ", Price: " + ticketPrice + ", Flight Number: " + flightNumber);

                    denstiy++;
                }
            }
        }
    }

    public List<FlightInfo> getFlightsByDateAndCities(Integer flightDay, Integer flightMonth, Integer flightYear, String departureCity, String arrivalCity) {
        List<FlightInfo> flightList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_FLIGHTS + " WHERE 1=1");

        // Append conditions based on non-null arguments
        if (flightDay != null) {
            query.append(" AND ").append(COL_FLIGHT_DAY).append(" = ").append(flightDay);
        }
        if (flightMonth != null) {
            query.append(" AND ").append(COL_FLIGHT_MONTH).append(" = ").append(flightMonth);
        }
        if (flightYear != null) {
            query.append(" AND ").append(COL_FLIGHT_YEAR).append(" = ").append(flightYear);
        }
        if (departureCity != null) {
            query.append(" AND ").append(COL_DEPARTURE_CITY).append(" = '").append(departureCity).append("'");
        }
        if (arrivalCity != null) {
            query.append(" AND ").append(COL_ARRIVAL_CITY).append(" = '").append(arrivalCity).append("'");
        }

        Cursor cursor = db.rawQuery(query.toString(), null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_FLIGHTS));
                String depCity = cursor.getString(cursor.getColumnIndexOrThrow(COL_DEPARTURE_CITY));
                String arrCity = cursor.getString(cursor.getColumnIndexOrThrow(COL_ARRIVAL_CITY));
                int day = cursor.getInt(cursor.getColumnIndexOrThrow(COL_FLIGHT_DAY));
                int month = cursor.getInt(cursor.getColumnIndexOrThrow(COL_FLIGHT_MONTH));
                int year = cursor.getInt(cursor.getColumnIndexOrThrow(COL_FLIGHT_YEAR));
                String depTime = cursor.getString(cursor.getColumnIndexOrThrow(COL_DEPARTURE_TIME));
                float price = cursor.getFloat(cursor.getColumnIndexOrThrow(COL_PRICE));
                String number = cursor.getString(cursor.getColumnIndexOrThrow(COL_FLIGHT_NUMBER));

                FlightInfo flight = new FlightInfo(id, depCity, arrCity, day, month, year, depTime, price, number);
                flightList.add(flight);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return flightList;
    }

    public void deleteAllTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHTS);
        onCreate(db);
        db.close();
    }
}

