package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breakfastorderonline.utils.DatabaseOperator;
import com.example.breakfastorderonline.utils.SharedPreferencesOperator;
import com.example.breakfastorderonline.utils.models.User;

public class MemberInfoActivity extends AppCompatActivity {

    private TextView emailText;
    private TextView newPasswordText;
    private TextView confirmNewPasswordText;
    private TextView warningText;
    private Button saveChangesBtn;
    private Button deleteAccountBtn;

    private DatabaseOperator db;
    private SharedPreferencesOperator pref;

    private User userObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        emailText = findViewById(R.id.memberinfo_email_text);
        newPasswordText = findViewById(R.id.memberinfo_new_password_text);
        confirmNewPasswordText = findViewById(R.id.memberinfo_confirm_new_password_text);
        saveChangesBtn = findViewById(R.id.memberinfo_save_change_btn);
        deleteAccountBtn = findViewById(R.id.memberinfo_delete_account_btn);
        warningText = findViewById(R.id.memberinfo_psw_warning);

        saveChangesBtn.setOnClickListener(onClickListener);
        deleteAccountBtn.setOnClickListener(onClickListener);
        warningText.setVisibility(View.INVISIBLE);

        pref = new SharedPreferencesOperator(MemberInfoActivity.this);
        db = new DatabaseOperator(MemberInfoActivity.this);

        // get signed in user
        String curUserAccount = pref.getSignedInUserAccount();
        if (curUserAccount.isEmpty()) {
            // found no user signed in
            backToSignInPage();
        }

        // get user data
        userObj = db.findUser(curUserAccount);
        if (userObj == null) {
            backToSignInPage();
        } else {
            emailText.setText(userObj.getEmail());
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            warningText.setVisibility(View.INVISIBLE);
            if (id == R.id.memberinfo_save_change_btn) {
                if (userObj == null) {
                    backToSignInPage();
                }
                User newUser = new User(userObj.getAccount(), userObj.getPassword(), userObj.getEmail());
                String editEmail = emailText.getText().toString();
                String editPassword = newPasswordText.getText().toString();
                String confirmEditPassword = confirmNewPasswordText.getText().toString();
                // set new email if need
                if (!editEmail.isEmpty()) {
                    newUser.setEmail(editEmail);
                } else {
                    Toast.makeText(MemberInfoActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                // set new password if need
                if (!editPassword.isEmpty()) {
                    if (editPassword.equals(confirmEditPassword)) {
                        newUser.setPassword(editPassword);
                    } else {
                        warningText.setVisibility(View.VISIBLE);
                        return;
                    }
                }
                db.updateUser(newUser);
                Toast.makeText(MemberInfoActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                finish();  // close current activity and return to previous activity
            } else if (id == R.id.memberinfo_delete_account_btn) {
                if (userObj == null) {
                    backToSignInPage();
                }
                db.deleteUser(userObj.getAccount());
                Toast.makeText(MemberInfoActivity.this, "Delete account successfully", Toast.LENGTH_SHORT).show();
                pref.clearSignedInUser();
                backToSignInPage();
            }
        }
    };

    private void backToSignInPage() {
        Toast.makeText(MemberInfoActivity.this, "Please sign in again", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MemberInfoActivity.this, SignInActivity.class);
        startActivity(intent);
    }
}