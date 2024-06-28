package com.example.travelly;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class EditInformation extends Fragment {
    private PersonalInfo account;
    private Button btnSaveChanges;
    private ImageButton btnBack;

    public EditInformation() {
        // Required empty public constructor
    }

    public static EditInformation newInstance() {
        EditInformation fragment = new EditInformation();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_information, container, false);

        btnSaveChanges = view.findViewById(R.id.buttonSaveChanges);
        btnBack = view.findViewById(R.id.buttonBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, Account.newInstance(account)).commit();
            }
        });

        return view;
    }
}