package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Changepassword extends AppCompatActivity {

    private EditText etChangeNewpassword;
    private EditText etChangeConfirmNewpassword;
    private Button btnChangePassword;
    private FloatingActionButton btnChangeBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etChangeNewpassword = findViewById(R.id.et_change_newpassword);
        etChangeConfirmNewpassword = findViewById(R.id.et_change_confirm_newpassword);
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnChangeBack = findViewById(R.id.btn_change_back);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btn_change_password){
                    String newpassword = etChangeNewpassword.getText().toString();
                    String confirmnewpassword = etChangeConfirmNewpassword.getText().toString();
                    if(newpassword.equals(confirmnewpassword)){
                        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("password");
                        editor.putString("password", newpassword);
                        editor.apply();
                        Toast.makeText(Changepassword.this, "更改密碼成功12", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Changepassword.this, SignInActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Changepassword.this, "密碼錯誤", Toast.LENGTH_SHORT).show();
                    }

                }else if(v.getId()==R.id.btn_change_back){
                    Intent intent = new Intent(Changepassword.this, VerifyCode.class);
                    startActivity(intent);
                }
            }



        };
        btnChangePassword.setOnClickListener(onClickListener);
        btnChangeBack.setOnClickListener(onClickListener);
    }
}
