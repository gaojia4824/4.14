package com.hh.mvp_bao_4_8.di.component;

import com.hh.mvp_bao_4_8.di.annotation.PerSingleton;
import com.hh.mvp_bao_4_8.mvp.model.RxOperateImpl;

import dagger.Component;

@PerSingleton
@Component(dependencies =AppComponent.class)
public interface RxOperateComponent {
    void inject(RxOperateImpl rxOperate);
}
