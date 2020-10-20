package com.example.tp3_bannani_loumi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Resultat extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran3);
        Bundle extras = getIntent().getExtras();
        String resultat = extras.getString("resultat");
        TextView view = (TextView) findViewById(R.id.resultat);
        view.setText(resultat);
        ImageView imageResult = (ImageView) findViewById(R.id.imageResulat);
        if (resultat.equals("Vous avez Gagné")) {
            imageResult.setBackgroundResource(R.drawable.voiture);
        } else {
            imageResult.setBackgroundResource(R.drawable.chevre);
        }
    }

    public void restart(View view) {
        Intent intent = new Intent(this, Porte1.class);
        startActivity(intent);
        finish();
    }
}