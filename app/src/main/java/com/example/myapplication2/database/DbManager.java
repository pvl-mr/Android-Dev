package com.example.myapplication2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication2.Telephone;

import java.util.ArrayList;

public class DbManager {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    public void insertToDb(String name, int price, boolean isAvailableTemp) {
        int isAvailable = (isAvailableTemp == true) ? 1 : 0;
        ContentValues cv = new ContentValues();
        cv.put(SettingConstants.NAME, name);
        cv.put(SettingConstants.PRICE, price);
        cv.put(SettingConstants.IS_AVAILABLE, isAvailable);
        db.insert(SettingConstants.TABLE_NAME, null, cv);
    }

    public void insertToDb(Telephone telephone) {
        int isAvailable = (telephone.isAvailable() == true) ? 1 : 0;
        ContentValues cv = new ContentValues();
        cv.put(SettingConstants.NAME, telephone.getName());
        cv.put(SettingConstants.PRICE, telephone.getPrice());
        cv.put(SettingConstants.IS_AVAILABLE, isAvailable);
        db.insert(SettingConstants.TABLE_NAME, null, cv);
    }

    public Telephone createModel(String name, int price, boolean isAvailableTemp) {
        Telephone tel = new Telephone();
        tel.setName(name);
        tel.setPrice(price);
        tel.setAvailable(isAvailableTemp);
        return tel;
    }
    public ArrayList<Telephone> getFromDb() {
        ArrayList<Telephone> tempList = new ArrayList<>();
        Cursor cursor = db.query(SettingConstants.TABLE_NAME, null, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(SettingConstants.NAME));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(SettingConstants.PRICE));
            boolean isAv = (cursor.getInt(cursor.getColumnIndexOrThrow(SettingConstants.IS_AVAILABLE)) == 0)?false:true;
            tempList.add(createModel(name, price, isAv));
        }
        cursor.close();
        return tempList;
    }

    public void beginTransaction() {
        db.beginTransaction();
    }

    public void endTransaction(){
        db.endTransaction();
    }

    public void closeDb() {
        dbHelper.close();
    }
}
