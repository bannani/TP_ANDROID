package com.example.android_bannani_mohamed_fares_loumi_mohamed.domain;

import android.location.Location;

import com.example.android_bannani_mohamed_fares_loumi_mohamed.repository.MySqlLiteDB;

import java.util.Date;

public class Rdv {

    private int idRdv;
    private String date;
    private double latitude,longitude;
    private String username;

    public Rdv(){
    }

    public Rdv(String date,double latitude, double longitude, String username) {
        this.date = date;
        this.username = username;
        this.latitude = latitude;
        this.longitude=longitude;
    }

    public int getIdRdv() {
        return idRdv;
    }

    public void setIdRdv(int idRdv) {
        this.idRdv = idRdv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
