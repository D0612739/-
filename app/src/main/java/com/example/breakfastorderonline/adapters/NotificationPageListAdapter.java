package com.example.breakfastorderonline.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.breakfastorderonline.R;
import com.example.breakfastorderonline.utils.models.Notification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NotificationPageListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Notification> notifications;

    public NotificationPageListAdapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(
                R.layout.notification_page_listview_item, viewGroup, false
            );
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        TextView orderIdText = view.findViewById(R.id.notification_listview_item_orderid);
        TextView titleText = view.findViewById(R.id.notification_listview_item_title);
        TextView timeText = view.findViewById(R.id.notification_listview_item_time);

        orderIdText.setText("訂單編號: " + notifications.get(i).getOrder().getId());
        titleText.setText(notifications.get(i).getTitle());
        timeText.setText(df.format(notifications.get(i).getTime()));

        // 未讀通知為白色背景，已讀通知為灰色背景
        if (notifications.get(i).isUserRead()) {
            view.setBackgroundColor(Color.parseColor("#D4D4D4"));
        } else {
            view.setBackgroundColor(Color.WHITE);
        }

        return view;
    }
}
