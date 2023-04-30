package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText etForgetEmail;
    private Button btnCode;
    private FloatingActionButton btnForgetback;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        etForgetEmail = findViewById(R.id.et_forget_email);
        btnCode = findViewById(R.id.btn_forget_code);
        btnForgetback = findViewById(R.id.btn_forget_back);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.btn_forget_code) {
                    String email = etForgetEmail.getText().toString();
                    if (email.isEmpty()) {
                        etForgetEmail.setError("Email is required");
                    }
                    SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                    String sharedEmail = sharedPreferences.getString("email", "");
                    if (email.equals(sharedEmail)) {
                        Toast.makeText(ForgetPasswordActivity.this, "Verify Code:123456", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ForgetPasswordActivity.this, com.example.breakfastorderonline.VerifyCode.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(ForgetPasswordActivity.this, "無此信箱", Toast.LENGTH_LONG).show();
                    }
                }else if (v.getId() == R.id.btn_forget_back) {
                    Intent intent = new Intent(ForgetPasswordActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            }
        };


        btnCode.setOnClickListener(onClickListener);
        btnForgetback.setOnClickListener(onClickListener);
    }

}