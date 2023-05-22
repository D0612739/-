package com.example.breakfastorderonline.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.breakfastorderonline.R;

public class MenuOrderFragment extends Fragment {

    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_menu_order, container, false);

        return root;
    }
    public void onClick(View v){


    }
}