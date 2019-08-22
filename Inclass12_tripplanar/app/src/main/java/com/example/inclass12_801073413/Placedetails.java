package com.example.inclass12_801073413;

import java.io.Serializable;
import java.util.ArrayList;

public class Placedetails implements Serializable {

    String destcity,tripname,date;
    ArrayList<Trip> selplaces;

    public Placedetails() {
    }

    public String getDestcity() {
        return destcity;
    }

    public void setDestcity(String destcity) {
        this.destcity = destcity;
    }

    public String getTripname() {
        return tripname;
    }

    public void setTripname(String tripname) {
        this.tripname = tripname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Trip> getSelplaces() {
        return selplaces;
    }

    public void setSelplaces(ArrayList<Trip> selplaces) {
        this.selplaces = selplaces;
    }

    @Override
    public String toString() {
        return "Placedetails{" +
                "destcity='" + destcity + '\'' +
                ", tripname='" + tripname + '\'' +
                ", date='" + date + '\'' +
                ", selplaces=" + selplaces +
                '}';
    }
}
