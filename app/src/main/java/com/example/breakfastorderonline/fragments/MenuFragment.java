package com.example.breakfastorderonline.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.breakfastorderonline.R;
import com.example.breakfastorderonline.orderdrink;
import com.example.breakfastorderonline.ordereggroll;
import com.example.breakfastorderonline.orderhamburger;
import com.example.breakfastorderonline.ordersandwich;

public class MenuFragment extends Fragment {


    /*CartFragment CartFragmentAction;
    MemberFragment MemberFragmentAction;
    NotificationFragment NotificationFragmentAction;*/
    private View root;

    private ImageView ivHamburger;
    private TextView tvHamburger;
    private Spinner spnHamburger;
    private ImageView ivSankwich;
    private TextView tvSandwich;
    private  Spinner spnSandwich;
    private ImageView ivEggroll;
    private TextView tvEggroll;
    private Spinner spnEggroll;
    private ImageView ivDrink;
    private TextView tvDrink;
    private Spinner spnDrink;
    private Button btnHamburger;
    private Button btnSandwich;
    private Button btnEggroll;
    private Button btnDrink;
    private String temp;
    String [] hamburgerString = new String[] {"豬肉 $35","牛肉 $35","鮪魚 $35","香雞 $35"};
    //List<String> hamburger;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_menu, container, false);

        ivHamburger = root.findViewById(R.id.iv_hamburger);
        btnHamburger = root.findViewById(R.id.btn_hamburger);
        btnSandwich = root.findViewById(R.id.btn_sandwich);
        btnEggroll = root.findViewById(R.id.btn_eggroll);
        btnDrink = root.findViewById(R.id.btn_drink);
        Spinner spnHamburger = (Spinner) root.findViewById(R.id.spn_hamburger);
        Spinner spnSandwich = (Spinner) root.findViewById(R.id.spn_sandwich);
        Spinner spnEggroll = (Spinner) root.findViewById(R.id.spn_eggroll);
        Spinner spnDrink = (Spinner) root.findViewById(R.id.spn_drink);
        btnHamburger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HamburgerOrder(v);
            }
        });
        btnSandwich.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SandwichOrder(v);
            }
        });
        btnEggroll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EggrollOrder(v);
            }
        });
        btnDrink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DrinkOrder(v);
            }
        });
        /*CartFragmentAction = new CartFragment();
        MemberFragmentAction = new MemberFragment();
        NotificationFragmentAction = new NotificationFragment();*/
        /*hamburger = new ArrayList<>();
        hamburger.add("豬肉 $35");
        hamburger.add("牛肉 $35");
        hamburger.add("鮪魚 $35");
        hamburger.add("香雞 $35");*/


        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
          (this, android.R.layout.simple_spinner_item,hamburgerString);*/


        /*ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(MenuFragment.this,
          android.R.layout.simple_spinner_item);*/


        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.fragment_menu,hamburger);*/

        /*arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHamburger.setAdapter(arrayAdapter);
        spnHamburger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


        ArrayAdapter<CharSequence> adapterHamburger = ArrayAdapter.createFromResource(root.getContext(),
                R.array.hamburger, android.R.layout.simple_spinner_item);
        adapterHamburger.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHamburger.setAdapter(adapterHamburger);
        spnHamburger.setOnItemSelectedListener(spnHamburger.getOnItemSelectedListener());

        ArrayAdapter<CharSequence> adapterSandwich = ArrayAdapter.createFromResource(root.getContext(),
                R.array.sandwich, android.R.layout.simple_spinner_item);
        adapterSandwich.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSandwich.setAdapter(adapterSandwich);
        spnSandwich.setOnItemSelectedListener(spnSandwich.getOnItemSelectedListener());

        ArrayAdapter<CharSequence> adapterEggroll = ArrayAdapter.createFromResource(root.getContext(),
                R.array.eggroll, android.R.layout.simple_spinner_item);
        adapterEggroll.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEggroll.setAdapter(adapterEggroll);
        spnEggroll.setOnItemSelectedListener(spnEggroll.getOnItemSelectedListener());

        ArrayAdapter<CharSequence> adapterDrink = ArrayAdapter.createFromResource(root.getContext(),
                R.array.drink, android.R.layout.simple_spinner_item);
        adapterDrink.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDrink.setAdapter(adapterDrink);
        spnDrink.setOnItemSelectedListener(spnDrink.getOnItemSelectedListener());

        //Toast.makeText(root.getContext(),spnHamburger.toString(), Toast.LENGTH_LONG).show();


        temp = (String)spnHamburger.getSelectedItem();
        //Toast.makeText(root.getContext(),temp, Toast.LENGTH_LONG).show();
        btnHamburger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                temp = (String)spnHamburger.getSelectedItem();
                HamburgerOrder(v);
            }
        });
        btnSandwich.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                temp = (String)spnSandwich.getSelectedItem();
                SandwichOrder(v);
            }
        });
        btnEggroll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                temp = (String)spnEggroll.getSelectedItem();
                EggrollOrder(v);
            }
        });
        btnDrink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                temp = (String)spnDrink.getSelectedItem();
                DrinkOrder(v);
            }
        });

        /*ArrayAdapter adapter = new ArrayAdapter(this,
          android.R.layout.simple_dropdown_item_1line,
          new String[]{"Taiwan","Korean","Japan"});*/


        // Inflate the layout for this fragment
        return root;
    }
    /*public void onClick(View v){

        Toast.makeText(root.getContext(), "onclick" , Toast.LENGTH_LONG).show();
        if (v.getId()==R.id.btn_hamburger){
            Intent intent = new Intent(getActivity(), MenuOrderFragment.class);
            startActivity(intent);
        }

    }*/

    public void HamburgerOrder(View v) {
        //Toast.makeText(root.getContext(),temp, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), orderhamburger.class);
        intent.putExtra("mealname",temp);
        startActivity(intent);
    }
    public void SandwichOrder(View v) {
        Intent intent = new Intent(getActivity(), ordersandwich.class);
        intent.putExtra("mealname",temp);
        startActivity(intent);
    }
    public void EggrollOrder(View v) {
        Intent intent = new Intent(getActivity(), ordereggroll.class);
        intent.putExtra("mealname",temp);
        startActivity(intent);
    }
    public void DrinkOrder(View v) {
        Intent intent = new Intent(getActivity(), orderdrink.class);
        intent.putExtra("mealname",temp);
        startActivity(intent);
    }
}