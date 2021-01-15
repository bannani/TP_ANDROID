package com.example.android_bannani_mohamed_fares_loumi_mohamed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_bannani_mohamed_fares_loumi_mohamed.domain.Rdv;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.repository.MySqlLiteDB;
import com.lamudi.phonefield.PhoneEditText;

import java.util.Calendar;

public class Accept_Refuse_RDV extends AppCompatActivity {
    private PhoneEditText phoneEditText;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText editTextDate;
    private EditText laltitude;
    private EditText longitude;
    String name;
    SmsManager sms = SmsManager.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept__refuse__rdv);
        phoneEditText = (PhoneEditText) findViewById(R.id.editTextPhoneNumber);
        phoneEditText.setDefaultCountry("FR");
        editTextDate = findViewById(R.id.editTextDate);
        laltitude = findViewById(R.id.idTxtLatitude);
        longitude = findViewById(R.id.idLongitude);
        name = getIntent().getExtras().getString("username");
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                editTextDate.setText(date);
            }
        };
    }

    public void onClickAccept(View view){
        if (!phoneEditText.isValid()) {
            Toast.makeText(this,"Invalid Phone Number !!",Toast.LENGTH_SHORT).show();
        }
        else{
            Rdv x=new Rdv(editTextDate.getText().toString(),Double.parseDouble(laltitude.getText().toString()),Double.parseDouble(longitude.getText().toString()),phoneEditText.getPhoneNumber());
            editTextDate.getText().toString();
            sms.sendTextMessage("5556", null,  " hi there, "+name+" accept the RDV !"  , null, null);
            (new MySqlLiteDB(this)).insertRdv(x);
            Toast.makeText(this,"Message send",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MyAccountActivity.class);
            i.putExtra("username", name);
            startActivity(i);
        }

    }

    public void onClickRefuse(View view){
        if (!phoneEditText.isValid()) {
            Toast.makeText(this,"Invalid Phone Number !!",Toast.LENGTH_SHORT).show();
        }
        else{
            Rdv x=new Rdv(editTextDate.getText().toString(),Double.parseDouble(laltitude.getText().toString()),Double.parseDouble(longitude.getText().toString()),phoneEditText.getPhoneNumber());
            editTextDate.getText().toString();
            sms.sendTextMessage(phoneEditText.getPhoneNumber(), null,  " hi there, "+name+" refuse the RDV !"  , null, null);
            Toast.makeText(this,"Message send",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MyAccountActivity.class);
            i.putExtra("username", name);
            startActivity(i);
        }

    }

    public void selectDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}