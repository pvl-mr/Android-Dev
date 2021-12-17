package com.example.myapplication2.database;

public class SettingConstants {
    public static final String TABLE_NAME = "telephone";
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String IS_AVAILABLE = "is_available";
    public static final String DB_NAME = "lab4_android.db";
    public static final int DB_VERSION = 5;
    public static final String  TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + "( " + _ID + " INTEGER PRIMARY KEY," + NAME +
            " TEXT," + PRICE + " INTEGER," + IS_AVAILABLE + " INTEGER)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
