package com.example.breakfastorderonline.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.breakfastorderonline.utils.models.Cart;
import com.example.breakfastorderonline.utils.models.Menu;
import com.example.breakfastorderonline.utils.models.Order;
import com.example.breakfastorderonline.utils.models.User;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseOperator {

    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public DatabaseOperator(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
        this.db = databaseHelper.getWritableDatabase();
    }

    public void close() {
        if (db.isOpen()) {
            db.close();
        }
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }

    /**
     * 註冊的時候用來新增使用者資料
     */
    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put("account", user.getAccount());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        db.insert("User", null, values);
    }

    /**
     * 列出所有使用者資料
     */
    public ArrayList<User> findAllUsers() {
        // define data container and cursor
        ArrayList<User> users = new ArrayList<>();
        // define statement
        String statement = "SELECT `account`, `password`, `email` FROM `User`;";
        // execute query and get a cursor object
        // extract data from cursor
        Cursor cursor = db.rawQuery(statement, null);
        while (cursor.moveToNext()) {  // cursor的index從-1開始，第一次moveToNext之後來到0
            User user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            users.add(user);
        }
        cursor.close();  // close cursor after used
        return users;
    }

    /**
     * 用account來得到該使用者的資料
     */
    public User findOneUser(String userAccount) {
        String statement = "SELECT `account`, `password`, `email` FROM `User` WHERE `account`=?;";
        String[] arguments = new String[]{userAccount};
        Cursor cursor = db.rawQuery(statement, arguments);
        while (cursor.moveToNext()) {
            User user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }

    /**
     * 列出整個菜單
     */
    public ArrayList<Menu> findAllMenuDishes() {
        ArrayList<Menu> menuDishes = new ArrayList<>();
        String statement = "SELECT `name`, `price`, `has_icehot` FROM `Menu`;";
        Cursor cursor = db.rawQuery(statement, null);
        while (cursor.moveToNext()) {
            Menu menu = new Menu(cursor.getString(0), cursor.getInt(1), (cursor.getInt(2) > 0));
            menuDishes.add(menu);
        }
        cursor.close();
        return menuDishes;
    }

    /**
     * 新增數個菜單品項，可以用來初始化菜單資料
     */
    public void addMenuDishes(ArrayList<Menu> menuDishes) {
        for (Menu menuDish: menuDishes) {
            ContentValues values = new ContentValues();
            values.put("name", menuDish.getName());
            values.put("price", menuDish.getPrice());
            values.put("has_icehot", menuDish.isHas_icehot());
            db.insert("Menu", null, values);
        }
    }

    /**
     * 用菜單品項名稱得到該品項的資料
     */
    public Menu findOneMenuDish(String name) {
        String statement = "SELECT `name`, `price`, `has_icehot` FROM `Menu` WHERE `name`=?;";
        String[] arguments = new String[]{name};
        Cursor cursor = db.rawQuery(statement, arguments);
        while (cursor.moveToNext()) {
            Menu menu = new Menu(cursor.getString(0), cursor.getInt(1), (cursor.getInt(2) > 0));
            cursor.close();
            return menu;
        }
        cursor.close();
        return null;
    }

    /**
     * 列出該使用者的所有訂單(歷史訂單)
     */
    public ArrayList<Order> findAllOrders(String userAccount) {
        ArrayList<Order> orders = new ArrayList<>();
        String statement = "SELECT `account`, `password`, `email`, " +
            "`id`, `time1`, `time2`, `note`, `state` FROM `Order`, `User` WHERE `account`=?;";
        String[] arguments = new String[]{userAccount};
        Cursor cursor = db.rawQuery(statement, arguments);
        while (cursor.moveToNext()) {
            Order order = new Order(
                    cursor.getLong(3),
                    new User(cursor.getString(0), cursor.getString(1), cursor.getString(2)),
                    new Date(cursor.getLong(4)),
                    new Date(cursor.getLong(5)),
                    cursor.getString(6),
                    cursor.getString(7),
                    getTotalPriceOfOrder(cursor.getLong(3))  // total price (Call getTotalPriceOfOrder())
                );
            orders.add(order);
        }
        cursor.close();
        return orders;
    }

    /**
     * 計算該訂單的總價錢
     */
    public int getTotalPriceOfOrder(long orderId) {
        String statement = "SELECT `price`, `count` FROM `Menu`, `OrderDishes` " +
            "WHERE `order_id`=? AND `Menu`.`name`=`OrderDishes`.`dish_name`;";
        String[] arguments = new String[]{String.valueOf(orderId)};
        Cursor cursor = db.rawQuery(statement, arguments);
        int curCount;
        int curPrice;
        int totalPrice = 0;
        while (cursor.moveToNext()) {
            curPrice = cursor.getInt(0);
            curCount = cursor.getInt(1);
            totalPrice += curPrice * curCount;
        }
        cursor.close();
        return totalPrice;
    }

    /**
     * 新增餐點到購物車
     */
    public void addDishToCart(Cart cart) {
        ContentValues values = new ContentValues();
        values.put("dish_name", cart.getMenuDish().getName());
        values.put("count", cart.getCount());
        values.put("icehot", cart.getIcehot());
        values.put("note", cart.getNote());
        db.insert("Cart", null, values);
    }

    /**
     * 清空購物車，在建立訂單資料後會清空購物車，帳號登出後也會清空購物車(購物車在裝置上共用，不綁定帳號)
     */
    public void clearCart() {
        db.delete("Cart", null, null);
    }

    /**
     * 取得當前購物車列表
     */
    public ArrayList<Cart> findAllCartItems() {
        ArrayList<Cart> cartItems = new ArrayList<>();
        String statement = "SELECT `name`, `price`, `has_icehot`, " +
            "`count`, `icehot`, `note` FROM `Menu`, `Cart` WHERE `Menu`.`name`=`Cart`.`dish_name`;";
        Cursor cursor = db.rawQuery(statement, null);
        while (cursor.moveToNext()) {
            Cart cartItem = new Cart(
                new Menu(cursor.getString(0), cursor.getInt(1), (cursor.getInt(2) > 0)),
                cursor.getInt(3),
                cursor.getString(4),
                cursor.getString(5)
            );
            cartItems.add(cartItem);
        }
        cursor.close();
        return cartItems;
    }
}
