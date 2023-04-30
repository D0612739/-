package com.example.breakfastorderonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.view.MenuItem;
import android.widget.Toast;

import com.example.breakfastorderonline.adapters.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    // Used on BottomNavigationView
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        boolean isSignedIn = sharedPreferences.getBoolean("signed_in", false);
        if(!isSignedIn) {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        }
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        };

        Toast.makeText(MainActivity.this, "onCreate method is called", Toast.LENGTH_SHORT).show();
        bottomNavigationView = findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.viewPager);

        // init ViewPager
        viewPagerAdapter = new ViewPagerAdapter(this);

        // setup ViewPager
        viewPager.setAdapter(viewPagerAdapter);
        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);
        viewPager.registerOnPageChangeCallback(onPageChangeCallback);
    }
    NavigationBarView.OnItemSelectedListener onItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.menu_navItem) {
                viewPager.setCurrentItem(0);
            } else if (id == R.id.cart_navItem) {
                viewPager.setCurrentItem(1);
            } else if (id == R.id.notification_navItem) {
                viewPager.setCurrentItem(2);
            } else if (id == R.id.member_navItem) {
                viewPager.setCurrentItem(3);
            }
            return true;
        }
    };

    ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    bottomNavigationView.getMenu().findItem(R.id.menu_navItem).setChecked(true);
                    break;
                case 1:
                    bottomNavigationView.getMenu().findItem(R.id.cart_navItem).setChecked(true);
                    break;
                case 2:
                    bottomNavigationView.getMenu().findItem(R.id.notification_navItem).setChecked(true);
                    break;
                case 3:
                    bottomNavigationView.getMenu().findItem(R.id.member_navItem).setChecked(true);
                    break;
                default:
                    break;
            }
            super.onPageSelected(position);
        }
    };
}