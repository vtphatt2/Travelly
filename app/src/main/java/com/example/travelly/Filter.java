package com.example.travelly;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.slider.RangeSlider;

import java.util.Arrays;
import java.util.List;

public class Filter extends AppCompatActivity implements FilterAdapter.OnItemClickListener {
    ImageButton btnBack, btnCoffee, btnForkKnife, btnWifi, btnSnowFlake;
    RecyclerView rvDeparture, rvArrival;
    RangeSlider rsPrice;
    EditText etFromPrice, etToPrice;
    RadioGroup rgSortBy;
    Button btnDone;
    private boolean isUpdating = false;
    private boolean isChooseCoffee = false, isChooseForkKnife = false, isChooseWifi = false, isChooseSnowFlake = false;
    private String selectedSortByOption = "";
    private String selectedDepartureRange = "All";

    @Override
    public void onItemClick(String item) {
        selectedDepartureRange = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter);
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

        rvDeparture = findViewById(R.id.recyclerViewDeparture);
        rvDeparture.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<String> departureOptions = Arrays.asList("All", "12AM - 04AM", "04AM - 08AM", "08AM - 12PM", "12PM - 04PM", "04PM - 08PM", "08PM - 12AM");
        FilterAdapter departureAdapter = new FilterAdapter(departureOptions);
        departureAdapter.setOnClickListener(this);
        rvDeparture.setAdapter(departureAdapter);

        rvArrival = findViewById(R.id.recyclerViewArrival);
        rvArrival.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<String> arrivalOptions = Arrays.asList("All", "12AM - 04AM", "04AM - 08AM", "08AM - 12PM", "12PM - 04PM", "04PM - 08PM", "08PM - 12AM");
        FilterAdapter arrivalAdapter = new FilterAdapter(arrivalOptions);
        rvArrival.setAdapter(arrivalAdapter);

        rsPrice = findViewById(R.id.sliderPrice);
        etFromPrice = findViewById(R.id.editTextFrom);
        etToPrice = findViewById(R.id.editTextTo);

        // Set initial slider values
        rsPrice.setValues(250f, 500f);
        rsPrice.setCustomThumbDrawable(R.drawable.custom_thumb);
        rsPrice.setThumbHeight(50);
        rsPrice.setTrackActiveTintList(getResources().getColorStateList(R.color.green_500));

        // Add TextWatchers to EditText fields to update RangeSlider values
        etFromPrice.addTextChangedListener(new CustomTextWatcher(etFromPrice));
        etToPrice.addTextChangedListener(new CustomTextWatcher(etToPrice));

        // Set event listener for slider value changes
        rsPrice.addOnChangeListener((slider, value, fromUser) -> {
            if (!isUpdating) {
                isUpdating = true;
                List<Float> values = rsPrice.getValues();
                updateEditTexts(values.get(0).intValue(), values.get(1).intValue());
                isUpdating = false;
            }
        });

        btnCoffee = findViewById(R.id.imageButtonCoffee);
        btnForkKnife = findViewById(R.id.imageButtonForkKnife);
        btnWifi = findViewById(R.id.imageButtonWifi);
        btnSnowFlake = findViewById(R.id.imageButtonSnowFlake);

        btnCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChooseCoffee) {
                    btnCoffee.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                    btnCoffee.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_500));
                } else {
                    btnCoffee.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_500));
                    btnCoffee.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                }
                isChooseCoffee = !isChooseCoffee;
            }
        });

        btnForkKnife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChooseForkKnife) {
                    btnForkKnife.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                    btnForkKnife.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_500));
                } else {
                    btnForkKnife.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_500));
                    btnForkKnife.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                }
                isChooseForkKnife = !isChooseForkKnife;
            }
        });

        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChooseWifi) {
                    btnWifi.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                    btnWifi.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_500));
                } else {
                    btnWifi.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_500));
                    btnWifi.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                }
                isChooseWifi = !isChooseWifi;
            }
        });

        btnSnowFlake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChooseSnowFlake) {
                    btnSnowFlake.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                    btnSnowFlake.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_500));
                } else {
                    btnSnowFlake.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_500));
                    btnSnowFlake.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
                }
                isChooseSnowFlake = !isChooseSnowFlake;
            }
        });

        rgSortBy = findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                selectedSortByOption = selectedRadioButton.getText().toString();
            }
        });


        btnDone = findViewById(R.id.buttonDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("DEPARTURE", selectedDepartureRange);
                resultIntent.putExtra("PRICE-MIN", Double.valueOf(etFromPrice.getText().toString()));
                resultIntent.putExtra("PRICE-MAX", Double.valueOf(etToPrice.getText().toString()));
                resultIntent.putExtra("SORT-BY", selectedSortByOption);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

    }


    private class CustomTextWatcher implements TextWatcher {
        private EditText editText;

        public CustomTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!isUpdating) {
                updateSliderValues();
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    private void updateSliderValues() {
        isUpdating = true;
        String fromText = etFromPrice.getText().toString();
        String toText = etToPrice.getText().toString();
        if (!fromText.isEmpty() && !toText.isEmpty()) {
            float fromValue = Float.parseFloat(fromText);
            float toValue = Float.parseFloat(toText);
            rsPrice.setValues(fromValue, toValue);
        }
        isUpdating = false;
    }

    private void updateEditTexts(int fromValue, int toValue) {
        etFromPrice.setText(String.valueOf(fromValue));
        etToPrice.setText(String.valueOf(toValue));
    }
}