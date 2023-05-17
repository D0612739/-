package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.breakfastorderonline.utils.DatabaseOperator;
import com.example.breakfastorderonline.utils.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etEmail;
    private Button btnSignUp;
    private FloatingActionButton btnSignUpBack;

    private DatabaseOperator db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = new DatabaseOperator(SignUpActivity.this);

        etUserName = findViewById(R.id.et_signup_username);
        etEmail = findViewById(R.id.et_signup_email);
        etPassword = findViewById(R.id.et_signup_password);
        etConfirmPassword = findViewById(R.id.et_signup_confirm_password);
        btnSignUp = findViewById(R.id.btn_signup_2);
        btnSignUpBack = findViewById(R.id.btn_signup_back);

        btnSignUp.setOnClickListener(onClickListener);
        btnSignUpBack.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_signup_2) {
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()||email.isEmpty()) {
                    Toast.makeText(SignUpActivity.this,"Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.contains("@") || !email.contains(".")) {
                    Toast.makeText(SignUpActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // add user to database
                User newUser = new User(username, password, email);
                db.addUser(newUser);
                Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                finish();  // close this activity and return back to SignInActivity

//                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("username", username);
//                editor.putString("password", password);
//                editor.putString("email", email);
//                editor.apply();
//                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
//                startActivity(intent);
            } else if (v.getId() == R.id.btn_signup_back) {
                finish();
//                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
//                startActivity(intent);
            }
        }
    };
}