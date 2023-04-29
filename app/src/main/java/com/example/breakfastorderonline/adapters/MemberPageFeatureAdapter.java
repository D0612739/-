package com.example.breakfastorderonline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.breakfastorderonline.R;

public class MemberPageFeatureAdapter extends BaseAdapter {

    private Context context;
    private String[] features;
    private int[] icons;

    public MemberPageFeatureAdapter(Context context, String[] features, int[] icons) {
        this.context = context;
        this.features = features;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return features.length;
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
                    R.layout.member_page_listview_item, viewGroup, false
            );
        }
        ImageView itemIcon = view.findViewById(R.id.member_page_listview_item_icon);
        TextView itemText = view.findViewById(R.id.member_page_listview_item_text);
        itemIcon.setImageResource(icons[i]);
        itemText.setText(features[i]);
        return view;
    }
}
