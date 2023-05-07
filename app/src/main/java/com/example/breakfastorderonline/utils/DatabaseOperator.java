package com.example.breakfastorderonline.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.breakfastorderonline.utils.models.Order;
import com.example.breakfastorderonline.utils.models.User;

import java.util.ArrayList;

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

    public ArrayList<Order> findAllOrders(User user) {
        ArrayList<Order> orders = new ArrayList<>();
        // query orders with user account
        return orders;
    }
}
