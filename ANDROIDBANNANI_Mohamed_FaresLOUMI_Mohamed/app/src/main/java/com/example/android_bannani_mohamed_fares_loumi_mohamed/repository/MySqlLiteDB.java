package com.example.android_bannani_mohamed_fares_loumi_mohamed.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android_bannani_mohamed_fares_loumi_mohamed.domain.Rdv;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.domain.User;

import java.util.ArrayList;
import java.util.List;

public class MySqlLiteDB extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME = "RDV.db";
    private static final int DATA_BASE_VERSION = 1;

    public MySqlLiteDB(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String userSQL = " create table IF NOT EXISTS User ("
                + " idUser integer primary key autoincrement,"
                + " nameUser text not null,"
                + " password text not null,"
                + " phoneNumber text not null)";
        String rdvSQL = " create table IF NOT EXISTS Rdv ("
                + " idRdv integer primary key autoincrement,"
                + " date text,"
                + " latitude float,"
                + " longitude float,"
                + " username text)";
        sqLiteDatabase.execSQL(userSQL);
        sqLiteDatabase.execSQL(rdvSQL);
        Log.i("DATABASE", "oncreate invoked");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertUser(String name, String password, String phoneNumber) {
        String sqlStr = "insert into User (nameUser,password,phoneNumber) values('" + name + "','" + password + "','" + phoneNumber + "')";
        this.getWritableDatabase().execSQL(sqlStr);
        Log.i("DATABASE", "Inserted RDV");
    }

    public void insertRdv(Rdv rdv) {
        String sqlStr = "insert into Rdv (date,latitude,longitude,username) " + "values('" + rdv.getDate() + "','" + rdv.getLatitude() +"','" + rdv.getLongitude()+  "','" + rdv.getUsername() +"')";
        this.getWritableDatabase().execSQL(sqlStr);
        Log.i("DATABASE", "Inserted Session");
    }

    public List<User> readUsers() {
        List<User> users = new ArrayList<User>();
        String sql = "select * from User";
        Cursor curseur = this.getReadableDatabase().rawQuery(sql, null);
        curseur.moveToFirst();
        while (!curseur.isAfterLast()) {
            User user = new User(curseur.getInt(0), curseur.getString(1), curseur.getString(2), curseur.getString(3));
            users.add(user);
            curseur.moveToNext();
        }
        curseur.close();
        return users;
    }


    public List<Rdv> readALLRdv() {
        List<Rdv> rdvs = new ArrayList<Rdv>();
        String sql = "select * from Rdv";
        Cursor curseur = this.getReadableDatabase().rawQuery(sql, null);
        curseur.moveToFirst();
        while (!curseur.isAfterLast()) {
            if(curseur.getString(1)!=null && curseur.getString(2)!=null && curseur.getString(3)!=null ){
                Rdv rdv = new Rdv(curseur.getString(1), Float.valueOf(curseur.getString(2)),Float.valueOf(curseur.getString(3)), curseur.getString(4));
                rdvs.add(rdv);
            }
            curseur.moveToNext();
        }
        curseur.close();
        return rdvs;
    }

    public User readUser(String nameUser, String password) {
        User user = null;
        String sql = "select * from User where nameUser=" + "'" + nameUser + "'" + "AND password =" + "'" + password + "'";
        Cursor curseur = this.getReadableDatabase().rawQuery(sql, null);
        curseur.moveToFirst();
        if (curseur.getCount() > 0) {
            user = new User(curseur.getInt(0), curseur.getString(1), curseur.getString(2), curseur.getString(3));
            curseur.close();
        }

        return user;
    }

}
