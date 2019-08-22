package com.example.inclass11_801073413;

import java.util.ArrayList;

public class Location {
    ArrayList<Loc> points;
    String title;

    public Location(ArrayList<Loc> points, String title) {

        this.points = points;
        this.title = title;
    }

    public ArrayList<Loc> getListPoints(){
        return points;

    }


}
