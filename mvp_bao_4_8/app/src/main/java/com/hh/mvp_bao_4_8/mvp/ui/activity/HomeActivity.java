package com.hh.mvp_bao_4_8.mvp.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hh.mvp_bao_4_8.R;
import com.hh.mvp_bao_4_8.base.BaseActivity;

import java.util.List;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.home_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.home_navigation)
    BottomNavigationView mBottomNv;
    @Override
    protected void initListenner() {
        mBottomNv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.item_home:
                        Log.e("TAG","000");
                        switchTab(0);
                        break;
                    case R.id.item_navigation:
                        Log.e("TAG","111");
                        switchTab(1);
                        break;
                    case R.id.item_tixi:
                        Log.e("TAG","222");
                        switchTab(2);
                        break;
                    case R.id.item_gongzhonghao:
                        Log.e("TAG","333");
                        switchTab(3);
                        break;
                }
                return true;
            }

            private void switchTab(int i) {
                mViewPager.setCurrentItem(i);
            }
        });
    }

    @Override
    protected void onViewCreated() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}
