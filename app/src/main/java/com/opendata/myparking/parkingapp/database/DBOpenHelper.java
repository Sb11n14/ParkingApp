package com.opendata.myparking.parkingapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opendata.myparking.parkingapp.model.Location;
import com.opendata.myparking.parkingapp.model.Parking;
import com.opendata.myparking.parkingapp.model.Vehicle;

import java.util.ArrayList;


/**
 * Created by is chan on 17/04/2016.
 */
public class DBOpenHelper extends SQLiteOpenHelper{
    // Logcat tag
    private static final String LOG = "DBOpenHelper";

    //Constants for db name and version
    private static final String DATABASE_NAME = "parking.db";
    private static final int DATABASE_VERSION = 3;


    //Constants for identifying table & column

    //Table names
    public static final String TABLE_PARKING = "parking";
    public static final String TABLE_LOCATION = "location";
    public static final String TABLE_VEHICLE = "vehicle";

    // Common column names
    private static final String KEY_ID = "_id";

    // PARKING Table - column names
    //public static final String PARKING_ID = "_id";
    public static final String TIME_IN = "timein";
    public static final String TIME_OUT = "timeout";
    public static final String ACTIVE = "active";
    private static final String KEY_LOCATION_ID = "location_id";
    private static final String KEY_VEHICLE_ID = "vehicle_id";

    // LOCATION Table - column names
    //public static final String LOCATION_ID = "_id";
    public static final String LOCATION_NAME = "location_name";
    public static final String COST = "cost";

    // VEHICLE Table - column names
    public static final String PLATE_NUMBER = "plate_number";
    public static final String BRAND = "brand";
    public static final String MODEL = "model";
    public static final String YEAR_MANUFACTURED = "year_manufactured";
    public static final String COLOR = "color";

    public static final String [] ALL_COLUMNS_PARKING =
            {KEY_ID, TIME_IN, TIME_OUT, ACTIVE, KEY_LOCATION_ID, KEY_VEHICLE_ID};

    public static final String [] ALL_COLUMNS_LOCATION =
            {KEY_ID, LOCATION_NAME, COST};

    public static final String [] ALL_COLUMNS_VEHICLE =
            {KEY_ID, PLATE_NUMBER, BRAND, MODEL, YEAR_MANUFACTURED, COLOR};


    // parking table create statement
    //KEY_VEHICLE_ID set for plate number
    private static final String CREATE_TABLE_PARKING =
            "CREATE TABLE " + TABLE_PARKING + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    TIME_IN + " TEXT, " +
                    TIME_OUT + " TEXT, " +
                    ACTIVE + " INTEGER, " +
                    KEY_LOCATION_ID + " INTEGER, " +
                    KEY_VEHICLE_ID + " TEXT" +
                    ");";


    // location table create statement
    private static final String CREATE_TABLE_LOCATION =
            "CREATE TABLE " + TABLE_LOCATION + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    LOCATION_NAME + " TEXT, " +
                    COST + " INTEGER" +
                    ");";


    // vehicle table create statement
    private static final String CREATE_TABLE_VEHICLE =
            "CREATE TABLE " + TABLE_VEHICLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    PLATE_NUMBER + " TEXT, " +
                    BRAND + " TEXT, " +
                    MODEL + " TEXT, " +
                    YEAR_MANUFACTURED + " TEXT, " +
                    COLOR + " TEXT" +
                    ");";


    //Context c;

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        //c= context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL(TABLE_CREATE);
        // creating required tables
        db.execSQL(CREATE_TABLE_PARKING);
        db.execSQL(CREATE_TABLE_LOCATION);
        db.execSQL(CREATE_TABLE_VEHICLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARKING);
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARKING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLE);
        onCreate(db);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

// ------------------------ "Parking" table methods ----------------//
    public int countParking(){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT COUNT(*) FROM " + TABLE_PARKING ;
        Cursor c = db.rawQuery(count, null);
        c.moveToFirst();
        int icount = c.getInt(0);
        c.close();
        return icount;
    }

    public boolean isParkingExist (String plate_number){
        SQLiteDatabase db = this.getReadableDatabase();
        if (countParking() > 0) {
            String selectQuery = "SELECT * FROM " + TABLE_PARKING +
                    " WHERE " + KEY_VEHICLE_ID + " = " + plate_number;
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.getCount() <= 0) {
                c.close();
                return false;
            } else {
                c.close();
                return true;
            }
        } else {
            return false;
        }
    }


    public long createParking (Parking parking){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VEHICLE_ID, parking.getKey_vehicle_id());
        values.put(KEY_LOCATION_ID, parking.getKey_location_id());
        values.put(TIME_IN, parking.getTime_in());
        values.put(ACTIVE, parking.getActivation());
        values.put(TIME_OUT, parking.getTime_out());

        // insert row
        long parking_id = db.insert(TABLE_PARKING, null, values);

        return parking_id;
    }

    //Getting a parking
    public Parking getParkingById(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PARKING +
                " WHERE " + KEY_ID + " = " + id;
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        Parking park = new Parking();
        park.setParking_id(c.getInt((c.getColumnIndex(KEY_ID))));
        park.setKey_vehicle_id(c.getString(c.getColumnIndex(KEY_VEHICLE_ID)));
        park.setKey_location_id(c.getInt(c.getColumnIndex(KEY_LOCATION_ID)));
        park.setTime_in(c.getString(c.getColumnIndex(TIME_IN)));
        park.setTime_out(c.getString(c.getColumnIndex(TIME_OUT)));
        park.setActive(c.getInt(c.getColumnIndex(ACTIVE)));

        c.close();
        return park;
    }

    /**
     * getting all parkings
     * */
    public ArrayList<Parking> getAllParkings() {
        ArrayList<Parking> parkings = new ArrayList<Parking>();
        String selectQuery = "SELECT  * FROM " + TABLE_PARKING;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Parking parking = new Parking();
                parking.setParking_id(c.getInt((c.getColumnIndex(KEY_ID))));
                parking.setTime_in(c.getString(c.getColumnIndex(TIME_IN)));
                parking.setKey_location_id(c.getInt(c.getColumnIndex(KEY_LOCATION_ID)));
                parking.setKey_vehicle_id(c.getString(c.getColumnIndex(KEY_VEHICLE_ID)));
                parking.setActive(c.getInt(c.getColumnIndex(ACTIVE)));

                // adding to todo list
                parkings.add(parking);
            } while (c.moveToNext());
        }
        c.close();
        return parkings;
    }


    public void deleteParking(String plate){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PARKING, KEY_VEHICLE_ID + " = ?",
                new String[] { String.valueOf(plate)});
    }

// ------------------------ "vehicle" table methods ----------------//

//see http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
    public int countVehicle(){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT COUNT(*) FROM " + TABLE_VEHICLE;
        Cursor c = db.rawQuery(count, null);
        c.moveToFirst();
        int icount = c.getInt(0);
        c.close();
        return icount;
    }

    public boolean isVehicleExist (String plate){
        SQLiteDatabase db = this.getReadableDatabase();
        if (countVehicle() > 0) {
            String selectQuery = "SELECT * FROM " + TABLE_VEHICLE +
                    " WHERE " + PLATE_NUMBER+ " = " + plate;

            Cursor c = db.rawQuery(selectQuery, null);
            if (c.getCount() <= 0) {
                c.close();
                return false;
            } else {
                c.close();
                return true;
            }
        } else {
            return false;
        }
    }

    /*
     * Creating vehicle
     */
    public long createVehicle(Vehicle vehicle) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Database nih", vehicle.getPlateNumber());
        ContentValues values = new ContentValues();
        values.put(PLATE_NUMBER, vehicle.getPlateNumber());
        values.put(BRAND, vehicle.getBrand());
        values.put(MODEL, vehicle.getModel());
        values.put(YEAR_MANUFACTURED, vehicle.getYearManufactured());
        values.put(COLOR, vehicle.getColor());

        // insert row
        long vehicle_id = db.insert(TABLE_VEHICLE, null, values);

        return vehicle_id;
    }

    //Getting a vehicle
    public Vehicle getVehicle(String plate_number){
        Vehicle vce;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_VEHICLE +
                " WHERE " + PLATE_NUMBER + " = \"" + plate_number + ";";
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();
        vce = new Vehicle();
        vce.setId(c.getInt((c.getColumnIndex(KEY_ID))));
        vce.setPlateNumber(c.getString(c.getColumnIndex(PLATE_NUMBER)));
        vce.setBrand(c.getString(c.getColumnIndex(BRAND)));
        vce.setModel(c.getString(c.getColumnIndex(MODEL)));
        vce.setColor(c.getString(c.getColumnIndex(COLOR)));
        vce.setYearManufactured(c.getString(c.getColumnIndex(YEAR_MANUFACTURED)));

        c.close();

        return vce;
    }

    //Getting a vehicle
    public Vehicle getVehicleById(long vehicle_id){
        Vehicle vce;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_VEHICLE +
                " WHERE " + KEY_ID + " = " + vehicle_id;
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();
        vce = new Vehicle();
        vce.setId(c.getInt((c.getColumnIndex(KEY_ID))));
        vce.setPlateNumber(c.getString(c.getColumnIndex(PLATE_NUMBER)));
        vce.setBrand(c.getString(c.getColumnIndex(BRAND)));
        vce.setModel(c.getString(c.getColumnIndex(MODEL)));
        vce.setColor(c.getString(c.getColumnIndex(COLOR)));
        vce.setYearManufactured(c.getString(c.getColumnIndex(YEAR_MANUFACTURED)));

        c.close();

        return vce;
    }

    /**
     * Getting all vehicles
     * */

    public ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Vehicle vce = new Vehicle();
                vce.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                vce.setPlateNumber(c.getString(c.getColumnIndex(PLATE_NUMBER)));
                vce.setBrand(c.getString(c.getColumnIndex(BRAND)));
                vce.setModel(c.getString(c.getColumnIndex(MODEL)));
                vce.setColor(c.getString(c.getColumnIndex(COLOR)));
                vce.setYearManufactured(c.getString(c.getColumnIndex(YEAR_MANUFACTURED)));

                // adding to todo list
                vehicles.add(vce);
            } while (c.moveToNext());
        }
        c.close();
        return vehicles;
    }

// ------------------------ "Location" table methods ----------------//

//see http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/

    public boolean isLocationExist (int location_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_LOCATION +
                " WHERE " + KEY_ID + " = " + location_id;

        Cursor c = db.rawQuery(selectQuery, null);
        if(c.getCount() <= 0){
            c.close();
            return false;
        } else {
            c.close();
            return true;
        }
    }

    /**
     * Create a location of parking
     * @param location
     * @return location id
     */
    public long createLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LOCATION_NAME, location.getLocation_name());
        values.put(COST, location.getCost());

        // insert row
        long location_id = db.insert(TABLE_LOCATION, null, values);
        Log.d("Location ID", Long.toString(location_id));
        return location_id;
    }

    //Getting a vehicle
    public Location getLocation(long key_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_LOCATION +
                " WHERE " + KEY_ID + " = " + key_id;
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        Location loc = new Location();
        loc.setLocation_id(c.getInt((c.getColumnIndex(KEY_ID))));
        loc.setLocation_name(c.getString(c.getColumnIndex(LOCATION_NAME)));
        loc.setCost(c.getInt(c.getColumnIndex(COST)));

        c.close();
        return loc;

    }

    public ArrayList<Location> getAllLocations() {
        ArrayList<Location> locations = new ArrayList<Location>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Location location = new Location();
                location.setLocation_id(c.getInt((c.getColumnIndex(KEY_ID))));
                location.setLocation_name(c.getString(c.getColumnIndex(LOCATION_NAME)));
                location.setCost(c.getInt(c.getColumnIndex(COST)));

                // adding to todo list
                locations.add(location);
            } while (c.moveToNext());
        }
        c.close();
        return locations;
    }

}
