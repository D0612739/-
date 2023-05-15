package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.breakfastorderonline.adapters.OrderHistoryListAdapter;
import com.example.breakfastorderonline.utils.DatabaseOperator;
import com.example.breakfastorderonline.utils.SharedPreferencesOperator;
import com.example.breakfastorderonline.utils.models.Order;
import com.example.breakfastorderonline.utils.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OrderHistoryActivity extends AppCompatActivity {

    private ListView orderHistoryListView;
    private ArrayList<Order> orders;  // get from database
    private DatabaseOperator db;
    private SharedPreferencesOperator pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        db = new DatabaseOperator(OrderHistoryActivity.this);
        pref = new SharedPreferencesOperator(OrderHistoryActivity.this);
        updateOrderHistory();

        orderHistoryListView = findViewById(R.id.order_history_listview);
        OrderHistoryListAdapter orderHistoryListAdapter = new OrderHistoryListAdapter(
                OrderHistoryActivity.this, orders
        );
        orderHistoryListView.setAdapter(orderHistoryListAdapter);
        orderHistoryListView.setOnItemClickListener(orderHistoryListViewItemClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateOrderHistory();
    }

    AdapterView.OnItemClickListener orderHistoryListViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (i >= orders.size()) {
                return;
            }
            Order clickedOrder = orders.get(i);
            Intent orderDetailIntent = new Intent(OrderHistoryActivity.this, OrderDetailActivity.class);
            orderDetailIntent.putExtra("order_object", clickedOrder);
            startActivity(orderDetailIntent);
        }
    };

    private void updateOrderHistory() {
        // get orders from database
        String curUserAccount = pref.getSignedInUserAccount();
        if (curUserAccount.isEmpty()) {
            backToSignInPage();
        }
        orders = db.findAllOrders(curUserAccount);
    }

    private void backToSignInPage() {
        Toast.makeText(OrderHistoryActivity.this, "Please sign in again", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(OrderHistoryActivity.this, SignInActivity.class);
        startActivity(intent);
    }
}