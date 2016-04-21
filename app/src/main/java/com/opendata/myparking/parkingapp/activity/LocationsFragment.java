package com.opendata.myparking.parkingapp.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.opendata.myparking.parkingapp.R;
import com.opendata.myparking.parkingapp.adapter.LocationsAdapter;
import com.opendata.myparking.parkingapp.adapter.VehiclesAdapter;
import com.opendata.myparking.parkingapp.database.DBOpenHelper;
import com.opendata.myparking.parkingapp.model.Location;
import com.opendata.myparking.parkingapp.model.Vehicle;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by is chan on 17/04/2016.
 */
public class LocationsFragment extends Fragment {
    private ListView listLocations;
    private ArrayList<Location> locationArrayList; //temporary for testing
    private LocationsAdapter locationsAdapter;

    private Location location;

    private DBOpenHelper db;
    private TextInputLayout inputLayoutLocation;
    private TextInputLayout inputLayoutCost;
    private EditText inputLocation;
    private EditText inputCost;
    private Button btnSubmit;



    public LocationsFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_locations, container, false);

        inputLayoutLocation = (TextInputLayout) rootView.findViewById(R.id.input_layout_location);
        inputLayoutCost = (TextInputLayout) rootView.findViewById(R.id.input_layout_cost);
        inputLocation = (EditText) rootView.findViewById(R.id.input_location);
        inputCost = (EditText) rootView.findViewById(R.id.input_cost);
        btnSubmit = (Button) rootView.findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String location = inputLocation.getText().toString().trim();
                int cost = Integer.parseInt(inputCost.getText().toString().trim());
                inputLocation(location, cost);
            }
        });

        //Build access to database
        db = new DBOpenHelper(getActivity().getApplicationContext());

        locationsAdapter = new LocationsAdapter(getContext(), getLocations());
        listLocations = (ListView) rootView.findViewById(R.id.list_plates);

        listLocations.setAdapter(locationsAdapter);

        //Dont forget to close database
        db.closeDB();

        return rootView;
    }

    /**
     * Input locatiopn of parking to Database
     * @param location_name is a location name
     * @param cost is cost per hour
     */
    public void inputLocation(String location_name, int cost){
        location = new Location();
        location.setLocation_name(location_name);
        location.setCost(cost);

        long location_id = db.createLocation(location);

        Toast.makeText(getActivity().getApplicationContext(), "The location of parking is submitted.", Toast.LENGTH_SHORT).show();
    }
    public ArrayList<Location> getLocations(){
        locationArrayList = db.getAllLocations();
        return locationArrayList;
    }
}
