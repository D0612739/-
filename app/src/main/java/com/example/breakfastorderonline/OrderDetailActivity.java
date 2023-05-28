package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breakfastorderonline.adapters.OrderDetailMenuDishesListAdapter;
import com.example.breakfastorderonline.adapters.OrderDetailStateListAdapter;
import com.example.breakfastorderonline.utils.DatabaseOperator;
import com.example.breakfastorderonline.utils.SharedPreferencesOperator;
import com.example.breakfastorderonline.utils.models.Order;
import com.example.breakfastorderonline.utils.models.OrderDishes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView orderIdText;
    private TextView orderTimeText1;
    private TextView orderTimeText2;
    private TextView orderTotalPriceText;
    private TextView orderNoteText;
    private ListView orderMenuDishesListView;
    private ListView orderStateListView;

    private SharedPreferencesOperator pref;
    private DatabaseOperator db;

    private Order orderObj;
    private ArrayList<OrderDishes> orderDishesList;
    private final String[] orderStateTitles = new String[]{"製作中", "待取餐", "已取餐"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        pref = new SharedPreferencesOperator(OrderDetailActivity.this);
        db = new DatabaseOperator(OrderDetailActivity.this);

        // get order object from OrderHistoryActivity
        orderObj = (Order) getIntent().getSerializableExtra("order_object");
        String curUserAccount = pref.getSignedInUserAccount();
        if (curUserAccount.isEmpty()) {
            backToSignInPage();
        }
        orderDishesList = db.findAllOrderDishesOfOrder(orderObj);

        // get views
        orderIdText = findViewById(R.id.orderdetail_id_text);
        orderTimeText1 = findViewById(R.id.orderdetail_time_1_text);
        orderTimeText2 = findViewById(R.id.orderdetail_time_2_text);
        orderTotalPriceText = findViewById(R.id.orderdetail_totalprice_text);
        orderNoteText = findViewById(R.id.orderdetail_note_text);
        orderMenuDishesListView = findViewById(R.id.orderdetail_menudishes_listview);
        orderStateListView = findViewById(R.id.orderdetail_state_listview);

        // declare adapters
        OrderDetailMenuDishesListAdapter orderDetailMenuDishesListAdapter = new OrderDetailMenuDishesListAdapter(
                orderDishesList, OrderDetailActivity.this
        );
        OrderDetailStateListAdapter orderDetailStateAdapter = new OrderDetailStateListAdapter(
                OrderDetailActivity.this, orderStateTitles, orderObj
        );

        // set adapters
        orderMenuDishesListView.setAdapter(orderDetailMenuDishesListAdapter);
        orderStateListView.setAdapter(orderDetailStateAdapter);

        // set other texts
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        orderIdText.setText(orderObj.getId());
        orderTimeText1.setText(df.format(orderObj.getTime1()) + " 下單");
        orderTimeText2.setText(df.format(orderObj.getTime2()) + " 完成");
        orderTotalPriceText.setText("總金額    " + "$" + orderObj.getTotalPrice());
        orderNoteText.setText(orderObj.getNote());
    }

    private void backToSignInPage() {
        Toast.makeText(OrderDetailActivity.this, "Please sign in again", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(OrderDetailActivity.this, SignInActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
        );
        startActivity(intent);
    }
}