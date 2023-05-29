package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.breakfastorderonline.fragments.MenuFragment;
import com.example.breakfastorderonline.utils.DatabaseOperator;
import com.example.breakfastorderonline.utils.SharedPreferencesOperator;
import com.example.breakfastorderonline.utils.models.Cart;
import com.example.breakfastorderonline.utils.models.Menu;
import com.example.breakfastorderonline.utils.models.User;

import org.w3c.dom.Text;

public class ordersandwich extends AppCompatActivity {

    private EditText etMealName;
    private Button btnMealAdd;
    private EditText etMealNumber;
    private Button btnMealSubmit;
    private EditText etMealRemark;
    private Button btnCart;
    private String temp;
    private int mealnumber;
    private String[] mealmoney=null;
    //private Text money;
    private MenuFragment menufragment;
    private SharedPreferencesOperator pref;
    private DatabaseOperator db;
    private Text note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersandwich);
        pref = new SharedPreferencesOperator(ordersandwich.this);
        db = new DatabaseOperator(ordersandwich.this);
        etMealName = findViewById(R.id.et_meal_name_sandwich);
        btnMealAdd = findViewById(R.id.btn_meal_add_sandwich);
        etMealNumber = findViewById(R.id.et_meal_number_sandwich);
        btnMealSubmit = findViewById(R.id.btn_meal_submit_sandwich);
        etMealRemark = findViewById(R.id.et_meal_remark_sandwich);
        btnCart = findViewById(R.id.btn_cart_sandwich);
        temp = getIntent().getStringExtra("mealname");
        mealmoney = temp.split(" ");
        Toast.makeText(ordersandwich.this,mealmoney[0]+"+"+mealmoney[1],Toast.LENGTH_LONG).show();
        etMealName.setText(temp);

        btnMealAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mealnumber = Integer.parseInt(etMealNumber.getText().toString());
                mealnumber = mealnumber+1;
                etMealNumber.setText(String.valueOf(mealnumber));
            }
        });
        btnMealSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mealnumber = Integer.parseInt(etMealNumber.getText().toString());
                mealnumber = mealnumber-1;
                if(mealnumber<=1){
                    mealnumber = 1;
                }
                etMealNumber.setText(String.valueOf(mealnumber));
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useraccount = pref.getSignedInUserAccount();
                if(useraccount.isEmpty()){
                    Intent intent = new Intent(ordersandwich.this,SignInActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
                    );
                    startActivity(intent);
                }
                User user = db.findUserByAccount(useraccount);
                Menu menu = db.findMenuDish(mealmoney[0]+"吐司");
                Cart cart = new Cart(
                        user,menu,Integer.parseInt(etMealNumber.getText().toString()),etMealRemark.getText().toString()
                );
                db.addDishToCart(cart);
                Intent intent = new Intent(ordersandwich.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}