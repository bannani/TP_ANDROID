package com.example.tp3_bannani_loumi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Porte1 extends AppCompatActivity {
    int voiture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran1);
        Random r = new Random();
        voiture = r.nextInt(3) + 1;
    }

    public void about(View view) {
        Uri page = Uri.parse("https://fr.wikipedia.org/wiki/Probl%C3%A8me_de_Monty_Hall");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(page);
        startActivity(intent);
    }

    public void select(View view) {
        Button button = (Button) view;
        Intent intent = new Intent(this, Porte2.class);
        String choix = button.getText().toString();
        int choixPorte = 1;
        if (choix.equals("Porte 1")) {
            choixPorte = 1;
        } else if (choix.equals("Porte 2")) {
            choixPorte = 2;
        } else if (choix.equals("Porte 3")) {
            choixPorte = 3;
        }
        Random r = new Random();
        int choixPresentateur = r.nextInt(3) + 1;
        while (choixPresentateur == choixPorte || choixPresentateur == voiture) {
            choixPresentateur = r.nextInt(3) + 1;
        }
        intent.putExtra("voiture", voiture);
        intent.putExtra("choixPorte", choixPorte);
        intent.putExtra("choixPresentateur", choixPresentateur);
        startActivity(intent);
    }

}
