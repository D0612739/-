package com.example.breakfastorderonline.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breakfastorderonline.CheckoutActivity;
import com.example.breakfastorderonline.R;
import com.example.breakfastorderonline.SignInActivity;
import com.example.breakfastorderonline.adapters.CartPageListAdapter;
import com.example.breakfastorderonline.utils.DatabaseOperator;
import com.example.breakfastorderonline.utils.SharedPreferencesOperator;
import com.example.breakfastorderonline.utils.models.Cart;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private View root;

    private TextView cartTitle;
    private ListView cartListView;
    private Button cartCheckOrderBtn;

    private SharedPreferencesOperator pref;
    private DatabaseOperator db;
    private ArrayList<Cart> cartItemList;
    private CartPageListAdapter cartPageListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_cart, container, false);

        pref = new SharedPreferencesOperator(root.getContext());
        db = new DatabaseOperator(root.getContext());
        updateCartItemList();

        cartTitle = root.findViewById(R.id.cart_page_title);
        cartListView = root.findViewById(R.id.cart_page_listview);
        cartCheckOrderBtn = root.findViewById(R.id.cart_page_check_order_btn);
        cartPageListAdapter = new CartPageListAdapter(root.getContext(), cartItemList);

        if (cartItemList.size() == 0) {
            cartTitle.setText("您的購物車是空的");
            cartCheckOrderBtn.setEnabled(false);
        } else {
            cartTitle.setText("購物車內容");
            cartCheckOrderBtn.setEnabled(true);
        }

        cartListView.setAdapter(cartPageListAdapter);
        cartListView.setOnItemClickListener(listViewItemClickListener);
        cartCheckOrderBtn.setOnClickListener(checkOrderBtnClickListener);

        return root;
    }

    @Override
    public void onResume() {
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
        Toast.makeText(root.getContext(), "Please sign in again", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(root.getContext(), SignInActivity.class);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
        );
        startActivity(intent);
    }

    View.OnClickListener checkOrderBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // 到結帳頁面
            Intent checkoutIntent = new Intent(getActivity(), CheckoutActivity.class);
            startActivity(checkoutIntent);
        }
    };

    AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // 到編輯頁面
            // UI還沒做
        }
    };
}