package com.example.travelly;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class Account extends Fragment {
    private LinearLayout llPersonal, llPayment, llSaved, llBooking, llSetting;
    private Button btnEndSession;

    public Account() {
        // Required empty public constructor
    }

    public static Account newInstance() {
        Account fragment = new Account();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        llPersonal = view.findViewById(R.id.llPersonal_info);
        llPayment = view.findViewById(R.id.llPayment);
        llSaved = view.findViewById(R.id.llSaved);
        llBooking = view.findViewById(R.id.llBooking);
        llSetting = view.findViewById(R.id.llSetting);

        llPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, EditInformation.newInstance()).commit();
            }
        });

        llPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Payment and cards");
                Log.d("Success", "Payment and cards : On click");
            }
        });

        llSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Saved");
                Log.d("Success", "Saved : On click");
            }
        });

        llBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Booking history");
                Log.d("Success", "Booking history : On click");
            }
        });

        llSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Settings");
                Log.d("Success", "Settings");
            }
        });

        btnEndSession = view.findViewById(R.id.endSessionButton);
        btnEndSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitConfirmationDialog();
            }
        });

        return view;
    }

    private void showDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(title);
        builder.setMessage(title + " function is being developed...");
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(requireActivity())
                .setTitle("Confirm Exit")
                .setMessage("Are you sure you want to close the app?")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    requireActivity().finish();
                    System.exit(0);
                }).setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }
}