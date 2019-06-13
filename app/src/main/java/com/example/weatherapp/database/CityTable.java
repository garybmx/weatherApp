package com.example.weatherapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CityTable {
    private final static String TABLE_NAME = "Weather";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_CITY = "city";
    private final static String COLUMN_UPDATE_DATE = "last_update";
    private final static String COLUMN_TEMPERATURE = "temperature";


    public static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE DATABASE_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "DESCRIPTION TEXT, "
                + "IMAGE_RESOURCE_ID INTEGER);");

        database.execSQL( "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CITY + " VARCHAR(100), " + COLUMN_UPDATE_DATE + " VARCHAR(100), " +  COLUMN_TEMPERATURE + " INEGER" + ");");
    }

    public static void onUpgrade(SQLiteDatabase database) {
       // database.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_TITLE
         //       + " TEXT DEFAULT 'Default title'");
        return;
    }

    public static void addCity(String city, Integer temperature, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY, city);
        values.put(COLUMN_TEMPERATURE, temperature);
        database.insert(TABLE_NAME, null, values);
    }

    public static void editCity(String city, Integer temp, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEMPERATURE, temp);

        database.update(TABLE_NAME, values, COLUMN_CITY + " = '" + city + "'", null);
        //database.update(TABLE_NAME, values, "%s = %s", new String[] {COLUMN_NOTE, String.valueOf(noteToEdit)});
        //database.execSQL("UPDATE " + TABLE_NAME + " set " + COLUMN_NOTE + " = 100 " + "WHERE "
         //       + COLUMN_NOTE + " = " + noteToEdit + ";");
    }

    public static void deleteCity(String city, SQLiteDatabase database) {
        database.delete(TABLE_NAME, COLUMN_CITY + " = " + city, null);
    }

    public static void deleteAll(SQLiteDatabase database) {
        database.delete(TABLE_NAME, null, null);
        //DELETE * FROM Notes
    }
    public static List<Integer> getCityInfo(SQLiteDatabase database) {
        Cursor cursor = database.query(TABLE_NAME, null, null, null,
                null, null, null);
        //Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return getResultFromCursor(cursor);
    }

    public static List<Integer> getLastTemperature(String city, SQLiteDatabase database) {
        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_CITY + " = '" + city + "'", null,
                null, null, null);
        //Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return getResultFromCursor(cursor);
    }

    private static List<Integer> getResultFromCursor(Cursor cursor) {
        List<Integer> result = null;

        if(cursor != null && cursor.moveToFirst()) {
            result = new ArrayList<>(cursor.getCount());

            int temperature = cursor.getColumnIndex(COLUMN_TEMPERATURE);
            do {
                result.add(cursor.getInt(temperature));
            } while (cursor.moveToNext());
        }

        try { cursor.close(); } catch (Exception ignored) {}
        return result == null ? new ArrayList<Integer>(0) : result;
    }
}
