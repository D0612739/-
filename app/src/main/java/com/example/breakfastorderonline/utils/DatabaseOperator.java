package com.example.breakfastorderonline.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseOperator {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public DatabaseOperator(Context context) {
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }

    public void close() {
        if (db.isOpen()) {
            db.close();
        }
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
