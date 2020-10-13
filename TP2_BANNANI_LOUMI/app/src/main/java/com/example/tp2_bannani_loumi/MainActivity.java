package com.example.tp2_bannani_loumi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;

public class MainActivity extends AppCompatActivity {
    public enum Ops {
        PLUS("+"),
        MOINS("-"),
        FOIS("*"),
        DIV("/");

        private String name = "";
        Ops(String name){this.name = name;}
        public String toString(){return name;}
    }
    private TextView screen;
    private float op1=0;
    private float op2=0;
    private Ops operator=null;
    private boolean isOp1=true;
    private int nbAfterCamma1=0;
    private float camma1;
    private int nbAfterCamma2=0;
    private float camma2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (TextView) findViewById(R.id.screen);
        Button btnEgal = (Button)findViewById(R.id.btnEgal);
        btnEgal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
            }
        });
        Button btnClear = (Button)findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });
    }

    private void updateDisplay() {
        float v=op1;
        if(!isOp1) {
            v=op2;
            screen.setText(String.format("%."+Integer.toString(nbAfterCamma2)+"f",v));
        }
        else screen.setText(String.format("%."+Integer.toString(nbAfterCamma1)+"f",v));


    }

    private int getMax(int nbAfterCamma1, int nbAfterCamma2) {
        if (nbAfterCamma1>nbAfterCamma2)
               return  nbAfterCamma1;
        return  nbAfterCamma2 ;
    }

    public void compute() {
        if(isOp1) {
            // do nothing
        } else {
            switch(operator) {
                case PLUS  : op1 = op1 + op2; break;
                case MOINS : op1 = op1 - op2; break;
                case FOIS  : op1 = op1 * op2; break;
                case DIV   : op1 = op1 / op2; break;
                default : return;
            }

            op2 = 0;
            nbAfterCamma1=getMax(nbAfterCamma1,nbAfterCamma2);
            camma1= (float) Math.pow(0.1,nbAfterCamma1);
            nbAfterCamma2=0;

            isOp1 = true;
            updateDisplay();
        }
    }

    private void clear() {
        op1 = 0;
        op2 = 0;
        nbAfterCamma1=0;
        nbAfterCamma2=0;
        operator = null;
        isOp1 = true;
        updateDisplay();
    }

    public void setOperator(View v) {
        nbAfterCamma2 = 0;

        switch (v.getId()) {
            case R.id.btnPlus  : operator=Ops.PLUS;  break;
            case R.id.btnMoins : operator=Ops.MOINS; break;
            case R.id.btnFois  : operator=Ops.FOIS;  break;
            case R.id.btnDiv   : operator=Ops.DIV;   break;
            default :
                Toast.makeText(this, "Opérateur non reconnu",Toast.LENGTH_LONG);

        }
        isOp1=false;
        updateDisplay();
    }
    public void addPoint(View v){
        if (isOp1) {
            if (nbAfterCamma1 == 0) {
                camma1 = (float) 0.1;
                nbAfterCamma1 = 1;
                updateDisplay();
            }

        }
        else {
            if (nbAfterCamma2 == 0) {
                camma2 = (float) 0.1;
                nbAfterCamma2 = 1;
                updateDisplay();
            }

        }

    }

    public void addNumber(View v){
        try {
            float val = Float.parseFloat(((Button)v).getText().toString());
            if (isOp1) {
                if(nbAfterCamma1>0) {
                    op1 = op1 + val * camma1;
                    camma1 = (float) (camma1 * 0.1);
                    nbAfterCamma1 = nbAfterCamma1 + 1;
                }
                else {
                    op1 = op1*10 + val;
                }
                updateDisplay();
            }
            else {
                if(nbAfterCamma2>0) {
                    op2 = op2 + val * camma2;
                    camma2 = (float) (camma2 * 0.1);
                    nbAfterCamma2 = nbAfterCamma2 + 1;
                }
                    else {
                    op2 = op2*10 + val;
                }

                updateDisplay();
            }

        }catch (NumberFormatException | ClassCastException e) {
            Toast.makeText(this, "Valeur erronée",Toast.LENGTH_LONG);
        }
    }
}