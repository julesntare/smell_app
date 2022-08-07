package com.example.smell.smell_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBHandler {
    private DBHelper dbHelper;
    private final Context context;
    private SQLiteDatabase database;

    public DBHandler(Context c) {
        context = c;
    }

    public void open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(String name, String desc, boolean sense) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.TITLE, name);
        contentValue.put(DBHelper.DESCRIPTION, desc);
        contentValue.put(DBHelper.SENSE, sense);
        return database.insert(DBHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor getSmellTypes() {
        String[] columns = new String[]{DBHelper._ID, DBHelper.TITLE, DBHelper.DESCRIPTION, DBHelper.SENSE};
        Cursor cursor = database.query(DBHelper.TABLE_NAME, columns, null, null, null, null, "smell_id DESC");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}