package com.example.android_bannani_mohamed_fares_loumi_mohamed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LearnMoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_more);
    }

    public void close(View view) {
        startActivity(new Intent(LearnMoreActivity.this, HomeActivity.class));
    }
}
