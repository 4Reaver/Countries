package com.example.reaver.countries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by Reaver on 08.08.2014.
 */
public class DataBase {
    static final int DB_VERSION = 1;
    static final String DB_NAME = "db_countries";
    static final String TABLE_NAME = "country";
    static final String COLUMN_NAME = "country_name";
    static final String COLUMN_AREA = "country_area";
    static final String COLUMN_POPULATION = "country_population";

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DataBase(Context context) {
        this.context = context;
    }

    public void open() {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        if ( db != null ) {
            db.close();
        }
    }

    public Cursor getAllData() {
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public void addCountry(Country country) {
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, country.getName());
        cv.put(COLUMN_AREA, country.getArea());
        cv.put(COLUMN_POPULATION, country.getPopulation());

        db.insert(TABLE_NAME, null, cv);
    }

    public void deleteCountry(Country country) {
        db.delete(TABLE_NAME, COLUMN_NAME + " = ?", new String[] {country.getName()});
    }

    private void fillDatabase(SQLiteDatabase db) {
        try {
            InputStream is = context.getAssets().open("names_s_a.txt");
            Scanner scanner = new Scanner(is).useLocale(Locale.US);
            ContentValues cv = new ContentValues();

            while ( scanner.hasNextLine() ) {
                cv.put(COLUMN_NAME, scanner.nextLine());
                cv.put(COLUMN_AREA, scanner.nextInt());
                cv.put(COLUMN_POPULATION, scanner.nextInt());
                db.insert(TABLE_NAME, null, cv);
                scanner.nextLine();
            }
        } catch (IOException e) {
            Log.d(MainActivity.LOG_TAG, "File \"namesandsquares\" not found");
        }
    }

    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(MainActivity.LOG_TAG, "--- Creating database ---");

            db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                    + "country_id INTEGER PRIMARY KEY AUTOINCREMENT"
                    + ", " + COLUMN_NAME + " TEXT"
                    + ", " + COLUMN_AREA + " INTEGER"
                    + ", " + COLUMN_POPULATION + " INTEGER);");

            fillDatabase(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
