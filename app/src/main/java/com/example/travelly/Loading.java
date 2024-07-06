package com.example.travelly;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelly.Database.FlightsDatabaseHandler;
import com.example.travelly.R;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;

public class Loading extends AppCompatActivity {
    private final int DELAY_MILLIS = 500;
    private static final int STAGE_ONE_PROGRESS = 40;
    private static final int STAGE_TWO_PROGRESS = 100;
    private ImageView ivLoadingBar;
    private TextView tvLoadingPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvLoadingPercent = findViewById(R.id.textViewLoadingPercent);
        ivLoadingBar = findViewById(R.id.imageViewLoadingBar);

//        initializeData();
        cleanAndReinitializeData();
        jumpToMainActivity();
    }

    private void initializeData() {
        FlightsDatabaseHandler db = new FlightsDatabaseHandler(this);

        LocalDate currentDate = LocalDate.now();
        if (db.flightExistsWithSpecificDate(currentDate.getDayOfMonth(), currentDate.getMonthValue(), currentDate.getYear())) {
            // Stage 1: Delete all tables
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                updateLoadingProgress(STAGE_ONE_PROGRESS);
            }, DELAY_MILLIS);

            // Stage 2: Add famous cities and generate flights
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                updateLoadingProgress(STAGE_TWO_PROGRESS);
                jumpToMainActivity();
            }, 2 * DELAY_MILLIS);
        }
        else {
            // Stage 1: Delete all tables
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                db.deleteAllTables();
                updateLoadingProgress(STAGE_ONE_PROGRESS);
            }, DELAY_MILLIS);

            // Stage 2: Add famous cities and generate flights
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                db.addFamousCities();
                db.generateFlights();
                updateLoadingProgress(STAGE_TWO_PROGRESS);
                jumpToMainActivity();
            }, 2 * DELAY_MILLIS);
        }
    }

    private void updateLoadingProgress(int progress) {
        tvLoadingPercent.setText("Loading... " + progress + "%");

        switch (progress) {
            case STAGE_ONE_PROGRESS:
                ivLoadingBar.setImageResource(R.drawable.loading40);
                break;
            case STAGE_TWO_PROGRESS:
                ivLoadingBar.setImageResource(R.drawable.loading100);
                break;
            default:
                ivLoadingBar.setImageResource(R.drawable.loading0);
                break;
        }
    }

    private void jumpToMainActivity() {
        Intent intent = new Intent(Loading.this, MainActivity.class);
        startActivity(intent);
    }

    private void cleanAndReinitializeData() {
        FlightsDatabaseHandler db = new FlightsDatabaseHandler(this);
        db.deleteAllTables();
        db.addFamousCities();
        db.generateFlights();
    }
}