package com.example.inikolovski.mvpsampleproject.common;


public interface PresenterFactory<T extends BasePresenter> {
    T create();
}
