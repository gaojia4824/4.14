package com.hh.mvp_bao_4_8.app;

import android.app.Activity;
import android.app.Application;
import android.os.Process;

import com.hh.mvp_bao_4_8.di.component.DaggerAppComponent;
import com.hh.mvp_bao_4_8.di.module.AppModule;

import java.util.HashSet;
import java.util.Set;

public class App extends Application {
    public static App mInstance;
    private static  DaggerAppComponent mDaggerAppComponent;
    private HashSet <Activity> activities;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        initInject();
    }

    private void initInject() {
        if (mDaggerAppComponent==null){
            mDaggerAppComponent=(DaggerAppComponent) DaggerAppComponent.builder()
                    .appModule(new AppModule(this)).build();
        }
    }
    public static DaggerAppComponent daggerAppComponent() {
        return mDaggerAppComponent;
    }


    public static synchronized App getInstance() {
        return mInstance;
    }


    public void addActivity(Activity act) {
        if (activities == null) {
            activities = new HashSet <>();
            activities.add(act);
        }
    }

    public void removeActivity() {
        if (activities != null) {
            for (Activity act : activities) {
                act.finish();
            }
        }
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

}
