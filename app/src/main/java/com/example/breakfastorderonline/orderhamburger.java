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

public class orderhamburger extends AppCompatActivity {

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
    setContentView(R.layout.activity_orderhamburger);

    pref = new SharedPreferencesOperator(orderhamburger.this);
    db = new DatabaseOperator(orderhamburger.this);
    etMealName = (EditText) findViewById(R.id.et_meal_name_hamburger);
    btnMealAdd = (Button) findViewById(R.id.btn_meal_add_hamburger);
    etMealNumber = (EditText) findViewById(R.id.et_meal_number_hamburger);
    btnMealSubmit = (Button) findViewById(R.id.btn_meal_submit_hamburger);
    etMealRemark = (EditText) findViewById(R.id.et_meal_remark_hamburger);
    btnCart = (Button) findViewById(R.id.btn_cart_hamburger);
    temp = getIntent().getStringExtra("mealname");
    mealmoney = temp.split(" ");
    //Toast.makeText(orderhamburger.this,mealmoney[0]+"+"+mealmoney[1],Toast.LENGTH_LONG).show();
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
          Intent intent = new Intent(orderhamburger.this,SignInActivity.class);
          intent.setFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
          );
          startActivity(intent);
        }
        User user = db.findUserByAccount(useraccount);
        //Toast.makeText(orderhamburger.this,mealmoney[0],Toast.LENGTH_LONG).show();
        Menu menu = db.findMenuDish(mealmoney[0]+"漢堡");
        //Toast.makeText(orderhamburger.this,menu.getName(),Toast.LENGTH_LONG).show();
        Cart cart = new Cart(
          user,menu,Integer.parseInt(etMealNumber.getText().toString()),etMealRemark.getText().toString()
        );
        db.addDishToCart(cart);
        Intent intent = new Intent(orderhamburger.this,MainActivity.class);
        startActivity(intent);
      }
    });


  }


}