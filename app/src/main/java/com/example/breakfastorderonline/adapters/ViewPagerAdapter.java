package com.example.breakfastorderonline.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.breakfastorderonline.fragments.CartFragment;
import com.example.breakfastorderonline.fragments.MemberFragment;
import com.example.breakfastorderonline.fragments.MenuFragment;
import com.example.breakfastorderonline.fragments.NotificationFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MenuFragment();
            case 1:
                return new CartFragment();
            case 2:
                return new NotificationFragment();
            case 3:
                return new MemberFragment();
            default:
                break;
        }
        return new MenuFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
