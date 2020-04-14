package com.hh.mvp_bao_4_8.base;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
//关联v层生命周期和网络开关容器存储的所有网络开关
public abstract class BasePresenter<V extends IBaseView<T>,T> implements IBasePresenter<T>{

    private WeakReference <V> mWr;
    private CompositeDisposable mCompositeDisposable;

    public void attachView(V view){
        if (mWr==null){
            mWr = new WeakReference <>(view);
        }
    }
    public void detachView(){
        if (mWr!=null){
            mWr.clear();
            mWr=null;
        }
        deleteDisposable();
    }
    public void addDisposable(Disposable disposable){
        if (mCompositeDisposable==null){
            mCompositeDisposable = new CompositeDisposable();
            mCompositeDisposable.add(disposable);
        }
    }
    private void deleteDisposable() {
        if (mCompositeDisposable!=null&&!mCompositeDisposable.isDisposed()){
            mCompositeDisposable.dispose();
            mCompositeDisposable.clear();
            mCompositeDisposable=null;
        }
    }

    @Override
    public void start() {
    }
    @Override
    public void start(T t) {
    }
}
