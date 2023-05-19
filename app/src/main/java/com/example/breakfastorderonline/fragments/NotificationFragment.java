package com.example.breakfastorderonline.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.breakfastorderonline.NotificationDetailedContentActivity;
import com.example.breakfastorderonline.OrderHistoryActivity;
import com.example.breakfastorderonline.R;
import com.example.breakfastorderonline.SignInActivity;
import com.example.breakfastorderonline.adapters.NotificationPageListAdapter;
import com.example.breakfastorderonline.utils.DatabaseOperator;
import com.example.breakfastorderonline.utils.SharedPreferencesOperator;
import com.example.breakfastorderonline.utils.models.Notification;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    private View root;
    private ListView notificationPageListView;
    private NotificationPageListAdapter notificationPageListAdapter;

    private ArrayList<Notification> notifications;
    private SharedPreferencesOperator pref;
    private DatabaseOperator db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_notification, container, false);

        pref = new SharedPreferencesOperator(root.getContext());
        db = new DatabaseOperator(root.getContext());
        updateNotificationList();

        notificationPageListView = root.findViewById(R.id.notification_page_listview);
        notificationPageListAdapter = new NotificationPageListAdapter(
            root.getContext(), notifications
        );
        notificationPageListView.setAdapter(notificationPageListAdapter);
        notificationPageListView.setOnItemClickListener(notificationPageListViewOnItemClickListener);
        registerForContextMenu(notificationPageListView);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateNotificationList();
    }

    private void updateNotificationList() {
        String curUserAccount = pref.getSignedInUserAccount();
        if (curUserAccount.isEmpty()) {
            backToSignInPage();
        }
        notifications = db.findAllNotifications(curUserAccount);
    }

    private void backToSignInPage() {
        Toast.makeText(root.getContext(), "Please sign in again", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(root.getContext(), SignInActivity.class);
        startActivity(intent);
    }

    AdapterView.OnItemClickListener notificationPageListViewOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
            Notification clickedNotification = notifications.get(i);
            db.updateNotificationRead(clickedNotification);  // set user_read to true
            updateNotificationList();
            notificationPageListAdapter.notifyDataSetChanged();
            Intent intent = new Intent(root.getContext(), NotificationDetailedContentActivity.class);
            intent.putExtra("notification_object", clickedNotification);
            startActivity(intent);
        }
    };

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(root.getContext());
        inflater.inflate(R.menu.notification_listpage_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.notification_listpage_menu_delete_current) {
            // 刪除當前選中的通知
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Notification selectedNotification = notifications.get(info.position);
            db.deleteNotification(selectedNotification);
            updateNotificationList();
            notificationPageListAdapter.notifyDataSetChanged();
            Toast.makeText(root.getContext(), "A notification is deleted", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.notification_listpage_menu_delete_all) {
            // 刪除所有通知
            String curUserAccount = pref.getSignedInUserAccount();
            if (curUserAccount.isEmpty()) {
                backToSignInPage();
            }
            db.deleteAllNotifications(curUserAccount);
            updateNotificationList();
            notificationPageListAdapter.notifyDataSetChanged();
            Toast.makeText(root.getContext(), "All notifications are deleted", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }
}