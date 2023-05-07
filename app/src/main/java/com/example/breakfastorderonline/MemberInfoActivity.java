package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MemberInfoActivity extends AppCompatActivity {

    private TextView emailText;
    private TextView newPasswordText;
    private TextView confirmNewPasswordText;
    private Button saveChangesBtn;
    private Button deleteAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        emailText = findViewById(R.id.memberinfo_email_text);
        newPasswordText = findViewById(R.id.memberinfo_new_password_text);
        confirmNewPasswordText = findViewById(R.id.memberinfo_confirm_new_password_text);
        saveChangesBtn = findViewById(R.id.memberinfo_save_change_btn);
        deleteAccountBtn = findViewById(R.id.memberinfo_delete_account_btn);

        saveChangesBtn.setOnClickListener(onClickListener);
        deleteAccountBtn.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.memberinfo_save_change_btn) {
                Toast.makeText(MemberInfoActivity.this, "save changes", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.memberinfo_delete_account_btn) {
                Toast.makeText(MemberInfoActivity.this, "delete account", Toast.LENGTH_SHORT).show();
            }
        }
    };
}