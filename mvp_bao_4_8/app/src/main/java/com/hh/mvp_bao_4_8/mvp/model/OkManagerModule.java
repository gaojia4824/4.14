package com.hh.mvp_bao_4_8.mvp.model;

import com.hh.mvp_bao_4_8.mvp.model.api.ApiService;
import com.hh.mvp_bao_4_8.mvp.model.api.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class OkManagerModule {
    //添加不同的拦截器
    @Provides
    @Singleton
    public OkHttpClient.Builder provideOkBuilder(){
        return new OkHttpClient.Builder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .readTimeout(6,TimeUnit.SECONDS);
    }
    @Provides
    @Singleton
    public OkHttpClient provideOkClient(){
        return provideOkBuilder().build();
    }
    @Provides
    @Singleton
    public Retrofit.Builder provideRetrofitBuilder(){
        return new Retrofit.Builder().baseUrl(Constants.BASE_URL);
    }
    @Provides
    @Singleton
    public Retrofit provideRetrofit(){
        OkHttpClient okHttpClient = provideOkClient();
        return provideRetrofitBuilder().client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    @Provides
    @Singleton
    public ApiService provideApiService(){
        return provideRetrofit().create(ApiService.class);
    }
}
