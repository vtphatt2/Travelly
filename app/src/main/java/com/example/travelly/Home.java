package com.example.travelly;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Home extends Fragment {
    private LinearLayout llTrips, llHotel, llTransport, llEvents;

    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        llTrips = view.findViewById(R.id.llTrips);
        llHotel = view.findViewById(R.id.llHotel);
        llTransport = view.findViewById(R.id.llTransport);
        llEvents = view.findViewById(R.id.llEvents);

        llTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Success", "llTrips : On click");
                showDialog("Trips");
            }
        });

        llHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Success", "llHotel  : On click");
                showDialog("Hotel");
            }
        });

        llEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Success", "llEvents : On click");
                showDialog("Events");
            }
        });

        llTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Success", "llTransport : On click");
                Intent intent = new Intent(requireContext(), TransportBooking.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void showDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(title);
        builder.setMessage(title + " services are not available now.");
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}