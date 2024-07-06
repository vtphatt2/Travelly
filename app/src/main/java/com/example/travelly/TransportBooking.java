package com.example.travelly;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    Spinner spnFromCity, spnToCity;
    TextView tvDeparture, tvReturn, tvEconomy, tvBusiness;
    ImageView ivPlane, ivBoat, ivTrain, ivBus;
    Button btnSearch;
    String departureCity, arrivalCity;
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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        db = new FlightsDatabaseHandler(this);
        List<String> cityList = db.getAllCities();
        spnFromCity = findViewById(R.id.spinnerFromCity);
        spnToCity = findViewById(R.id.spinnerToCity);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_city_item, cityList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFromCity.setAdapter(adapter);
        spnToCity.setAdapter(adapter);

        spnFromCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departureCity = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        spnToCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                arrivalCity = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        btnSwap = findViewById(R.id.buttonSwap);
        btnSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int spinner1Position = spnFromCity.getSelectedItemPosition();
                int spinner2Position = spnToCity.getSelectedItemPosition();
                spnFromCity.setSelection(spinner2Position);
                spnToCity.setSelection(spinner1Position);
            }
        });

        tvDeparture = findViewById(R.id.textViewDepartureDate);
        tvReturn = findViewById(R.id.textViewReturnDate);
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

        tvEconomy = findViewById(R.id.textViewEconomy);
        tvBusiness = findViewById(R.id.textViewBusiness);
        tvEconomy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvEconomy.setTextColor(Color.parseColor("#FFFFFF"));
                tvEconomy.setBackgroundColor(Color.parseColor("#089093"));
                tvBusiness.setTextColor(Color.parseColor("#089093"));
                tvBusiness.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

        tvBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvBusiness.setTextColor(Color.parseColor("#FFFFFF"));
                tvBusiness.setBackgroundColor(Color.parseColor("#089093"));
                tvEconomy.setTextColor(Color.parseColor("#089093"));
                tvEconomy.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

        ivPlane = findViewById(R.id.imageViewPlane);
        ivBoat = findViewById(R.id.imageViewBoat);
        ivTrain = findViewById(R.id.imageViewTrain);
        ivBus = findViewById(R.id.imageViewBus);

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