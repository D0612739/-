package com.example.breakfastorderonline.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.breakfastorderonline.utils.models.Menu;
import com.example.breakfastorderonline.utils.models.Order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "bfoo_app_db.db";
    public static final String[] DB_TABLE_CREATE_SCRIPTS = new String[]{
            "PRAGMA foreign_keys = ON;",
            "CREATE TABLE IF NOT EXISTS `User` (" +
                    "`account`     VARCHAR(50)    NOT NULL," +
                    "`password`    VARCHAR(50)    NOt NULL," +
                    "`email`       VARCHAR(50)    NOT NULL," +
                    "PRIMARY KEY (`account`), " +
                    "UNIQUE(`email`) " +
                    ");",
            "CREATE TABLE IF NOT EXISTS `Menu` (" +
                    "`name`            VARCHAR(255)   NOT NULL," +
                    "`price`           INTEGER        NOT NULL," +
                    "PRIMARY KEY (`name`) " +
                    ");",
            "CREATE TABLE IF NOT EXISTS `Cart` (" +
                    "`user_account`  VARCHAR(50)      NOT NULL," +
                    "`dish_name`     VARCHAR(255)     NOT NULL," +
                    "`count`         INTEGER          NOT NULL," +
                    "`note`          TEXT," +
                    "PRIMARY KEY (`user_account`, `dish_name`)," +
                    "FOREIGN KEY (`user_account`) REFERENCES `User`(`account`) " +
                    "ON UPDATE CASCADE  ON DELETE CASCADE," +
                    "FOREIGN KEY (`dish_name`) REFERENCES `Menu`(`name`) " +
                    "ON UPDATE CASCADE  ON DELETE CASCADE " +
                    ");",
            "CREATE TABLE IF NOT EXISTS `Notification` (" +
                    "`time`            DATETIME        NOT NULL," +
                    "`order_id`        BIGINT          NOT NULL," +
                    "`title`           VARCHAR(255)    NOT NULL," +
                    "`content`         TEXT," +
                    "`user_read`       BOOLEAN         NOT NULL," +
                    "PRIMARY KEY (`time`, `order_id`)," +
                    "FOREIGN KEY (`order_id`) REFERENCES `Order`(`id`) " +
                    "ON UPDATE CASCADE  ON DELETE CASCADE " +
                    ");",
            "CREATE TABLE IF NOT EXISTS `Order` (" +
                    "`id`              VARCHAR(36)    NOT NULL," +
                    "`user_account`    VARCHAR(50)    NOT NULL," +
                    "`time1`           DATETIME       NOT NULL," +
                    "`time2`           DATETIME," +
                    "`note`            TEXT," +
                    "`state`           VARCHAR(20)    NOT NULL," +  // state("MAKING", "WAITING_TAKING", "COMPLETE")
                    "PRIMARY KEY (`id`)," +
                    "FOREIGN KEY (`user_account`) REFERENCES `User`(`account`) " +
                    "ON UPDATE CASCADE  ON DELETE CASCADE " +
                    ");",
            "CREATE TABLE IF NOT EXISTS `OrderDishes` (" +
                    "`order_id`        VARCHAR(36)      NOT NULL," +
                    "`dish_name`       VARCHAR(255)     NOT NULL," +
                    "`count`           INTEGER          NOT NULL," +
                    "`note`            TEXT," +
                    "PRIMARY KEY (`order_id`, `dish_name`)," +
                    "FOREIGN KEY (`order_id`) REFERENCES `Order`(`id`) " +
                    "ON UPDATE CASCADE  ON DELETE CASCADE," +
                    "FOREIGN KEY (`dish_name`) REFERENCES `Menu`(`name`) " +
                    "ON UPDATE CASCADE  ON DELETE CASCADE " +
                    ");"
    };
    public static final String[] DB_TABLE_DROP_SCRIPTS = new String[]{
            "DROP TABLE IF EXISTS `User`;",
            "DROP TABLE IF EXISTS `Menu`;",
            "DROP TABLE IF EXISTS `Cart`;",
            "DROP TABLE IF EXISTS `Notification`;",
            "DROP TABLE IF EXISTS `Order`;",
            "DROP TABLE IF EXISTS `OrderDishes`;"
    };

    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String dbTableScript : DB_TABLE_CREATE_SCRIPTS) {
            db.execSQL(dbTableScript);
        }
        // insert test data
        prepareData(db);
        // insert demo data
        prepareDemoData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String dbTableDropScript : DB_TABLE_DROP_SCRIPTS) {
            db.execSQL(dbTableDropScript);
        }
    }

    private void prepareData(SQLiteDatabase db) {
        // User
        ContentValues userValues = new ContentValues();
        userValues.put("account", "admin");
        userValues.put("password", "admin");
        userValues.put("email", "admin@gmail.com");
        db.insert("`User`", null, userValues);

        // Menu
        String[] dishes = new String[]{
                "煎蛋吐司", "起司吐司", "培根吐司", "豬肉吐司", "燻雞吐司", "牛肉吐司", "鮪魚吐司", "香雞吐司",
                "煎蛋漢堡", "起司漢堡", "培根漢堡", "豬肉漢堡", "燻雞漢堡", "牛肉漢堡", "鮪魚漢堡", "香雞漢堡",
                "原味蛋餅", "起司蛋餅", "培根蛋餅", "豬肉蛋餅", "燻雞蛋餅", "玉米蛋餅", "肉鬆蛋餅",
                "豆漿", "紅茶", "奶茶", "綠茶", "美式咖啡"
        };
        int[] prices = new int[]{
                30, 30, 30, 35, 35, 35, 35, 35,
                30, 30, 30, 35, 35, 35, 35, 35,
                25, 35, 30, 35, 35, 35, 35,
                20, 20, 25, 20, 35
        };
        for (int i = 0; i < dishes.length; i++) {
            ContentValues dishValues = new ContentValues();
            dishValues.put("name", dishes[i]);
            dishValues.put("price", prices[i]);
            db.insert("`Menu`", null, dishValues);
        }

        // Order
        ContentValues orderValues = new ContentValues();
        String orderId = UUID.randomUUID().toString();
        orderValues.put("id", orderId);
        orderValues.put("user_account", "admin");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 4, 1, 7, 30);
        orderValues.put("time1", calendar.getTime().getTime());
        calendar.set(2023, 4, 1, 7, 50);
        orderValues.put("time2", calendar.getTime().getTime());
        orderValues.put("state", "COMPLETE");
        // Log的用法
        try {
            db.insertOrThrow("`Order`", null, orderValues);
        } catch (SQLException e) {
            Log.e("MyTag", e.toString());
        }

        // Notification
        ContentValues notificationValues = new ContentValues();
        notificationValues.put("time", calendar.getTime().getTime());  // use the time2 of Order
        notificationValues.put("order_id", orderId);  // use the id of Order
        notificationValues.put("title", "餐點已取餐。");
        notificationValues.put("content", "您的餐點已取餐，祝您有美好的一天。");
        notificationValues.put("user_read", 0);
        db.insert("`Notification`", null, notificationValues);

        // OrderDishes
        String[] dishNames = new String[]{"燻雞漢堡", "奶茶"};
        for (String dishName: dishNames) {
            ContentValues orderDishValues = new ContentValues();
            orderDishValues.put("order_id", orderId);
            orderDishValues.put("dish_name", dishName);
            orderDishValues.put("count", 1);
            db.insert("`OrderDishes`", null, orderDishValues);
        }

        // Cart
        String[] cartDishNames = new String[]{"培根漢堡", "起司蛋餅", "奶茶"};
        for (String dishName: cartDishNames) {
            ContentValues cartValues = new ContentValues();
            cartValues.put("user_account", "admin");
            cartValues.put("dish_name", dishName);
            cartValues.put("count", 1);
            db.insert("`Cart`", null, cartValues);
        }
    }

    private void prepareDemoData(SQLiteDatabase db) {
        // Demo user =================================================================
        ContentValues userValues = new ContentValues();
        String userName = "tony";
        userValues.put("account", userName);
        userValues.put("password", userName);
        userValues.put("email", "tony0705@gmail.com");
        db.insert("`User`", null, userValues);

        // Demo order (need total 3 orders)
        String[] orderIdList = new String[]{
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        };
        Calendar[] calendarList = new Calendar[]{
            Calendar.getInstance(),
            Calendar.getInstance(),
            Calendar.getInstance()
        };
        calendarList[0].set(2023, 5, 1, 7, 0);
        calendarList[1].set(2023, 5, 2, 6, 40);
        calendarList[2].set(2023, 5, 3, 7, 20);
        String[] orderStateList = new String[]{
            "MAKING",
            "WAITING_TAKING",
            "COMPLETE"
        };

        // Insert demo order
        for (int i = 0; i < 3; i++) {
            ContentValues orderValues = new ContentValues();
            orderValues.put("id", orderIdList[i]);
            orderValues.put("user_account", userName);
            orderValues.put("time1", calendarList[i].getTime().getTime());
            calendarList[i].add(Calendar.MINUTE, 20);
            orderValues.put("time2", calendarList[i].getTime().getTime());
            orderValues.put("state", orderStateList[i]);
            try {
                db.insertOrThrow("`Order`", null, orderValues);
            } catch (SQLException e) {
                Log.e("MyTag", e.toString());
            }
        }

        // Demo orderDishes
        String[][] orderDishesList = new String[][]{
            {"燻雞漢堡", "起司蛋餅", "奶茶"},
            {"豬肉蛋餅", "燻雞蛋餅", "鮪魚吐司", "奶茶"},
            {"培根漢堡", "玉米蛋餅"}
        };
        int[][] orderDishesCountsList = new int[][]{
            {1, 1, 1},
            {2, 1, 3, 3},
            {1, 3}
        };

        // Insert demo orderDishes
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < orderDishesList[r].length; c++) {
                ContentValues orderDishValues = new ContentValues();
                orderDishValues.put("order_id", orderIdList[r]);
                orderDishValues.put("dish_name", orderDishesList[r][c]);
                orderDishValues.put("count", orderDishesCountsList[r][c]);
                db.insert("`OrderDishes`", null, orderDishValues);
            }
        }
    }
}
