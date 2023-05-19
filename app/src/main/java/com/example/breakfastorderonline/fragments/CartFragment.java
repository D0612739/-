package com.example.breakfastorderonline.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.breakfastorderonline.R;
import com.example.breakfastorderonline.utils.models.Cart;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private View root;

    private ArrayList<Cart> cartList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_cart, container, false);

        return root;
    }
}