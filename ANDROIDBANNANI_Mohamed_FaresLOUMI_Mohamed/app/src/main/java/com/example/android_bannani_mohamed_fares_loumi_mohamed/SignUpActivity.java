package com.example.android_bannani_mohamed_fares_loumi_mohamed;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_bannani_mohamed_fares_loumi_mohamed.repository.MySqlLiteDB;
import com.lamudi.phonefield.PhoneEditText;


import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    private EditText  editTextUsername, editTextPassword;
    MySqlLiteDB dB;
    private PhoneEditText phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dB = new MySqlLiteDB(this);
        phoneEditText = (PhoneEditText) findViewById(R.id.editTextPhoneNumber);
        phoneEditText.setDefaultCountry("FR");

    }

    public void register(View view) {
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        if("".equals(editTextUsername.getText().toString().trim())) {
            Toast.makeText(SignUpActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
        }
        else if("".equals(editTextPassword.getText().toString().trim())) {
            Toast.makeText(SignUpActivity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
        }
        else if (!phoneEditText.isValid()) {
            Toast.makeText(getApplicationContext(),"Invalid Phone Number !!",Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("sign up",phoneEditText.getPhoneNumber());
            dB.insertUser(editTextUsername.getText().toString().trim(), editTextPassword.getText().toString().trim(),phoneEditText.getPhoneNumber());
            Toast.makeText(SignUpActivity.this, "User Added successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
        }

    }

    public void logIn(View view) {
        finish();
        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
    }


}
