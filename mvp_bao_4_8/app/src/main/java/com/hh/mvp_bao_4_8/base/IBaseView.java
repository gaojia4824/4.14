package com.hh.mvp_bao_4_8.base;

public interface IBaseView<T> {
    //V层接口
    void stateSuccess(T t);
    void stateError(String msg);
}
