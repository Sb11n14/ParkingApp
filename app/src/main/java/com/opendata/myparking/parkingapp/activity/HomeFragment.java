package com.opendata.myparking.parkingapp.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.opendata.myparking.parkingapp.R;
import com.opendata.myparking.parkingapp.database.DBOpenHelper;
import com.opendata.myparking.parkingapp.model.Location;
import com.opendata.myparking.parkingapp.model.Parking;
import com.opendata.myparking.parkingapp.model.Vehicle;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.openalpr.OpenALPR;
import org.openalpr.model.Results;
import org.openalpr.model.ResultsError;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by is chan on 17/04/2016.
 */
public class HomeFragment extends Fragment {
    private static final int REQUEST_IMAGE = 100;
    final int STORAGE=1;
    private String ANDROID_DATA_DIR;
    private static File destination;
    private TextView resultTextView;
    private ImageView imageView;
    private FloatingActionButton fabButton;
    private String plate_number;
    private DBOpenHelper db;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DBOpenHelper(getActivity().getApplicationContext());
        /*Log.d("*****Testing: ", "Parking******");
        //Parking testing.
        Parking park1 = new Parking(1,1,1,5.0);
        Parking park2 = new Parking(2,1,1,15.0);
        Parking park3 = new Parking(3,1,1,115.0);
        Parking park4 = new Parking(4,1,1,1115.0);
        Parking park5 = new Parking(5,1,1,1115.0);

        long parkingId1 = db.createParking(park1);
        long parkingId2 = db.createParking(park2);
        long parkingId3 = db.createParking(park3);
        long parkingId4 = db.createParking(park4);
        long parkingId5 = db.createParking(park5);

        ArrayList<Parking> allParking = db.getAllParkings();
        for (Parking p: allParking) {
            Log.d("Results: ","Id= " + p.getParking_id() + " Veh Id= " + p.getKey_vehicle_id() + " LocId= " + p.getKey_location_id() + " TimeIn= " + p.getTime_in() + " charge= " + p.getCharge());

        }

        Parking oneParking = db.getParkingById(parkingId1);
        Log.d("oneParking: ","Id= " + oneParking.getParking_id() + " Veh Id= " + oneParking.getKey_vehicle_id() + " LocId= " + oneParking.getKey_location_id() + " TimeIn= " + oneParking.getTime_in() + " charge= " + oneParking.getCharge());


        String pCount = String.valueOf(db.countParking());
        Log.d("Parking count = ", pCount);


        Log.d("*****Testing: ", "Vehicle******");
        //Vehicle Testing
        Vehicle vehicle1 = new Vehicle("ABC1234","Brand 1","Model 1","1997","Blue");
        Vehicle vehicle2 = new Vehicle("EFG1234","Brand 1","Model 1","1997","Blue");
        Vehicle vehicle3 = new Vehicle("HIJK1234","Brand 1","Model 1","1997","Blue");
        Vehicle vehicle4 = new Vehicle("LMNO1234","Brand 1","Model 1","1997","Blue");
        Vehicle vehicle5 = new Vehicle("PQR1234","Brand 1","Model 1","1997","Blue");

        long vehicleId1 = db.createVehicle(vehicle1);
        long vehicleId2 = db.createVehicle(vehicle2);
        long vehicleId3 = db.createVehicle(vehicle3);
        long vehicleId4 = db.createVehicle(vehicle4);
        long vehicleId5 = db.createVehicle(vehicle5);

        ArrayList<Vehicle> allVehicle = db.getAllVehicles();
        for (Vehicle v: allVehicle) {
            Log.d("Results: ","Id= " + v.getId() + " Numberplate= " + v.getPlateNumber() + " Brand= " + v.getBrand() + " Model= " + v.getModel() + " yr= " + v.getYearManufactured());
        }

        Vehicle vById = db.getVehicleById(vehicleId1);
        Log.d("Results vById: ","Id= " + vById.getId() + " Numberplate= " + vById.getPlateNumber() + " Brand= " + vById.getBrand() + " Model= " + vById.getModel() + " yr= " + vById.getYearManufactured());

        Vehicle vByPlate = db.getVehicleByPlateNumber(vehicle1.getPlateNumber());
        Log.d("Results vByPlate: ","Id= " + vByPlate.getId() + " Numberplate= " + vByPlate.getPlateNumber() + " Brand= " + vByPlate.getBrand() + " Model= " + vByPlate.getModel() + " yr= " + vByPlate.getYearManufactured());

        String vCount = String.valueOf(db.countVehicle());
        Log.d("Vehicle count = ", vCount);
        */

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ANDROID_DATA_DIR = getActivity().getApplicationInfo().dataDir;


        resultTextView = (TextView) rootView.findViewById(R.id.textView);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);

        rootView.findViewById(R.id.fabButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });

        resultTextView.setText("Welcome! Press the red button below to scan your plae number.");

        // Inflate the layout for this fragment

        /**
         * For testing only
         */

        //Check the plate number in database, if it is not available, access open data
        /*db = new DBOpenHelper(getActivity().getApplicationContext());
        plate_number = "MT09NKS";
        if (db.isParkingExist(plate_number)){
            Parking park = db.getParking(plate_number); //Here we can pick the id of location
            Log.d("isParkingExist",plate_number);
            //updateParking(plate_number);
        } else {
            if (db.isVehicleExist(plate_number)) {
                Vehicle vce = db.getVehicle(plate_number);
                Location loc = getParkingLocation(1); //test set key id of parking location to 1
                insertParking(plate_number, vce, loc);
                Log.d("isVehicleExist",plate_number);
            } else {
                //Log.d("Plate Number", plate_number);
                //Accessing open data of plate number
                String urlString = "https://dvlasearch.appspot.com/DvlaSearch?licencePlate=" + plate_number + "&apikey=DvlaSearchDemoAccount";
                new JSONTask().execute(urlString);
            }
        }
        db.closeDB();
        *///For testing only EOF

        return rootView;
    }

    /**
     * Check permission to write the Phone's storage
     */
    private void checkPermission() {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        }
        if (!permissions.isEmpty()) {
            Toast.makeText(getActivity(), "Storage access needed to manage the picture.", Toast.LENGTH_LONG).show();
            String[] params = permissions.toArray(new String[permissions.size()]);
            ActivityCompat.requestPermissions(getActivity(), params, STORAGE);
        } else { // We already have permissions, so handle as normal
            takePicture();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            final ProgressDialog progress = ProgressDialog.show(getActivity(), "Loading", "Parsing result...", true);
            final String openAlprConfFile = ANDROID_DATA_DIR + File.separatorChar + "runtime_data" + File.separatorChar + "openalpr.conf";
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;

            // Picasso requires permission.WRITE_EXTERNAL_STORAGE
            Picasso.with(getActivity()).load(destination).fit().centerCrop().into(imageView);
            resultTextView.setText("Processing");

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    String result = OpenALPR.Factory.create(getActivity(), ANDROID_DATA_DIR).recognizeWithCountryRegionNConfig("eu", "", destination.getAbsolutePath(), openAlprConfFile, 10);

                    Log.d("OPEN ALPR", result);

                    try {
                        final Results results = new Gson().fromJson(result, Results.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (results == null || results.getResults() == null || results.getResults().size() == 0) {
                                    Toast.makeText(getActivity(), "It was not possible to detect the licence plate.", Toast.LENGTH_LONG).show();
                                    resultTextView.setText("It was not possible to detect the licence plate.");
                                } else {
                                    resultTextView.setText("Plate: " + results.getResults().get(0).getPlate()
                                            // Trim confidence to two decimal places
                                            + " Confidence: " + String.format("%.2f", results.getResults().get(0).getConfidence()) + "%"
                                            // Convert processing time to seconds and trim to two decimal places
                                            + " Processing time: " + String.format("%.2f", ((results.getProcessing_time_ms() / 1000.0) % 60)) + " seconds");

                                    plate_number = results.getResults().get(0).getPlate();


                                    //Check the plate number in database, if it is not available, access open data
                                    db = new DBOpenHelper(getActivity().getApplicationContext());
                                    if (db.isVehicleParked(1)) { //fix this!!!!!!
                                        //Parking park = db.getParking(plate_number); //Here we can pick the id of location
                                        //updateParking(plate_number);
                                    } else {
                                        if (db.isVehicleExist(plate_number)) {
                                            Vehicle vce = db.getVehicleByPlateNumber(plate_number);
                                            Location loc = getParkingLocation(1); //test set key id of parking location to 1
                                            insertParking(plate_number, 1); //For test only set location id 1
                                        } else {
                                            //Log.d("Plate Number", plate_number);
                                            //Accessing open data of plate number
                                            String urlString = "https://dvlasearch.appspot.com/DvlaSearch?licencePlate=" + plate_number + "&apikey=DvlaSearchDemoAccount";
                                            new JSONTask().execute(urlString);
                                        }
                                    }
                                    db.closeDB();

                                }
                            }
                        });

                    } catch (JsonSyntaxException exception) {
                        final ResultsError resultsError = new Gson().fromJson(result, ResultsError.class);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultTextView.setText(resultsError.getMsg());
                            }
                        });
                    }

                    progress.dismiss();
                }
            });
        }
    }

    private Location insertLocation(String name, int cost) {

        Location loc = new Location();


        loc.setLocation_name(name);
        loc.setCost(cost);
        long location_id = db.createLocation(loc);

        loc.setLocation_id(location_id);

        db.closeDB();
        return loc;
    }

    private Location getParkingLocation(int location_id) {

        db = new DBOpenHelper(getActivity().getApplicationContext());
        Location loc = db.getLocationById(location_id);

        db.closeDB();
        return loc;
    }

    /*private boolean isParked(String plate_number) {
        boolean isAvailable;
        db = new DBOpenHelper(getActivity().getApplicationContext());

        if(db.getParking(plate_number)==null){
            isAvailable = false;
        } else {
            isAvailable = true;
        }

        db.closeDB();
        return isAvailable;
    }*/

    private void insertParking(String plate_number, long location_id) {
        String time_in = dateToString(new Date(), "dd/MM/yyyy hh:mm:ss");

        Parking park = new Parking();
        //park.setKey_vehicle_id(plate_number);
        park.setKey_location_id(location_id);
        park.setTime_in(time_in);
        park.setActive(1);

        long parking_id = db.createParking(park);
        //Log.d("Vehicle id", db.getParkingById(parking_id).getKey_vehicle_id());
    }

    private void updateParking(String plate_number) {

    }


    /**
     * Insert a vehicle into Database
     */
    public void insertVehicle(Vehicle vehicle){
        db = new DBOpenHelper(getActivity().getApplicationContext());

        // Inserting a vehicle in db
        long vehicle1_id = db.createVehicle(vehicle);

        Log.d("Vehicle Count", " Count: " + db.getAllVehicles().size());

        // Don't forget to close database connection
        db.closeDB();

    }

    /**
     * Checking a vehicle in Database
     * @param plate_number
     * @return boolean isVehicle
     */
    private boolean isVehicleAvailable(String plate_number) {
        boolean isAvailable;
        db = new DBOpenHelper(getActivity().getApplicationContext());

        if (db.getVehicleByPlateNumber(plate_number)==null){
            isAvailable = false;
        } else {
            isAvailable = true;
        }

        db.closeDB();
        return isAvailable;
    }
/*
    private Vehicle getVehicle(String plate_number){
        db = new DBOpenHelper(getActivity().getApplicationContext());
        Vehicle vce = db.getVehicle(plate_number);

        db.closeDB();

        return vce;
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case STORAGE:{
                Map<String, Integer> perms = new HashMap<>();
                // Initial
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for WRITE_EXTERNAL_STORAGE
                Boolean storage = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                if (storage) {
                    // permission was granted, yay!
                    takePicture();
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "Storage permission is needed to analyse the picture.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * Taking a picture
     */
    public void takePicture() {
        // Use a folder to store all results
        File folder = new File(Environment.getExternalStorageDirectory() + "/OpenALPR/");
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Generate the path for the next photo
        String name = dateToString(new Date(), "yyyy-MM-dd-hh-mm-ss");
        destination = new File(folder, name + ".jpg");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    //This works
    public String dateToString(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());

        return df.format(date);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (destination != null) {// Picasso does not seem to have an issue with a null value, but to be safe
            Picasso.with(getActivity()).load(destination).fit().centerCrop().into(imageView);
        }
    }

    public class JSONTask extends AsyncTask<String,String,Vehicle> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //dialog.show();
        }

        @Override
        protected Vehicle doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {

                URL url = new URL(params[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                //JSONArray parentArray = parentObject.getJSONArray("movies");
                //JSONObject finalObject = parentArray.getJSONObject(0);

                String make = parentObject.getString("make");
                String model = parentObject.getString("model");
                String colour = parentObject.getString("colour");
                String year = parentObject.getString("yearOfManufacture");

                //Log.d("JSONTask",plate_number);

                Vehicle vehicle = new Vehicle();
                vehicle.setPlateNumber(plate_number);
                vehicle.setBrand(make);
                vehicle.setModel(model);
                vehicle.setYearManufactured(year);
                vehicle.setColor(colour);

                return vehicle;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return  null;

        }

        @Override
        protected void onPostExecute(final Vehicle result) {
            super.onPostExecute(result);

            try {
                long vehicle_id = db.createVehicle(result);

                Log.d("Total vehicle", String.valueOf(db.countVehicle()));
                //Still do not understand why it can not retrieve a vehicle by plate number
                //But when I retrieve by key id, it works!
                //Bye bye for a while

                // comment for the moment as we cant find the vehicle from the platenumber
               // Vehicle vce = db.getVehicle(plate_number);
                //Vehicle vce = db.getVehicleById(vehicle_id);
                //Log.d("Vehicle is plate", "Plate Number: " + vce.getPlateNumber() + ", ID: " + vce.getId());
                //

                //Location loc = getParkingLocation(1); //to test only
                //Log.d("Location", loc.getLocation_name());


                ////
                //try to insert parking time and vehicle

                //insertParking(result.getPlateNumber(), result, loc); //insert parking
                //insertParking(result.getPlateNumber(), result, loc);

                //insertParking(plate_number, vce, loc);

                //vehicle.setPlateNumber(plate_number);
               //vehicle_id;

                //String plate_number, Vehicle vehicle, Location location


                //insert automatically few locations in the database;
                // String name and int price per hour
                Location location1 = insertLocation("Uni Parking", 2);
                Location location2 = insertLocation("Private Parking", 4);
                Log.d("parking 1 ", location1.getLocation_name());
                Log.d("parking 2 ", location2.getLocation_name());

                Log.d("Vehicle is plate", "Plate Number: " + result.getPlateNumber() + ", ID: " + result.getId());
                insertParking(result.getPlateNumber(), 1); //testing for only location parking with `ID is 1
                Log.d("insert parking", String.valueOf(db.countParking()));





            } catch(Exception e) {

                //dialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "The plate number is not recognised.", Toast.LENGTH_SHORT).show();
            }

            //dialog.dismiss();

            if(result != null) {

            } else {
                Toast.makeText(getActivity().getApplicationContext(), "The plate number is not recognised.", Toast.LENGTH_SHORT).show();
            }
        }



    }
}
