package com.example.android_bannani_mohamed_fares_loumi_mohamed;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.android_bannani_mohamed_fares_loumi_mohamed.domain.User;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.repository.MySqlLiteDB;


public class HomeActivity extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    MySqlLiteDB dB;
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if( getApplicationContext().checkSelfPermission( Manifest.permission.READ_CONTACTS ) != PackageManager.PERMISSION_GRANTED )
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_PICK_CONTACTS);
        if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
            Log.d("permission", "permission denied to SEND_SMS - requesting it");
            String[] permissions = {Manifest.permission.SEND_SMS};
            requestPermissions(permissions, 1);
        }
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
