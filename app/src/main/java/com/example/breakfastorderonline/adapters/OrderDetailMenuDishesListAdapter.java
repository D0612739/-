package com.example.breakfastorderonline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.breakfastorderonline.R;
import com.example.breakfastorderonline.utils.models.OrderDishes;

import java.util.ArrayList;

public class OrderDetailMenuDishesListAdapter extends BaseAdapter {

    private ArrayList<OrderDishes> orderDishesList;
    private Context context;

    public OrderDetailMenuDishesListAdapter(ArrayList<OrderDishes> orderDishesList, Context context) {
        this.orderDishesList = orderDishesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return orderDishesList.size();
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
                    R.layout.order_detail_menudishes_listview_item, viewGroup, false
            );
        }
        TextView dishName = view.findViewById(R.id.orderdetail_menudishes_lvitem_dishname);
        TextView price = view.findViewById(R.id.orderdetail_menudishes_lvitem_price);
        TextView count = view.findViewById(R.id.orderdetail_menudishes_lvitem_count);
        OrderDishes curOrderDishes = orderDishesList.get(i);
        dishName.setText(curOrderDishes.getMenuDish().getName());
        price.setText("$" + curOrderDishes.getMenuDish().getPrice());
        count.setText(String.valueOf(curOrderDishes.getCount()));
        return view;
    }
}
