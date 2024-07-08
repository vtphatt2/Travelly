package com.example.travelly;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelly.Database.FlightsDatabaseHandler;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.List;

public class TransportBooking extends AppCompatActivity {
    ImageButton btnBack, btnSwap;
    AutoCompleteTextView actvFromCity, actvToCity;
    TextView tvDeparture, tvReturn, tvEconomy, tvBusiness;
    ImageView ivPlane, ivBoat, ivTrain, ivBus;
    Button btnSearch;
    String departureCity = "", arrivalCity = "";
    FlightsDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transport_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.buttonBack);
        actvFromCity = findViewById(R.id.autoCompleteTextViewFrom);
        actvToCity = findViewById(R.id.autoCompleteTextViewTo);
        btnSwap = findViewById(R.id.buttonSwap);
        tvDeparture = findViewById(R.id.textViewDepartureDate);
        tvReturn = findViewById(R.id.textViewReturnDate);
        tvEconomy = findViewById(R.id.textViewEconomy);
        tvBusiness = findViewById(R.id.textViewBusiness);
        ivPlane = findViewById(R.id.imageViewPlane);
        ivBoat = findViewById(R.id.imageViewBoat);
        ivTrain = findViewById(R.id.imageViewTrain);
        ivBus = findViewById(R.id.imageViewBus);
        db = new FlightsDatabaseHandler(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<String> cityList = db.getAllCities();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cityList);
        actvFromCity.setAdapter(adapter);
        actvToCity.setAdapter(adapter);

        btnSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromCitySelected = actvFromCity.getText().toString();
                String toCitySelected = actvToCity.getText().toString();
                actvFromCity.setText(toCitySelected);
                actvToCity.setText(fromCitySelected);
            }
        });

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDateString = currentDate.format(formatter);
        tvDeparture.setText(currentDateString);
        tvReturn.setText(currentDateString);
        tvDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvDeparture);
            }
        });

        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvReturn);
            }
        });

        tvEconomy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvEconomy.setTextColor(getResources().getColor(R.color.white));
                tvEconomy.setBackgroundTintList(getResources().getColorStateList(R.color.green_500));
                tvBusiness.setTextColor(getResources().getColor(R.color.green_500));
                tvBusiness.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            }
        });

        tvBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvBusiness.setTextColor(getResources().getColor(R.color.white));
                tvBusiness.setBackgroundTintList(getResources().getColorStateList(R.color.green_500));
                tvEconomy.setTextColor(getResources().getColor(R.color.green_500));
                tvEconomy.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            }
        });

        ivBoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Boat");
            }
        });

        ivTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Train");
            }
        });

        ivBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Bus");
            }
        });

        btnSearch = findViewById(R.id.buttonSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportBooking.this, Flights.class);
                departureCity = actvFromCity.getText().toString();
                arrivalCity = actvToCity.getText().toString();
                intent.putExtra("DEPARTURE_CITY", departureCity);
                intent.putExtra("ARRIVAL_CITY", arrivalCity);
                intent.putExtra("DEPARTURE_DATE", tvDeparture.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog(TextView tv) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(tv.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Set selected date to TextView in "dd/MM/yyyy" format
                String selectedDate = String.format("%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year);
                tv.setText(selectedDate);
            }
        }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    private void showDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(title + " booking service is not available now.");
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}