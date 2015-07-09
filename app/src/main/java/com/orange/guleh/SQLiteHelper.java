package com.orange.guleh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Kevin on 27/6/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "carDB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_PRODUCTS = "products";
    public static final String TABLE_MODELS = "models";
    public static final String COL_ID = "id";
    public static final String COL_MODEL = "model";
    public static final String COL_CODE = "code";
    public static final String COL_BRAND = "brand";
    public static final String COL_PRODUCT = "product";
    public static final String COL_PRICE = "price";
    public static final String COL_IMAGE = "image";

    // Database creation sql statement
    private static final String TABLE_CREATE_PRODUCTS = "create table "
            + TABLE_PRODUCTS + "(" + COL_ID
            + " integer primary key autoincrement, " + COL_BRAND
            + " string, " + COL_CODE + " string, " + COL_PRODUCT + " string, "
            + COL_PRICE + " integer, " + COL_IMAGE + " blob);";

    private static final String TABLE_CREATE_MODELS = "create table "
            + TABLE_MODELS + "(" + COL_ID
            + " integer primary key autoincrement, " + COL_MODEL
            + " string, " + COL_CODE + " string);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_PRODUCTS);
        db.execSQL(TABLE_CREATE_MODELS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODELS);
        onCreate(db);
    }
}
