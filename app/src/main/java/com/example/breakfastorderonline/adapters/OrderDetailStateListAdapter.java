package com.example.breakfastorderonline.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.breakfastorderonline.R;
import com.example.breakfastorderonline.utils.models.Order;
import com.example.breakfastorderonline.utils.models.OrderState;

public class OrderDetailStateListAdapter extends BaseAdapter {

    private Context context;
    private String[] orderStateTitles;
    private Order curOrder;

    public OrderDetailStateListAdapter(Context context, String[] orderStateTitles, Order curOrder) {
        this.context = context;
        this.orderStateTitles = orderStateTitles;
        this.curOrder = curOrder;
    }

    @Override
    public int getCount() {
        return orderStateTitles.length;
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
                    R.layout.order_detail_state_listview_item, viewGroup, false
            );
        }
        TextView stateTitle = view.findViewById(R.id.orderdetail_state_lvitem_statestr);
        stateTitle.setText(orderStateTitles[i]);
        switch (curOrder.getState()) {
            case MAKING:
                if (i == 0) {
                    stateTitle.setBackgroundColor(Color.RED);
                } else if (i == 1) {
                    stateTitle.setBackgroundColor(Color.GRAY);
                } else if (i == 2) {
                    stateTitle.setBackgroundColor(Color.GRAY);
                }
                break;
            case WAITING_TAKING:
                if (i == 0) {
                    stateTitle.setBackgroundColor(Color.GREEN);
                } else if (i == 1) {
                    stateTitle.setBackgroundColor(Color.RED);
                } else if (i == 2) {
                    stateTitle.setBackgroundColor(Color.GRAY);
                }
                break;
            case COMPLETE:
                if (i == 0) {
                    stateTitle.setBackgroundColor(Color.GREEN);
                } else if (i == 1) {
                    stateTitle.setBackgroundColor(Color.GREEN);
                } else if (i == 2) {
                    stateTitle.setBackgroundColor(Color.GREEN);
                }
                break;
        }
        return view;
    }
}
