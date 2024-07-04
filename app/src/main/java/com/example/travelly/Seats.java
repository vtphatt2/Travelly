package com.example.travelly;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Seats extends AppCompatActivity implements SeatAdapter.OnButtonClickListener {
    private ListView listViewSeat;
    private SeatAdapter adapterSeat;
    private List<String> seatCode = new ArrayList<>();
    private TextView tvSelectedSeat;
    private ImageButton btnBack;

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

        List<Integer> SeatRow = new ArrayList<Integer>();
        listViewSeat = findViewById(R.id.listViewSeat);
        for (int i = 1 ; i <= 10 ; ++i) {
            SeatRow.add(i);
        }
        adapterSeat = new SeatAdapter(this, SeatRow, this);
        listViewSeat.setAdapter(adapterSeat);

        tvSelectedSeat = findViewById(R.id.textViewSelectedSeat);
        btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onButtonClick(int position, int buttonId, boolean toggle) {
        String seatcode;
        position++;

        if (buttonId == 1) {
            seatcode = String.valueOf(position) + "A";
        }
        else if (buttonId == 2) {
            seatcode = String.valueOf(position) + "B";
        }
        else if (buttonId == 3) {
            seatcode = String.valueOf(position) + "C";
        }
        else {
            seatcode = String.valueOf(position) + "D";
        }

        if (toggle) {
            seatCode.add(seatcode);
        }
        else {
            seatCode.removeIf(str -> str.equals(seatcode));
        }

        String str = "Traveller " + String.valueOf(seatCode.size()) + " / Seat ";
        for (int i = 0 ; i < seatCode.size() ; ++i) {
            if (i == seatCode.size() - 1) {
                str += seatCode.get(i);
            }
            else {
                str += seatCode.get(i) + ", ";
            }
        }
        tvSelectedSeat.setText(str);
    }
}