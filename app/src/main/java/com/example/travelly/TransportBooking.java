package com.example.travelly;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class TransportBooking extends AppCompatActivity {
    ImageButton btnBack, btnSwap;
    Spinner spnFromCity, spnToCity;
    TextView tvDeparture, tvReturn, tvEconomy, tvBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        spnFromCity = findViewById(R.id.spinnerFromCity);
        spnToCity = findViewById(R.id.spinnerToCity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options_array_City, R.layout.spinner_city_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFromCity.setAdapter(adapter);
        spnToCity.setAdapter(adapter);

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

    }

    private void showDatePickerDialog(TextView tv) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Set selected date to TextView
                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                tv.setText(selectedDate);
            }
        }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }


}