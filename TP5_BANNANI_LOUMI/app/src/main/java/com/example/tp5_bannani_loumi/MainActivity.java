package com.example.tp5_bannani_loumi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MyService myservice;
    Boolean mbound=false;
    private TextView screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (TextView) findViewById(R.id.screen);
    }

    public void startCount(View view) {
        bindService(new Intent(getBaseContext(),MyService.class),mConnection, getApplicationContext().BIND_AUTO_CREATE);
    }

    public void stopCount(View view) {
        if (mbound){
            stopService(new Intent(getBaseContext(),MyService.class));
            mbound=false;
        }
        else {
            Toast.makeText(this,"service does not created yet!!",Toast.LENGTH_LONG).show();
            screen.setText("le service n'est pas démarré!!!");
        }
    }
    public void getCount(View view) {
        if (mbound){
            long count=myservice.getrandom();
            screen.setText("count: "+Long.toString(count));

        }

    }
    private ServiceConnection mConnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            myservice = binder.getService();
            mbound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mbound = false;
        }
    };


}