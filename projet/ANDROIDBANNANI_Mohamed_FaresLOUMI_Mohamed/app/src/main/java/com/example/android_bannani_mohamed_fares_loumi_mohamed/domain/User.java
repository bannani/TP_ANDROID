package com.example.android_bannani_mohamed_fares_loumi_mohamed.domain;

public class User {
    private int idUser;
    private String nameUser;
    private String passwordUser;
    private String phoneNumber;


    public User(int idUser, String nameUser, String passwordUser, String phoneNumber) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.passwordUser = passwordUser;
        this.phoneNumber = phoneNumber;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getPhoneNumbert() {
        return phoneNumber;
    }

    public void setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String toString() {
        return "idUser:" + idUser + " -> " + nameUser + " , " + phoneNumber;
    }


}