package com.example.breakfastorderonline.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.breakfastorderonline.R;
import com.example.breakfastorderonline.adapters.MemberPageFeatureAdapter;

public class MemberFragment extends Fragment {

    private View root;  // root view of this fragment

    private final String[] memberPageFeatures = new String[]{"會員資料", "歷史訂單", "登出"};
    private final int[] memberPageFeatureIcons = new int[]{
            R.drawable.member_page_info_icon,
            R.drawable.member_page_orderhistory_icon,
            R.drawable.member_page_logout_icon
    };
    private ListView memberPageListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_member, container, false);
        // init views
        memberPageListView = root.findViewById(R.id.member_page_listView);
        // setup member page listview
        MemberPageFeatureAdapter memberPageFeatureAdapter = new MemberPageFeatureAdapter(
                root.getContext(), memberPageFeatures, memberPageFeatureIcons
        );
        memberPageListView.setAdapter(memberPageFeatureAdapter);
        memberPageListView.setOnItemClickListener(memberPageListViewItemClickListener);
        return root;
    }

    AdapterView.OnItemClickListener memberPageListViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // branch code here
            Toast.makeText(root.getContext(), memberPageFeatures[i], Toast.LENGTH_SHORT).show();
        }
    };
}