package com.example.travelly;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class EditInformation extends Fragment {
    private PersonalInfo account;
    private Button btnSaveChanges;
    private ImageButton btnBack;
    private TextView tvFirstName, tvLastName, tvPhone, tvEmail;

    public EditInformation() {
        // Required empty public constructor
    }

    public static EditInformation newInstance(PersonalInfo account) {
        EditInformation fragment = new EditInformation();
        Bundle arguments = new Bundle();
        arguments.putParcelable("PERSONAL_INFORMATION", account);
        fragment.setArguments(arguments);
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

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.setFirstName(tvFirstName.getText().toString());
                account.setLastName(tvLastName.getText().toString());
                account.setPhone(tvPhone.getText().toString());
                account.setEmail(tvEmail.getText().toString());
            }
        });


        tvFirstName = view.findViewById(R.id.edit_text_FirstName);
        tvLastName = view.findViewById(R.id.edit_text_LastName);
        tvPhone = view.findViewById(R.id.edit_text_Phone);
        tvEmail = view.findViewById(R.id.edit_text_Email);

        if (getArguments().containsKey("PERSONAL_INFORMATION")) {
            account = getArguments().getParcelable("PERSONAL_INFORMATION");
            tvFirstName.setText(account.getFirstName());
            tvLastName.setText(account.getLastName());
            tvPhone.setText(account.getPhone());
            tvEmail.setText(account.getEmail());
        }

        return view;
    }
}