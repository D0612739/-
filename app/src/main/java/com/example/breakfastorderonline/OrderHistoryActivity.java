package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.breakfastorderonline.adapters.OrderHistoryListAdapter;
import com.example.breakfastorderonline.utils.DatabaseOperator;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        db = new DatabaseOperator(OrderHistoryActivity.this);
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
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            String testMsg = df.format(orders.get(i).getTime1()) + " -- " + orders.get(i).getTotalPrice();
            Toast.makeText(OrderHistoryActivity.this, testMsg, Toast.LENGTH_SHORT).show();
        }
    };

    private void updateOrderHistory() {
        // test
        int testDataLength = 5;
        int priceCount = 100;
        int DD = 1;
        int id = 1;

        orders = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 4, DD, 7, 30);  // month is start from 0

        for (int i = 0; i < testDataLength; i++) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            orders.add(new Order(
                    id++, new User("", "", ""), calendar.getTime(), calendar.getTime(), "", "temp", priceCount
            ));
            calendar.set(2023, 4, ++DD, 7, 30);
            priceCount += 20;
        }
        // get orders from database
    }
}