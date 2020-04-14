package com.hh.mvp_bao_4_8.mvp.ui.fragment;

import android.util.Log;

import com.hh.mvp_bao_4_8.R;
import com.hh.mvp_bao_4_8.base.BasePresenter;
import com.hh.mvp_bao_4_8.mvp.presenter.HomePresenter;
import com.hh.mvp_bao_4_8.mvp.ui.common.LazyFragment;

public class HomeFragment extends LazyFragment {
    private int mType;
    public HomeFragment(int type){
        this.mType=type;
    }
    @Override
    protected void stopLazyLoad() {
        switch (mType){
            case 0:
                Log.e("TAG","fragment1停止加载。");
                break;
            case 1:
                Log.e("TAG","fragment2停止加载。");
                break;
            case 2:
                Log.e("TAG","fragment2停止加载。");
                break;
            case 3:
                Log.e("TAG","fragment3停止加载。");
                break;

        }
    }

    @Override
    protected void lazyLoad() {
        getmPresenter().start(mType);
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        Integer layoutId=switchayout(mType);
        if (layoutId!=null){
            return layoutId;
        }
        return 0;
    }

    private static Integer switchayout(int mType) {
        switch (mType){
            case 0:
                return R.layout.fragment_home;
            case 1:
                return R.layout.fragment_navigation;
            case 2:
                return R.layout.fragment_tixi;
            case 3:
                return R.layout.fragment_gongzhonghao;
        }
        return null;
    }

    @Override
    public void stateSuccess(Object o) {

    }

    @Override
    public void stateError(String msg) {

    }
}
