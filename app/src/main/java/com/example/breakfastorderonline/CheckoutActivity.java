package com.example.breakfastorderonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.breakfastorderonline.adapters.CheckoutMenuDishListAdapter;
import com.example.breakfastorderonline.utils.DatabaseOperator;
import com.example.breakfastorderonline.utils.SharedPreferencesOperator;
import com.example.breakfastorderonline.utils.models.Cart;
import com.example.breakfastorderonline.utils.models.Notification;
import com.example.breakfastorderonline.utils.models.Order;
import com.example.breakfastorderonline.utils.models.OrderDishes;
import com.example.breakfastorderonline.utils.models.OrderState;
import com.example.breakfastorderonline.utils.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

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
    private int menuDishTotalPriceValue;
    private Calendar todayDateCalendar;  // 只有年、月、日有意義，忽略時、分、秒

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
        radioGroup.check(R.id.checkout_radiobtn_fastest);
        timePicker.setOnTimeChangedListener(onTimeChangedListener);
        makeOrderBtn.setOnClickListener(onMakeOrderBtnClickListener);

        // display the date of today
        Date today = new Date();
        todayDateCalendar = Calendar.getInstance();
        todayDateCalendar.setTime(today);
        SimpleDateFormat df = new SimpleDateFormat("yyyy年 MM月 dd日");
        dateOfToday.setText("今日:  " + df.format(today));

        // calculate total price
        menuDishTotalPriceValue = calculateTotalPriceOfCartItems(cartItemList);
        totalPrice.setText("$" + menuDishTotalPriceValue);
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
            if (checkedId == R.id.checkout_radiobtn_fastest) {
                timePicker.setEnabled(false);
            } else if (checkedId == R.id.checkout_radiobtn_reservetime) {
                timePicker.setEnabled(true);
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                // limit time range
                if (hour < 5 || (hour == 5 && minute <= 30)) {
                    timePicker.setHour(5);
                    timePicker.setMinute(30);
                } else if (hour > 11 || (hour == 11 && minute > 0)) {
                    timePicker.setHour(11);
                    timePicker.setMinute(0);
                }
            }
        }
    };

    TimePicker.OnTimeChangedListener onTimeChangedListener = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
            // 限制可選時間 (demo mode)
            if (hour < 5 || (hour == 5 && minute <= 30)) {
                timePicker.setHour(5);
                timePicker.setMinute(30);
            } else if (hour > 11 || (hour == 11 && minute > 0)) {
                timePicker.setHour(11);
                timePicker.setMinute(0);
            }
        }
    };

    View.OnClickListener onMakeOrderBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (cartItemList.size() == 0) {
                Toast.makeText(CheckoutActivity.this, "您的購物車是空的", Toast.LENGTH_SHORT).show();
                return;
            }

            // get user
            String curUserAccount = pref.getSignedInUserAccount();
            if (curUserAccount.isEmpty()) {
                backToSignInPage();
            }
            User user = db.findUserByAccount(curUserAccount);

            // generate order id
            String orderId = UUID.randomUUID().toString();

            // set time1 (固定下單時間) (demo mode = fixed time)
            Calendar time1Calendar = Calendar.getInstance();
            time1Calendar.set(
                    todayDateCalendar.get(Calendar.YEAR),
                    todayDateCalendar.get(Calendar.MONTH),
                    todayDateCalendar.get(Calendar.DAY_OF_MONTH),
                    5,  // 固定下單時間早上05:30，模擬使用者(demo mode)
                    30
            );

            // set time2 (取餐時間) (demo mode = {fastest: 15min, reserve: reservation time})
            Calendar time2Calendar = Calendar.getInstance();
            time2Calendar.setTime(time1Calendar.getTime());
            int radioId = radioGroup.getCheckedRadioButtonId();
            if (radioId == R.id.checkout_radiobtn_fastest) {
                time2Calendar.add(Calendar.MINUTE, 15);
            } else if (radioId == R.id.checkout_radiobtn_reservetime) {
                time2Calendar.set(Calendar.HOUR, timePicker.getHour());
                time2Calendar.set(Calendar.MINUTE, timePicker.getMinute());
            }

            // set order state
            OrderState orderState = OrderState.MAKING;

            // build order object
            Order newOrder = new Order(
                orderId,
                user,
                time1Calendar.getTime(),
                time2Calendar.getTime(),
                orderNote.getText().toString(),
                orderState,
                menuDishTotalPriceValue
            );

            // build order dish objects
            ArrayList<OrderDishes> orderDishesList = new ArrayList<>();
            for (Cart cartItem: cartItemList) {
                orderDishesList.add(
                    new OrderDishes(
                        newOrder,
                        cartItem.getMenuDish(),
                        cartItem.getCount(),
                        cartItem.getNote()
                    )
                );
            }

            // insert new order
            db.addOrder(newOrder);

            // insert new order dishes
            db.addManyOrderDishes(orderDishesList);

            // clear cart items after built a new order
            db.clearCartItems(curUserAccount);

            // insert notification
            Notification notification = new Notification(
                time1Calendar.getTime(),
                newOrder,
                "已收到您的訂單",
                "您的餐點正在製作中，請耐心等候。",
                false
            );
            db.addNotification(notification);

            updateCartItemList();
            checkoutMenuDishListAdapter.notifyDataSetChanged();
            pref.setMainActivityRecreateFlag();
            Toast.makeText(CheckoutActivity.this, "下單成功", Toast.LENGTH_SHORT).show();
            finish();  // close this activity
        }
    };

    private int calculateTotalPriceOfCartItems(ArrayList<Cart> cartItemList) {
        int tPrice = 0;
        for (Cart cartItem : cartItemList) {
            tPrice += cartItem.getCount() * cartItem.getMenuDish().getPrice();
        }
        return tPrice;
    }
}