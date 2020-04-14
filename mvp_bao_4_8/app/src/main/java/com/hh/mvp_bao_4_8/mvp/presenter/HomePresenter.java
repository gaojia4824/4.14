package com.hh.mvp_bao_4_8.mvp.presenter;

import android.util.Log;

import com.hh.mvp_bao_4_8.app.App;
import com.hh.mvp_bao_4_8.base.BasePresenter;
import com.hh.mvp_bao_4_8.callback.IDataCallBack;
import com.hh.mvp_bao_4_8.mvp.model.RxOperateImpl;
import com.hh.mvp_bao_4_8.mvp.ui.fragment.HomeFragment;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

public class HomePresenter<T> extends BasePresenter {
//    @Inject
//    OkHttpClient okHttpClient;
//    public HomePresenter(){
//        DaggerHomeComponent.builder()
//                .appComponent(App.daggerAppComponent())
//                .build();
////                .inject(this);
//    }
    private RxOperateImpl mImpl;
    private HomeFragment mHomeFragment;
    public HomePresenter(HomeFragment homeFragment){
        this.mHomeFragment=homeFragment;
       mImpl= new RxOperateImpl();
    }
    //向M层请求数据
    @Override
    public void start(Object obj){
        super.start(obj);
//        Log.e("TAG",okHttpClient.toString());
        if (obj instanceof Integer){
            Integer  type= (Integer) obj;
            switch (type){
                case 0:
//                    Log.e("TAG","第一个fragment加载数据。");
                    mImpl.requestFormData("https://wanandroid.com/wxarticle/chapters/json", new IDataCallBack <T>() {
                        @Override
                        public void onStateSucess(T t) {
                            if (t instanceof ResponseBody){
                                ResponseBody body= (ResponseBody) t;
                                String jsonStr=null;
                                try {
                                    jsonStr=body.string();
                                    Log.e("TAG",jsonStr+"========");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    mHomeFragment.stateError(e.getMessage());
                                }
                            }
                        }
                        @Override
                        public void onStateError(String msg) {
                            mHomeFragment.stateError(msg);
                        }
                        @Override
                        public void onResponseDisposable(Disposable disposable) {
                                addDisposable(disposable);
                        }
                    });
                    break;
                case 1:
                    Log.e("TAG","第二个fragment加载数据。");
                    break;
                case 2:
                    Log.e("TAG","第三个fragment加载数据。");
                    break;
                case 3:
//                    Log.e("TAG","第四个fragment加载数据。");
                    mImpl.requestFormData("https://www.wanandroid.com/banner/json", new IDataCallBack <T>() {
                        @Override
                        public void onStateSucess(T t) {
                            if (t instanceof ResponseBody){
                                ResponseBody body= (ResponseBody) t;
                                String jsonStr=null;
                                try {
                                    jsonStr=body.string();
                                    Log.e("TAG",jsonStr+"========");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    mHomeFragment.stateError(e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onStateError(String msg) {
                            mHomeFragment.stateError(msg);
                        }

                        @Override
                        public void onResponseDisposable(Disposable disposable) {
                            addDisposable(disposable);
                        }
                    });
                    break;
            }
        }
    }
}
