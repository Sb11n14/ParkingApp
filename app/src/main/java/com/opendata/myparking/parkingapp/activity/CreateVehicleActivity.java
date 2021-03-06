package com.opendata.myparking.parkingapp.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.opendata.myparking.parkingapp.R;
import com.opendata.myparking.parkingapp.model.Location;

/**
 * Created by Shamel on 25/04/2016.
 */
public class CreateVehicleActivity extends ActionBarActivity {

    private TextInputLayout inputLayoutPlateNumber;
    private TextInputLayout inputLayoutBrand;
    private TextInputLayout inputLayoutModel;
    private TextInputLayout inputLayoutColour;
    private TextInputLayout inputLayoutYear;
    private EditText inputPlateNumber;
    private EditText inputBrand;
    private EditText inputModel;
    private EditText inputColour;
    private EditText inputYear;
    private Button btnSubmit;
    private Toolbar toolbar;
    //private DBOpenHelper db;
    //private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vehicle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        inputLayoutPlateNumber = (TextInputLayout) findViewById(R.id.input_layout_plate_number);
        inputLayoutBrand = (TextInputLayout) findViewById(R.id.input_layout_brand);
        inputLayoutModel = (TextInputLayout) findViewById(R.id.input_layout_model);
        inputLayoutColour = (TextInputLayout) findViewById(R.id.input_layout_colour);
        inputLayoutYear = (TextInputLayout) findViewById(R.id.input_layout_year);
        inputPlateNumber = (EditText) findViewById(R.id.input_plate_number);
        inputBrand = (EditText) findViewById(R.id.input_brand);
        inputModel = (EditText) findViewById(R.id.input_model);
        inputColour = (EditText) findViewById(R.id.input_colour);
        inputYear = (EditText) findViewById(R.id.input_year);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Create Vehicle"); //Set Title on Toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //to be implemented to handle vehicle insertion in db
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               /* db = new DBOpenHelper(getApplicationContext());
                String location = inputLocation.getText().toString().trim();
                int cost = Integer.parseInt(inputCost.getText().toString().trim());
                inputLocation(location, cost);
                db.closeDB();
                onBackPressed();*/
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Action when the menu on the toolbar pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_clear:
                return true;
            case R.id.action_done:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

   /* *//**
     * Input vehicle to Database : modify with vehicle
     * @param location_name is a location name
     * @param cost is cost per minute
     *//*
    public void inputLocation(String location_name, int cost){
        location = new Location();
        location.setLocation_name(location_name);
        location.setCost(cost);

        long location_id = db.createLocation(location);

        Toast.makeText(getApplicationContext(), "The location of parking is submitted.", Toast.LENGTH_SHORT).show();
    }*/



}
