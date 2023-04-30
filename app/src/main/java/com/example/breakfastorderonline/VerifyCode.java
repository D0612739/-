package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerifyCode extends AppCompatActivity {

    private EditText etVerifyCode;
    private Button btnVerify;
    private Button btnReVerifyCode;
    private FloatingActionButton btnVerifyBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        etVerifyCode = findViewById(R.id.et_verify_code);
        btnVerify = findViewById(R.id.btn_verify);
        btnReVerifyCode = findViewById(R.id.btn_re_verify_code);
        btnVerifyBack = findViewById(R.id.btn_verify_back);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(v.getId()==R.id.btn_verify) {
                    String verify = etVerifyCode.getText().toString();
                    if (verify.isEmpty()){
                        etVerifyCode.setError("Verify is required");
                    }else if (verify.equals("123456")){
                        Intent intent = new Intent(VerifyCode.this, com.example.breakfastorderonline.Changepassword.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(VerifyCode.this, "Validation error", Toast.LENGTH_LONG).show();
                    }

                }else if(v.getId()==R.id.btn_re_verify_code){
                    Toast.makeText(VerifyCode.this, "Verify Code:123456", Toast.LENGTH_LONG).show();
                }else if(v.getId()==R.id.btn_verify_back){
                    Intent intent = new Intent(VerifyCode.this, ForgetPasswordActivity.class);
                    startActivity(intent);
                }
            }
        };

        btnVerify.setOnClickListener(onClickListener);
        btnReVerifyCode.setOnClickListener(onClickListener);
        btnVerifyBack.setOnClickListener(onClickListener);
    }
}