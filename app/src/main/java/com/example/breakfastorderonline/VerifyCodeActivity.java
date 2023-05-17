package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.breakfastorderonline.utils.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerifyCodeActivity extends AppCompatActivity {

    private EditText etVerifyCode;
    private Button btnVerify;
    private Button btnReVerifyCode;
    private FloatingActionButton btnVerifyBack;

    private User userObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        userObj = (User) getIntent().getSerializableExtra("user_object");

        etVerifyCode = findViewById(R.id.et_verify_code);
        btnVerify = findViewById(R.id.btn_verify);
        btnReVerifyCode = findViewById(R.id.btn_re_verify_code);
        btnVerifyBack = findViewById(R.id.btn_verify_back);

        btnVerify.setOnClickListener(onClickListener);
        btnReVerifyCode.setOnClickListener(onClickListener);
        btnVerifyBack.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            if (v.getId()==R.id.btn_verify) {
                String verify = etVerifyCode.getText().toString();
                if (verify.isEmpty()) {
                    etVerifyCode.setError("Verify is required");
                } else if (verify.equals("123456")){
                    Intent intent = new Intent(VerifyCodeActivity.this, ChangePasswordActivity.class);
                    intent.putExtra("user_object", userObj);
                    startActivity(intent);
                } else {
                    Toast.makeText(VerifyCodeActivity.this, "Validation error", Toast.LENGTH_LONG).show();
                }

            } else if (v.getId()==R.id.btn_re_verify_code){
                Toast.makeText(VerifyCodeActivity.this, "Verify Code:123456", Toast.LENGTH_LONG).show();
            } else if (v.getId()==R.id.btn_verify_back){
                Intent intent = new Intent(VerifyCodeActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        }
    };
}