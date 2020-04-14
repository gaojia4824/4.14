package com.hh.mvp_bao_4_8.di.component;

import android.content.SharedPreferences;

import com.hh.mvp_bao_4_8.di.module.AppModule;
import com.hh.mvp_bao_4_8.mvp.model.OkManagerModule;
import com.hh.mvp_bao_4_8.mvp.model.api.ApiService;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = {AppModule.class, OkManagerModule.class})
public interface AppComponent {
    SharedPreferences provideSp();
    OkHttpClient provideOk();
    ApiService provideApiService();
}
