package com.example.db_magaz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Magaz";
    public static final String TABLE_USERS = "User";
    public static final String TABLE_PROD = "Tovary";

    public static final String KEY_ID1 = "_id";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";

    public static final String KEY_ID = "_id";
    public static final String KEY_GRUP = "grup";
    public static final String KEY_NAZV = "nazv";
    public static final String KEY_CENA = "cena";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_USERS + "(" + KEY_ID1
                + " integer primary key," + KEY_LOGIN + " text," + KEY_PASSWORD + " text"+")");
        db.execSQL("create table " + TABLE_PROD + "(" + KEY_ID
                + " integer primary key," + KEY_GRUP + " text," + KEY_NAZV + " text,"+ KEY_CENA + " text"+")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_USERS);
        db.execSQL("drop table if exists " + TABLE_PROD);
        onCreate(db);
    }
}
