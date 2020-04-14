package com.hh.mvp_bao_4_8.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.hh.mvp_bao_4_8.app.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private App mApp;
    public AppModule(App app){
        this.mApp=app;
    }
    @Provides
    @Singleton
    public SharedPreferences provideSp(){
        return mApp.getSharedPreferences("config", Context.MODE_PRIVATE);
    }
}

