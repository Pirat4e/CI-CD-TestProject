package com.example.inikolovski.mvpsampleproject.features.start;


import com.example.inikolovski.mvpsampleproject.common.BasePresenter;
import com.example.inikolovski.mvpsampleproject.common.BaseView;
import com.example.inikolovski.mvpsampleproject.data.Book;

interface MainContract {

    interface View extends BaseView {
        void successfullyAddedBook(String title);
    }

    interface Presenter extends BasePresenter<MainContract.View> {
        void addBook(Book book);
    }
}
