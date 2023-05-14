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
import com.example.breakfastorderonline.utils.SharedPreferencesOperator;
import com.example.breakfastorderonline.utils.models.User;

public class SignInActivity extends AppCompatActivity {

    private Button btnSignIn;
    private Button btnSignUp;
    private Button btnForget;
    private EditText etUserName;
    private EditText etPassword;

    private SharedPreferencesOperator pref;
    private DatabaseOperator db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_sign_in));

        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup);
        btnForget = findViewById(R.id.btn_forget);
        etUserName = findViewById(R.id.et_signin_username);
        etPassword = findViewById(R.id.et_signin_password);

        btnSignIn.setOnClickListener(onClickListener);
        btnSignUp.setOnClickListener(onClickListener);
        btnForget.setOnClickListener(onClickListener);

        pref = new SharedPreferencesOperator(SignInActivity.this);
        db = new DatabaseOperator(SignInActivity.this);

        if (hasUserSignedIn()) {
            // 有使用者已經登入就直接跳轉到MainActivity
            Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(mainIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasUserSignedIn()) {
            // 有使用者已經登入就直接跳轉到MainActivity
            Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(mainIntent);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_signin) {
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Please enter usernaeme or password", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = db.findUser(username);
                if (user == null) {
                    Toast.makeText(SignInActivity.this, "Invalid account or password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (user.getPassword().equals(password)) {
                    // Sign in successfully
                    pref.setUserSignedIn(user.getAccount());
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Sign in failed
                    Toast.makeText(SignInActivity.this, "Invalid account or password", Toast.LENGTH_LONG).show();
                }

//                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
//                String sharedUserName = sharedPreferences.getString("username", "");
//                String sharedPassword = sharedPreferences.getString("password", "");
//                if (username.equals(sharedUserName) && password.equals(sharedPassword)) {
//                    sharedPreferences.edit().putBoolean("SignIn", true).apply();
//                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(SignInActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
//                }
            } else if (v.getId() == R.id.btn_signup) {
                Intent intent = new Intent(SignInActivity.this, com.example.breakfastorderonline.SignUpActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.btn_forget) {
                Intent intent = new Intent(SignInActivity.this, com.example.breakfastorderonline.ForgetPasswordActivity.class);
                startActivity(intent);
            }
        }
    };

    private boolean hasUserSignedIn() {
        return !pref.getSignedInUserAccount().isEmpty();
    }
}