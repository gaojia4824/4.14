package com.hh.mvp_bao_4_8.mvp.ui.common;

import android.os.Bundle;
import android.view.View;

import com.hh.mvp_bao_4_8.base.BaseFragment;
import com.hh.mvp_bao_4_8.base.BasePresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class LazyFragment extends BaseFragment {
    //Fragment是否初始化   默认没有被初始化
    private boolean mInitView=false;
    //Fragment是否加载完数据   默认无
    private boolean mHasLoadMore=false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInitView=true;
        initLazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        initLazyLoad();
    }
    protected void initLazyLoad(){
        if (!mInitView){
            return;
        }
        if (getUserVisibleHint()){
            lazyLoad();
            //代表加载完数据
            mHasLoadMore=true;
            //Fragment代表对用户不可见
        }else{
            //如果有数据，停止加载
            if (mHasLoadMore){
                stopLazyLoad();
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mInitView=false;
        mHasLoadMore=false;
    }

    protected abstract void stopLazyLoad();

    protected abstract void lazyLoad();

    @Override
    protected abstract void initInject();

    @Override
    protected abstract BasePresenter createPresenter();

    @Override
    protected abstract int getLayoutId();

    @Override
    public abstract void stateSuccess(Object o);

    @Override
    public abstract void stateError(String msg);

}
