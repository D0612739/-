package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.breakfastorderonline.adapters.CheckoutMenuDishListAdapter;
import com.example.breakfastorderonline.utils.DatabaseOperator;
import com.example.breakfastorderonline.utils.SharedPreferencesOperator;
import com.example.breakfastorderonline.utils.models.Cart;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class CheckoutActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private TextView dateOfToday;
    private TimePicker timePicker;
    private ListView menuDishListView;
    private EditText orderNote;
    private TextView totalPrice;
    private Button makeOrderBtn;

    private SharedPreferencesOperator pref;
    private DatabaseOperator db;

    private ArrayList<Cart> cartItemList;
    private CheckoutMenuDishListAdapter checkoutMenuDishListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        pref = new SharedPreferencesOperator(CheckoutActivity.this);
        db = new DatabaseOperator(CheckoutActivity.this);

        updateCartItemList();

        radioGroup = findViewById(R.id.checkout_radiogroup);
        dateOfToday = findViewById(R.id.checkout_dateoftoday);
        timePicker = findViewById(R.id.checkout_timepicker);
        menuDishListView = findViewById(R.id.checkout_menudish_listview);
        orderNote = findViewById(R.id.checkout_order_note);
        totalPrice = findViewById(R.id.checkout_total_price);
        makeOrderBtn = findViewById(R.id.checkout_make_order_btn);

        checkoutMenuDishListAdapter = new CheckoutMenuDishListAdapter(
                CheckoutActivity.this, cartItemList
        );
        menuDishListView.setAdapter(checkoutMenuDishListAdapter);

        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        radioGroup.check(R.id.checkout_radiobtn_fatest);
        timePicker.setOnTimeChangedListener(onTimeChangedListener);

        // display the date of today
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dateOfToday.setText("今天: " + df.format(today));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartItemList();
    }

    private void updateCartItemList() {
        String curUserAccount = pref.getSignedInUserAccount();
        if (curUserAccount.isEmpty()) {
            backToSignInPage();
        }
        cartItemList = db.findAllCartItems(curUserAccount);
    }

    private void backToSignInPage() {
        Toast.makeText(CheckoutActivity.this, "Please sign in again", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CheckoutActivity.this, SignInActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
        );
        startActivity(intent);
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            if (checkedId == R.id.checkout_radiobtn_fatest) {
                timePicker.setEnabled(false);

            } else if (checkedId == R.id.checkout_radiobtn_reservetime) {
                timePicker.setEnabled(true);
            }
        }
    };

    TimePicker.OnTimeChangedListener onTimeChangedListener = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
            // 限制可選時間
            /* 先不管營不營業什麼的...
            int curHour = LocalDateTime.now().getHour();
            if (curHour < 5) {
                curHour = 5;
            } else if (curHour > 11) {
                return;
            }
            if (hourOfDay < curHour) {
                timePicker.setHour(curHour);
            } else if (hourOfDay > 11) {
                timePicker.setHour(11);
            }
            */
            // demo mode
            if (hourOfDay < 5) {
                timePicker.setHour(5);
            } else if (hourOfDay > 11) {
                timePicker.setHour(11);
            }
        }
    };
}