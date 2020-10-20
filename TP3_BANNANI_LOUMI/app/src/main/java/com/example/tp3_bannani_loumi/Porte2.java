package com.example.tp3_bannani_loumi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Porte2 extends AppCompatActivity {
    int voiture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran2);

        Bundle extras = getIntent().getExtras();
        voiture = extras.getInt("voiture");
        int choixPorte = extras.getInt("choixPorte");
        int choixPresentateur = extras.getInt("choixPresentateur");

        String buttonID1 = "porte" + choixPresentateur;

        int resID1 = getResources().getIdentifier(buttonID1, "id", getPackageName());
        Button portePresentateur = (Button) findViewById(resID1);
        portePresentateur.setBackgroundResource(R.drawable.chevre);
        portePresentateur.setClickable(false);
        String buttonID2 = "porte" + choixPorte;

        int resID2 = getResources().getIdentifier(buttonID2, "id", getPackageName());
        Button porteJoueur = (Button) findViewById(resID2);
        porteJoueur.setBackgroundResource(R.drawable.border);
    }

    public void select(View view) {
        Button button = (Button) view;
        Intent intent = new Intent(this, Resultat.class);
        String choix = button.getText().toString();
        int choixPorte;
        if (choix.equals("Porte1")) {
            choixPorte = 1;
        } else if (choix.equals("Porte2")) {
            choixPorte = 2;
        } else {
            choixPorte = 3;
        }
        String resultat;
        if (choixPorte == voiture) {
            resultat = "Vous avez Gagné";
        } else {
            resultat = "Vous avez Perdu";
        }
        intent.putExtra("resultat", resultat);
        startActivity(intent);
    }


}
