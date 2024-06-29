package com.example.travelly;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Booking extends Fragment {
    private ListView listView;
    private BookingItemAdapter customAdapter;
    private List<BookingItem> itemList;

    public Booking() {
        // Required empty public constructor
    }

    public static Booking newInstance() {
        Booking fragment = new Booking();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        listView = view.findViewById(R.id.listViewBooking);
        itemList = new ArrayList<>();
        itemList.add(new BookingItem("Trips", R.drawable.trips_booking));
        itemList.add(new BookingItem("Hotel", R.drawable.hotel_booking));
        itemList.add(new BookingItem("Transport", R.drawable.transport_booking));
        itemList.add(new BookingItem("Event", R.drawable.event_booking));

        customAdapter = new BookingItemAdapter(requireActivity(), itemList);
        listView.setAdapter(customAdapter);

        return view;
    }
}