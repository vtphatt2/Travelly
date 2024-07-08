package com.example.travelly;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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
        itemList.add(new BookingItem("Trips", R.drawable.trip_card));
        itemList.add(new BookingItem("Hotel", R.drawable.hotel_card));
        itemList.add(new BookingItem("Transport", R.drawable.transport_card));
        itemList.add(new BookingItem("Events", R.drawable.event_card));

        customAdapter = new BookingItemAdapter(requireActivity(), itemList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                BookingItem item = itemList.get(position);
                if (item.getTitle().equals("Transport")) {
                    Intent intent = new Intent(requireContext(), TransportBooking.class);
                    startActivity(intent);
                }
                else {
                    showDialog(item.getTitle());
                }
            }
        });
        listView.setAdapter(customAdapter);

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