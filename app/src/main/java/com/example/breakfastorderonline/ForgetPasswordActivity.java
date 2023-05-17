package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.breakfastorderonline.utils.DatabaseOperator;
import com.example.breakfastorderonline.utils.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText etForgetEmail;
    private Button btnCode;
    private FloatingActionButton btnForgetback;

    private DatabaseOperator db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        db = new DatabaseOperator(ForgetPasswordActivity.this);

        etForgetEmail = findViewById(R.id.et_forget_email);
        btnCode = findViewById(R.id.btn_forget_code);
        btnForgetback = findViewById(R.id.btn_forget_back);

        btnCode.setOnClickListener(onClickListener);
        btnForgetback.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_forget_code) {
                String email = etForgetEmail.getText().toString();
                if (email.isEmpty()) {
                    etForgetEmail.setError("Email is required");
                    return;
                }
                User user = db.findUserByEmail(email);
                if (user == null) {
                    Toast.makeText(ForgetPasswordActivity.this, "無此信箱", Toast.LENGTH_LONG).show();
                    return;
                }
                if (email.equals(user.getEmail())) {
                    Toast.makeText(ForgetPasswordActivity.this, "Verify Code:123456", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgetPasswordActivity.this, VerifyCodeActivity.class);
                    intent.putExtra("user_object", user);
                    startActivity(intent);
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "無此信箱", Toast.LENGTH_LONG).show();
                    return;
                }
//                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
//                String sharedEmail = sharedPreferences.getString("email", "");
//                if (email.equals(sharedEmail)) {
//                    Toast.makeText(ForgetPasswordActivity.this, "Verify Code:123456", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(ForgetPasswordActivity.this, com.example.breakfastorderonline.VerifyCode.class);
//                    startActivity(intent);
//                }else {
//                    Toast.makeText(ForgetPasswordActivity.this, "無此信箱", Toast.LENGTH_LONG).show();
//                }
            } else if (v.getId() == R.id.btn_forget_back) {
                finish();
//                Intent intent = new Intent(ForgetPasswordActivity.this, SignInActivity.class);
//                startActivity(intent);
            }
        }
    };
}