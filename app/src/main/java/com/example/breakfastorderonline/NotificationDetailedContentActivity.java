package com.example.breakfastorderonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breakfastorderonline.utils.DatabaseOperator;
import com.example.breakfastorderonline.utils.models.Notification;

import java.text.SimpleDateFormat;

public class NotificationDetailedContentActivity extends AppCompatActivity {

    private TextView detailedContentTitle;
    private TextView detailedContentOrderId;
    private TextView detailedContentTime;
    private TextView detailedContentContentText;
    private Button detailedContentCheckOrderBtn;

    private Notification notificationObj;
    private DatabaseOperator db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detailed_content);

        notificationObj = (Notification) getIntent().getSerializableExtra("notification_object");
        db = new DatabaseOperator(NotificationDetailedContentActivity.this);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        detailedContentOrderId = findViewById(R.id.notification_detailedcontent_orderid);
        detailedContentTitle = findViewById(R.id.notification_detailedcontent_title);
        detailedContentTime = findViewById(R.id.notification_detailedcontent_time);
        detailedContentContentText = findViewById(R.id.notification_detailedcontent_content);
        detailedContentCheckOrderBtn = findViewById(R.id.notification_detailedcontent_checkorder_btn);

        detailedContentOrderId.setText("訂單編號: " + notificationObj.getOrder().getId());
        detailedContentTitle.setText("標題: " + notificationObj.getTitle());
        detailedContentTime.setText("時間: " + df.format(notificationObj.getTime()));
        detailedContentContentText.setText(notificationObj.getContent());

        detailedContentCheckOrderBtn.setOnClickListener(checkOrderBtnOnClickListener);
    }

    View.OnClickListener checkOrderBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent orderDetailIntent = new Intent(NotificationDetailedContentActivity.this, OrderDetailActivity.class);
            orderDetailIntent.putExtra("order_object", notificationObj.getOrder());
            startActivity(orderDetailIntent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.notification_detail_menu_delete) {
            // delete current notification
            db.deleteNotification(notificationObj);
            Toast.makeText(NotificationDetailedContentActivity.this, "A notification is deleted", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}