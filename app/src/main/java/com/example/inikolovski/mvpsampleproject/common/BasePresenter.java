package com.example.inikolovski.mvpsampleproject.common;


public interface BasePresenter<T> {
    void registerView(T view);

    void unregisterView();

    void onDestroyed();
}
