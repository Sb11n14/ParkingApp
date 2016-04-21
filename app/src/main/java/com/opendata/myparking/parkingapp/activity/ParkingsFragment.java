package com.opendata.myparking.parkingapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.opendata.myparking.parkingapp.R;
import com.opendata.myparking.parkingapp.adapter.ParkingsAdapter;
import com.opendata.myparking.parkingapp.database.DBOpenHelper;
import com.opendata.myparking.parkingapp.model.Parking;
import com.opendata.myparking.parkingapp.model.Vehicle;

import java.util.ArrayList;

/**
 * Created by is chan on 17/04/2016.
 */
public class ParkingsFragment extends Fragment {
    private ListView listPlateNumbers;
    private ArrayList<Parking> listParkings; //temporary for testing
    private ParkingsAdapter adapterparking;

    private DBOpenHelper db;
    public ParkingsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_parkings, container, false);

        db = new DBOpenHelper(getActivity().getApplicationContext());
        listParkings = db.getAllParkings();

        adapterparking = new ParkingsAdapter(getContext(), listParkings);
        listPlateNumbers = (ListView) rootView.findViewById(R.id.list_plates);

        listPlateNumbers.setAdapter(adapterparking);

        db.closeDB();

        return rootView;
    }
}
