package com.example.tp5_bannani_loumi;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    private final IBinder mbinder=new LocalBinder();
    public long startTime=0;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"service is screated",Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        this.startTime = System.currentTimeMillis();
        Toast.makeText(this,"onBind",Toast.LENGTH_LONG).show();
        return mbinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this,"onUnBind",Toast.LENGTH_LONG).show();
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"service started",Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onRebind(Intent intent) {
        Toast.makeText(this,"onReBind",Toast.LENGTH_LONG).show();
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"service destroyed",Toast.LENGTH_LONG).show();
    }

    public class LocalBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }

    }
    public long getrandom(){
        //Toast.makeText(this,"service destroyed",Toast.LENGTH_LONG).show();
        long endTime = System.currentTimeMillis();
        return  ( endTime-this.startTime)/1000;
    }
}