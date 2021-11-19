package com.example.myapplication2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication2.database.DbHelper;
import com.example.myapplication2.database.DbManager;

import java.util.ArrayList;

public class MyService extends Service {
    DbManager dbManager;
    final String LOG_TAG = "myLogs";

    MyBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Служба запущена",Toast.LENGTH_SHORT).show();
        createDB();
    }

    private DbManager createDB()
    {
        dbManager = new DbManager(getApplicationContext());
        dbManager.openDb();
        return dbManager;
    }

    public void closeDB()
    {
        dbManager.closeDb();
    }

    public ArrayList<Telephone> getTelephones(){
        return dbManager.getFromDb();
    }

    public void insertTelephone(String name, int price, boolean isAvailable){
        dbManager.insertToDb(name, price, isAvailable);
    }


    public IBinder onBind(Intent arg0) {
        Log.d(LOG_TAG, "MyService onBind");
        return binder;
    }

    class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

}