package com.example.breakfastorderonline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.breakfastorderonline.R;
import com.example.breakfastorderonline.utils.models.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OrderHistoryListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Order> orders;

    public OrderHistoryListAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(
                    R.layout.order_history_listview_item, viewGroup, false
            );
        }
        TextView orderTimeTv = view.findViewById(R.id.orderhistory_listview_item_time);
        TextView orderTotalPriceTv = view.findViewById(R.id.orderhistory_listview_item_price);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        orderTimeTv.setText(df.format(orders.get(i).getTime1()));
        orderTotalPriceTv.setText("$" + orders.get(i).getTotalPrice());
        return view;
    }
}
