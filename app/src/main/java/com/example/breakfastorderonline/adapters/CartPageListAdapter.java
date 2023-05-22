package com.example.breakfastorderonline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.breakfastorderonline.R;
import com.example.breakfastorderonline.utils.models.Cart;

import java.util.ArrayList;

public class CartPageListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Cart> cartItemList;

    public CartPageListAdapter(Context context, ArrayList<Cart> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @Override
    public int getCount() {
        return cartItemList.size();
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
                    R.layout.cart_page_listview_item, viewGroup, false
            );
        }

        TextView dishName = view.findViewById(R.id.cartpage_listview_item_dishname);
        TextView price = view.findViewById(R.id.cartpage_listview_item_price);
        TextView count = view.findViewById(R.id.cartpage_listview_item_count);

        dishName.setText(cartItemList.get(i).getMenuDish().getName());
        price.setText("$" + cartItemList.get(i).getMenuDish().getPrice());
        count.setText(String.valueOf(cartItemList.get(i).getCount()));

        return view;
    }
}
