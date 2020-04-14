package com.hh.mvp_bao_4_8.mvp.ui.adapter;

import com.hh.mvp_bao_4_8.mvp.ui.fragment.HomeFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomeVpAdapter extends FragmentPagerAdapter {
    private List<HomeFragment> mFragments;
    public HomeVpAdapter(@NonNull FragmentManager fm,List<HomeFragment> fragments) {
        super(fm);
        this.mFragments=fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
