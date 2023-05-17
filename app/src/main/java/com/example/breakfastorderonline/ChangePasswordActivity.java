package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.breakfastorderonline.utils.DatabaseOperator;
import com.example.breakfastorderonline.utils.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etChangeNewPassword;
    private EditText etChangeConfirmNewPassword;
    private Button btnChangePassword;
    private FloatingActionButton btnChangeBack;

    private User userObj;
    private DatabaseOperator db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        userObj = (User) getIntent().getSerializableExtra("user_object");
        db = new DatabaseOperator(ChangePasswordActivity.this);

        etChangeNewPassword = findViewById(R.id.et_change_newpassword);
        etChangeConfirmNewPassword = findViewById(R.id.et_change_confirm_newpassword);
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnChangeBack = findViewById(R.id.btn_change_back);

        btnChangePassword.setOnClickListener(onClickListener);
        btnChangeBack.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.btn_change_password) {
                String newPassword = etChangeNewPassword.getText().toString();
                String confirmNewPassword = etChangeConfirmNewPassword.getText().toString();
                if (newPassword.equals(confirmNewPassword)) {
                    userObj.setPassword(newPassword);
                    db.updateUser(userObj);
                    Toast.makeText(ChangePasswordActivity.this, "更改密碼成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePasswordActivity.this, SignInActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
                    );
                    startActivity(intent);
//                    SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.remove("password");
//                    editor.putString("password", newPassword);
//                    editor.apply();
//                    Toast.makeText(ChangePasswordActivity.this, "更改密碼成功12", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(ChangePasswordActivity.this, SignInActivity.class);
//                    startActivity(intent);
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "密碼錯誤", Toast.LENGTH_SHORT).show();
                }
            } else if (v.getId()==R.id.btn_change_back){
                finish();
//                Intent intent = new Intent(ChangePasswordActivity.this, VerifyCodeActivity.class);
//                startActivity(intent);
            }
        }
    };
}
