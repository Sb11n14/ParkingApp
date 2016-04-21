package com.opendata.myparking.parkingapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.opendata.myparking.parkingapp.R;
import com.opendata.myparking.parkingapp.adapter.VehiclesAdapter;
import com.opendata.myparking.parkingapp.database.DBOpenHelper;
import com.opendata.myparking.parkingapp.model.Vehicle;

import java.util.ArrayList;

/**
 * Created by is chan on 17/04/2016.
 */
public class VehiclesFragment extends Fragment {
    private ListView listPlateNumbers;
    private ArrayList<Vehicle> vehicleArrayList; //temporary for testing
    private VehiclesAdapter vehiclesAdapter;

    private DBOpenHelper db;
    public VehiclesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_vehicles, container, false);

        db = new DBOpenHelper(getActivity().getApplicationContext());
        vehicleArrayList = db.getAllVehicles();

        vehiclesAdapter = new VehiclesAdapter(getContext(),vehicleArrayList);
        listPlateNumbers = (ListView) rootView.findViewById(R.id.list_plates);

        listPlateNumbers.setAdapter(vehiclesAdapter);

        return rootView;
    }
}
