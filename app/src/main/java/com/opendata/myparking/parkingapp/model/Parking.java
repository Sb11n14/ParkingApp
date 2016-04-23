package com.opendata.myparking.parkingapp.model;

/**
 * Created by is chan on 17/04/2016.
 */
public class Parking {
    private long parking_id;
    private String key_vehicle_id;
    private long key_location_id;
    private String time_in;
    private String time_out;
    private int active; //1 for true active, otherwise 0
    private Double charge;

    public Parking (){

    }

    public Parking (String time_in){
        this.time_in = time_in;
    }

    public void setKey_vehicle_id (String key_vehicle_id){
        this.key_vehicle_id = key_vehicle_id;
    }

    public void setKey_location_id (long key_location_id){
        this.key_location_id = key_location_id;
    }

    public void setParking_id (int parking_id){
        this.parking_id = parking_id;
    }

    public void setTime_in (String time_in){

        this.time_in = time_in;
    }

    public void setTime_out (String time_out){

        this.time_out = time_out;
    }

    public void setActive (int active){
        this.active = active;
    }

    public long getParking_id(){

        return this.parking_id;
    }

    public String getKey_vehicle_id(){
        return key_vehicle_id;
    }

    public long getKey_location_id(){
        return key_location_id;
    }

    public String getTime_in (){

        return this.time_in;
    }

    public String getTime_out (){

        return this.time_out;
    }

    public void setCharge (Double charge){
        this.charge = charge;
    }

    public Double getCharge (){

        return this.charge;

    }

    public int getActivation(){
        return active;
    }
}
