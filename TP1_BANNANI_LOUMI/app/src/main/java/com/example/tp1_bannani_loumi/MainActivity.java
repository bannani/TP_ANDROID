package com.example.tp1_bannani_loumi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean numeroValide;
    SmsManager sms = SmsManager.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendSMS(View view) {
        numeroValide = true;
        EditText editNumeros = (EditText) findViewById(R.id.editNumero);
        EditText editMessage = (EditText) findViewById(R.id.editMesage);
        String numeros = editNumeros.getText().toString();
        String message = editMessage.getText().toString();
        if (numeros.contains(":")){
            //si qq tape ":" au lieu de ";"
            Toast.makeText(getApplicationContext(), "Le numero " + numeros + " est invalide", Toast.LENGTH_SHORT).show();
        }
        else {
            if (message.length() > 0) {
                if (numeros.contains(";")) {
                    String[] tableau = numeros.split(";");
                    for (int i = 0; i < tableau.length; i++) {
                        if (tableau[i].length() < 4) {
                            Toast.makeText(getApplicationContext(), "Le numero " + tableau[i] + " est invalide", Toast.LENGTH_SHORT).show();
                            numeroValide = false;
                        }
                    }
                    if (numeroValide) {
                        for (int i = 0; i < tableau.length; i++) {
                            sms.sendTextMessage(tableau[i], null, message, null, null);
                        }
                    }
                } else if (numeros.length() >= 4) {
                    sms.sendTextMessage(numeros, null, message, null, null);
                } else {
                    Toast.makeText(getApplicationContext(), "Le numero " + numeros + " est invalide", Toast.LENGTH_SHORT).show();
                    numeroValide = false;
                }
                if (numeroValide) {
                    editNumeros.setText("");
                    editMessage.setText("");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Entrer le message", Toast.LENGTH_SHORT).show();
            }
        }
    }
}