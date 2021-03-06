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

import com.opendata.myparking.parkingapp.R;
import com.opendata.myparking.parkingapp.database.DBOpenHelper;
import com.opendata.myparking.parkingapp.model.Location;

/**
 * Created by Shamel on 25/04/2016.
 */
public class EditLocationActivity extends ActionBarActivity {

    private TextInputLayout inputLayoutLocation;
    private TextInputLayout inputLayoutCost;
    private EditText inputLocation;
    private EditText inputCost;
    private Button btnSubmit;
    private Toolbar toolbar;
    private DBOpenHelper db;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputLayoutLocation = (TextInputLayout) findViewById(R.id.input_layout_location);
        inputLayoutCost = (TextInputLayout) findViewById(R.id.input_layout_cost);
        inputLocation = (EditText) findViewById(R.id.input_location);
        inputCost = (EditText) findViewById(R.id.input_cost);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Edit Location"); //Set Title on Toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //to be implemented to handle location edit in db
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /*db = new DBOpenHelper(getApplicationContext());
                String location = inputLocation.getText().toString().trim();
                int cost = Integer.parseInt(inputCost.getText().toString().trim());
                editLocation(location, cost);
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

    // insert code for editing the model object and the database
    private void editLocation(String location, int cost) {
    }

}
