package com.example.travelly;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelly.Database.FlightsDatabaseHandler;
import com.example.travelly.R;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class Loading extends AppCompatActivity {
    private final int DELAY_MILLIS = 1000;
    private ImageView ivLoadingBar;

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

        ivLoadingBar = findViewById(R.id.imageViewLoadingBar);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ivLoadingBar.setImageResource(R.drawable.loading40);
            initializeData();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                ivLoadingBar.setImageResource(R.drawable.loading100);
                jumpToMainActivity();
            }, DELAY_MILLIS);
        }, DELAY_MILLIS);
    }

    private void initializeData() {
        FlightsDatabaseHandler db = new FlightsDatabaseHandler(this);
        db.deleteAllTables();
        db.addFamousCities();
        db.generateFlights();
    }

    private void jumpToMainActivity() {
        Intent intent = new Intent(Loading.this, MainActivity.class);
        startActivity(intent);
    }
}