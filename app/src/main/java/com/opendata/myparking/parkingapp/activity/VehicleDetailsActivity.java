package com.opendata.myparking.parkingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.opendata.myparking.parkingapp.R;

import org.w3c.dom.Text;

/**
 * Created by is chan on 07/04/2016.
 */
public class VehicleDetailsActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView tvPlate_number;
    private TextView tvBrand;
    private TextView tvModel;
    private TextView tvColour;
    private TextView tvYear;

    private String plate_number;
    private String brand;
    private String model;
    private String colour;
    private String year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvBrand = (TextView) findViewById(R.id.brand);
        tvModel = (TextView) findViewById(R.id.model);
        tvColour = (TextView) findViewById(R.id.colour);
        tvYear = (TextView) findViewById(R.id.year);

        Intent intent = getIntent();
        if(intent != null) {
            actionBar.setTitle("Detail " + intent.getStringExtra("plate_number").toUpperCase());
            tvBrand.setText("Brand: " + intent.getStringExtra("brand"));
            tvModel.setText("Model: " + intent.getStringExtra("model"));
            tvColour.setText("Colour: " + intent.getStringExtra("colour"));
            tvYear.setText("Year: " + intent.getStringExtra("year"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
