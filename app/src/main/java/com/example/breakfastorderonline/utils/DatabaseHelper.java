package com.example.breakfastorderonline.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "test.db";
    public static final String[] DB_TABLE_CREATE_SCRIPTS = new String[]{
            "PRAGMA foreign_keys = ON;",
            "CREATE TABLE IF NOT EXISTS `User` (" +
                    "`account`     VARCHAR(50)    NOT NULL," +
                    "`password`    VARCHAR(50)    NOt NULL," +
                    "`email`       VARCHAR(50)    NOT NULL," +
                    "PRIMARY KEY (`account`) " +
                    ");",
            "CREATE TABLE IF NOT EXISTS `Menu` (" +
                    "`name`            VARCHAR(255)   NOT NULL," +
                    "`price`           INTEGER        NOT NULL," +
                    "`has_icehot`      BOOLEAN        NOT NULL," +
                    "PRIMARY KEY (`name`) " +
                    ");",
            "CREATE TABLE IF NOT EXISTS `Cart` (" +
                    "`dish_name`     VARCHAR(255)     NOT NULL," +
                    "`count`         INTEGER          NOT NULL," +
                    "`icehot`        VARCHAR(10)," +
                    "`note`          TEXT," +
                    "PRIMARY KEY (`dish_name`, `count`, `icehot`)," +
                    "FOREIGN KEY (`dish_name`) REFERENCES `Menu`(`name`) " +
                    "ON UPDATE CASCADE  ON DELETE CASCADE " +
                    ");",
            "CREATE TABLE IF NOT EXISTS `Notification` (" +
                    "`time`            DATETIME        NOT NULL," +
                    "`order_id`        BIGINT          NOT NULL," +
                    "`title`           VARCHAR(255)    NOT NULL," +
                    "`content`         TEXT," +
                    "PRIMARY KEY (`time`, `order_id`)," +
                    "FOREIGN KEY (`order_id`) REFERENCES `Order`(`id`) " +
                    "ON UPDATE CASCADE  ON DELETE CASCADE " +
                    ");",
            "CREATE TABLE IF NOT EXISTS `Order` (" +
                    "`id`              BIGINT         NOT NULL," +
                    "`user_account`    VARCHAR(50)    NOT NULL," +
                    "`time1`           DATETIME       NOT NULL," +
                    "`time2`           DATETIME," +
                    "`note`            TEXT," +
                    "`state`           VARCHAR(20)    NOT NULL," +
                    "PRIMARY KEY (`id`)," +
                    "FOREIGN KEY (`user_account`) REFERENCES `User`(`account`) " +
                    "ON UPDATE CASCADE  ON DELETE CASCADE " +
                    ");",
            "CREATE TABLE IF NOT EXISTS `OrderDishes` (" +
                    "`_id`             INTEGER          AUTO INCREMENT," +
                    "`order_id`        BIGINT           NOT NULL," +
                    "`dish_name`       VARCHAR(255)     NOT NULL," +
                    "`count`           INTEGER          NOT NULL," +
                    "`icehot`          VARCHAR(10)," +
                    "`note`            TEXT," +
                    "PRIMARY KEY (`_id`)," +
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

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String dbTableScript : DB_TABLE_CREATE_SCRIPTS) {
            db.execSQL(dbTableScript);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String dbTableDropScript : DB_TABLE_DROP_SCRIPTS) {
            db.execSQL(dbTableDropScript);
        }
    }
}
