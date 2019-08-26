package com.example.hw3_grp3;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Comparator;

public class Results implements Serializable,Comparable<Results> {

    public String trackname;
    public String genre;
    public String artist;
    public String album;
    public String trackprice;
    public String albumprice;
    public String date;
    public String urltoimg;

    public Results() {
    }
   /*  public Results(String trackname, String genre, String artist, String album, String trackprice, String albumprice, String date, String urltoimg) {
        this.trackname = trackname;
        this.genre = genre;
        this.artist = artist;
        this.album = album;
        this.trackprice = trackprice;
        this.albumprice = albumprice;
        this.date = date;
        this.urltoimg = urltoimg;
    } */

    public String getTrackname() {
        return trackname;
    }

    public void setTrackname(String trackname) {
        this.trackname = trackname;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTrackprice() {
        return trackprice;
    }

    public void setTrackprice(String trackprice) {
        this.trackprice = trackprice;
    }

    public String getAlbumprice() {
        return albumprice;
    }

    public void setAlbumprice(String albumprice) {
        this.albumprice = albumprice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrltoimg() {
        return urltoimg;
    }

    public void setUrltoimg(String urltoimg) {
        this.urltoimg = urltoimg;
    }

    @Override
    public String toString() {
        return "Results{" +
                "trackname='" + trackname + '\'' +
                ", genre='" + genre + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", trackprice='" + trackprice + '\'' +
                ", albumprice='" + albumprice + '\'' +
                ", date='" + date + '\'' +
                ", urltoimg='" + urltoimg + '\'' +
                '}';
    }

    @Override
    public int compareTo(Results o) {
        return Comparators.DATE.compare(this, o);
    }

    public static class Comparators {

        public static Comparator<Results> DATE = new Comparator<Results>() {
            @Override
            public int compare(Results o1, Results o2) {

                try {
                    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
                    Date date1 = dateFormat.parse(o1.getDate());
                    Date date2 = dateFormat.parse(o2.getDate());
                    return date1.compareTo(date2);
                } catch (Exception e) {

                }
                return 0;
            }
        };
        public static Comparator<Results> PRICE = new Comparator<Results>() {
            @Override
            public int compare(Results o1, Results o2) {
                if (Double.parseDouble(o1.trackprice) < Double.parseDouble(o2.trackprice)) return -1;
                if (Double.parseDouble(o1.trackprice) > Double.parseDouble(o2.trackprice)) return 1;
                return 0;
            }
        };
    }
}
