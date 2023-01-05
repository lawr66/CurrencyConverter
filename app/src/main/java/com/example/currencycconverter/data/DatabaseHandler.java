package com.example.currencycconverter.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.window.SplashScreen;

import com.example.currencycconverter.model.Currency;
import com.example.currencycconverter.util.Util;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import currencyconverter.R;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }


    //We create our table
    @Override
    public void onCreate(SQLiteDatabase db) {
//SQL - Structured Query Language
/*
create table _name(id, name, phone_number);
*/
        Log.d("DatabaseHandler", "Create me");
        String CREATE_CURRENCY_TABLE = "CREATE TABLE IF NOT EXISTS " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT,"
                + Util.KEY_ABBREVIATION + " TEXT," + Util.KEY_BUY + " TEXT," + Util.KEY_SELL + " TEXT" + ")";
        db.execSQL(CREATE_CURRENCY_TABLE); //creating our table

        //db.execSQL(CREATE_CURRENCY_TABLE); //creating our table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});
        onCreate(db);
    }


    public void addCurrency(Currency currency) {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, currency.getName());
        values.put(Util.KEY_ABBREVIATION, currency.getAbbreviation());
        values.put(Util.KEY_BUY, currency.getBuy());
        values.put(Util.KEY_SELL, currency.getSell());
        Log.d("DatabaseHandler", "Try insert 2");
        db.insert(Util.TABLE_NAME, null, values);
        Log.d("DatabaseHandler", "Try insert 21");
        Log.d("DBHandler", "addCurrency: " + "item added");
        db.close();
    }

    public Currency getCurrency(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME,
                new String[]{Util.KEY_ID, Util.KEY_NAME, Util.KEY_ABBREVIATION, Util.KEY_BUY, Util.KEY_SELL},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Currency currency = new Currency(0, "Leu", "RON", "1", "1");
        currency.setId(Integer.parseInt(cursor.getString(0)));
        currency.setName(cursor.getString(1));
        currency.setAbbreviation(cursor.getString(2));
        currency.setBuy(cursor.getString(3));
        currency.setSell(cursor.getString(4));
        return currency;
    }



    //Get all Contacts
    public List<Currency> getAllCurrencies() {
        List<Currency> currencyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//Select all contacts
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);
//Loop through our data
        if (cursor.moveToFirst()) {
            do {
                Currency currency = new Currency(0, "Leu", "RON", "1", "1");
                currency.setId(Integer.parseInt(cursor.getString(0)));
                currency.setName(cursor.getString(1));
                currency.setAbbreviation(cursor.getString(2));
                currency.setBuy(cursor.getString(3));
                currency.setSell(cursor.getString(4));
//add contact objects to our list
                currencyList.add(currency);
            } while (cursor.moveToNext());
            System.out.println("Done");
        }
        return currencyList;
    }

    //Update contact
    public int updateCurrency(Currency currency) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, currency.getName());
        values.put(Util.KEY_ABBREVIATION, currency.getAbbreviation());
        values.put(Util.KEY_BUY, currency.getBuy());
        values.put(Util.KEY_SELL, currency.getSell());
//update the row
//update(tablename, values, where id = 43)
        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[]{String.valueOf(currency.getId())});
    }

    //Delete single contact
    public void deleteCurrency() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + Util.TABLE_NAME);
      /*  db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(currency.getId())});*/
        db.close();
    }

    //Get contacts count
    public int getCount() {
        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

   /* public ArrayList<String> getAllAbbr() {
        ArrayList<String> abbrList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("1111");
//Select all contacts
        String selectAll = "SELECT KEY_ABBREVIATION FROM " + Util.TABLE_NAME;
        System.out.println("2222");
        Cursor cursor = db.rawQuery(selectAll, null);
        System.out.println("3333");

//Loop through our data
        if (cursor.moveToFirst()) {
            do {
                Currency curr = new Currency(0, "Leu", "RON", "1", "1");
                String abrev = curr.getAbr(cursor.getString(2));
//add contact objects to our list
                System.out.println("5555");


            } while (cursor.moveToNext());
        }
        System.out.println("44444");

        return abbrList;


    }*/
}
