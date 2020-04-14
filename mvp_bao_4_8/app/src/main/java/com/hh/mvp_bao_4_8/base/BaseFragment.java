package com.hh.mvp_bao_4_8.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
//该类负责  拿到p层对象向p层发送请求数据的指令,拿到p层对象关联v层声明周期,拿到p层对象释放v层引用
public abstract class BaseFragment<P extends BasePresenter ,T>
        extends Fragment implements IBaseView<T> {
    private Unbinder unbinder;
    private P mPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId=getLayoutId();
        View view=null;
        if (layoutId!=0){
            view=inflater.inflate(layoutId,container,false);
        }
         return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (mPresenter==null){
            mPresenter=createPresenter();
        }
        mPresenter.attachView(this);
        initInject();
    }
    protected abstract void initInject();
    protected abstract P createPresenter();
    protected abstract int getLayoutId();

    public P getmPresenter(){
       if (mPresenter!=null){
           return mPresenter;
       }
       return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null){
            unbinder.unbind();
            unbinder=null;
        }
        //p层释放v层引用
        if (mPresenter!=null){
            mPresenter.detachView();
            mPresenter=null;
        }
    }
}
