package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breakfastorderonline.utils.DatabaseOperator;
import com.example.breakfastorderonline.utils.SharedPreferencesOperator;
import com.example.breakfastorderonline.utils.models.Cart;

public class EditCartItemActivity extends AppCompatActivity {

    private static final String MIN_DISH_COUNT = "1";
    private static final String MAX_DISH_COUNT = "50";

    private TextView dishName;
    private TextView dishPrice;
    private EditText dishCount;
    private ImageButton increaseCountBtn;
    private ImageButton decreaseCountBtn;
    private EditText note;
    private Button cancelBtn;
    private Button okBtn;
    private Button remFromCartBtn;

    private Cart cartObj;
    private DatabaseOperator db;
    private SharedPreferencesOperator pref;
    private boolean ignoreDishCountTextChange;  // 用來在TextWatcher中避免遞迴死循環

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cartitem);

        cartObj = (Cart) getIntent().getSerializableExtra("cart_object");
        db = new DatabaseOperator(EditCartItemActivity.this);
        pref = new SharedPreferencesOperator(EditCartItemActivity.this);
        ignoreDishCountTextChange = false;

        dishName = findViewById(R.id.editcartitem_dishname);
        dishPrice = findViewById(R.id.editcartitem_dishprice);
        dishCount = findViewById(R.id.editcartitem_dishcount);
        increaseCountBtn = findViewById(R.id.editcartitem_increase_count_btn);
        decreaseCountBtn = findViewById(R.id.editcartitem_decrease_count_btn);
        note = findViewById(R.id.editcartitem_note);
        cancelBtn = findViewById(R.id.editcartitem_cancel_btn);
        okBtn = findViewById(R.id.editcartitem_ok_btn);
        remFromCartBtn = findViewById(R.id.editcartitem_remove_from_cart_btn);

        dishCount.addTextChangedListener(textWatcher);
        increaseCountBtn.setOnClickListener(onClickListener);
        decreaseCountBtn.setOnClickListener(onClickListener);
        cancelBtn.setOnClickListener(onClickListener);
        okBtn.setOnClickListener(onClickListener);
        remFromCartBtn.setOnClickListener(onClickListener);

        dishName.setText(cartObj.getMenuDish().getName());
        dishPrice.setText("$" + String.valueOf(cartObj.getMenuDish().getPrice()));
        dishCount.setText(String.valueOf(cartObj.getCount()));
        note.setText(cartObj.getNote());
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.editcartitem_increase_count_btn) {
                int curCount = Integer.parseInt(dishCount.getText().toString());
                if (++curCount < 51) {
                    dishCount.setText(String.valueOf(curCount));
                } else {
                    dishCount.setText(MAX_DISH_COUNT);
                }
            } else if (id == R.id.editcartitem_decrease_count_btn) {
                int curCount = Integer.parseInt(dishCount.getText().toString());
                if (--curCount > 0) {
                    dishCount.setText(String.valueOf(curCount));
                } else {
                    dishCount.setText(MIN_DISH_COUNT);
                }
            } else if (id == R.id.editcartitem_cancel_btn) {
                finish();
            } else if (id == R.id.editcartitem_ok_btn) {
                cartObj.setCount(Integer.parseInt(dishCount.getText().toString()));
                cartObj.setNote(note.getText().toString());
                db.updateCartItem(cartObj);
                pref.setMainActivityRecreateFlag();
                finish();
            } else if (id == R.id.editcartitem_remove_from_cart_btn) {
                db.deleteCartItem(cartObj);
                pref.setMainActivityRecreateFlag();
                Toast.makeText(EditCartItemActivity.this, "您移除了一項餐點", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence beforeChangeCharSeq, int start, int count, int after) {
            // do not use
        }

        @Override
        public void onTextChanged(CharSequence afterChangeCharSeq, int start, int before, int count) {
            // do not use
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // 令使用者不能清除dishCount的值或設定超出[1, 50]範圍的值
            if (!ignoreDishCountTextChange) {
                ignoreDishCountTextChange = true;
                if (editable.length() == 0) {
                    dishCount.setText(MIN_DISH_COUNT);
                } else {
                    int curCount = Integer.parseInt(editable.toString());
                    if (curCount <= 0) {
                        dishCount.setText(MIN_DISH_COUNT);
                    } else if (curCount > 50) {
                        dishCount.setText(MAX_DISH_COUNT);
                    }
                }
                ignoreDishCountTextChange = false;
            }
        }
    };
}