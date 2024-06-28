package com.example.travelly;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Account extends Fragment {
    private LinearLayout llPayment;

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

        llPayment = view.findViewById(R.id.llPayment);

        llPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Payment and cards");
                Log.d("Success", "Payment and cards : On click");
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
}