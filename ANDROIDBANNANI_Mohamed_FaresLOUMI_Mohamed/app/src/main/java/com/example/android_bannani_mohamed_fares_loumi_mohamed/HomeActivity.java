package com.example.android_bannani_mohamed_fares_loumi_mohamed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_bannani_mohamed_fares_loumi_mohamed.domain.User;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.repository.MySqlLiteDB;


public class HomeActivity extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    MySqlLiteDB dB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void learnMore(View view) {
        startActivity(new Intent(HomeActivity.this, LearnMoreActivity.class));

    }

    public void signUp(View view) {
        startActivity(new Intent(HomeActivity.this, SignUpActivity.class));
    }

    public void logIn(View view) {
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        dB = new MySqlLiteDB(this);
        User user = dB.readUser(editTextUsername.getText().toString(), editTextPassword.getText().toString());
        if (user != null) {
            Intent intent = new Intent(this, MyAccountActivity.class);
            intent.putExtra("username", editTextUsername.getText().toString());
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(),"username or password is invalid !!",Toast.LENGTH_SHORT).show();
        }
    }
}
