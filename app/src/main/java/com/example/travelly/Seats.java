package com.example.travelly;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class Seats extends AppCompatActivity {
    private ListView listViewSeat;
    private SeatAdapter adapterSeat;
    private List<Integer> SeatRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seats);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listViewSeat = findViewById(R.id.listViewSeat);
        for (int i = 1 ; i <= 10 ; ++i) {
            SeatRow.add(i);
        }
        adapterSeat = new SeatAdapter(this, SeatRow);
        listViewSeat.setAdapter(adapterSeat);

    }
}