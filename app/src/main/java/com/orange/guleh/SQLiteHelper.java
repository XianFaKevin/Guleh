package com.orange.guleh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
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
    public static final String COL_MODEL = "model";
    public static final String COL_CODE = "code";
    public static final String COL_BRAND = "brand";
    public static final String COL_YEAR = "year";
    public static final String COL_PRICE = "price";
    public static final String COL_IMAGE = "image";

    // Database creation sql statement
    private static final String TABLE_CREATE_PRODUCTS = "create table "
            + TABLE_PRODUCTS + "(" + COL_CODE
            + " string primary key, " + COL_PRICE + " integer);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public ArrayList<String> listCodes(String brandname, String modelname, String year) {
        ArrayList<String> list = new ArrayList<String>();
        String SELECT_QUERY = "Select " + COL_CODE + " FROM " + TABLE_PRODUCTS
                + " WHERE " + COL_BRAND + " == '" + brandname + "' AND "
                + COL_MODEL + " == '" + modelname + "' AND " + COL_YEAR
                + " == '" + year + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public long add(Product pdt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_CODE, pdt.getCode());
        Double d = new Double(pdt.getPrice() * 100);
        values.put(COL_PRICE, d.intValue());

        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return result;
    }

    public Product get(String code) {
        Product pdt = new Product();
        String SELECT_QUERY = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COL_CODE + " == '"
                + code + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToFirst()) {
            pdt.setCode(cursor.getString(0));
            Double d = (double) cursor.getInt(1) / 100;
            pdt.setPrice(d);
            return pdt;
        }
        return null;
    }

    public void update(String code, String c, String p) {
        Double price = Double.parseDouble(p);
        price = price * 100;
        Log.d("price: ", String.valueOf(price));
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_CODE, c);
        values.put(COL_PRICE, price.intValue());

        db.update(TABLE_PRODUCTS, values, COL_CODE + "=?",
                new String[] {code});
    }

    public ArrayList<Product> list() {
        ArrayList<Product> list = new ArrayList<Product>();
        String SELECT_QUERY = "SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                Product pdt = new Product();
                pdt.setCode(cursor.getString(0));
                Double d = (double) cursor.getInt(1) / 100;
                pdt.setPrice(d);
                list.add(pdt);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void reset() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, null, null);
    }
}
