package com.example.inikolovski.mvpsampleproject.features.books;


import android.support.v4.widget.SwipeRefreshLayout;

import com.example.inikolovski.mvpsampleproject.common.BasePresenter;
import com.example.inikolovski.mvpsampleproject.common.BaseView;
import com.example.inikolovski.mvpsampleproject.data.Book;

import java.util.List;

interface BookContract {

    interface View extends BaseView {
        void showAllBooks(List<Book> books);

        void successfullyDeletedBook(String bookTitle, int positionInAdapter);

        void setLoadingIndicator(boolean active);
    }

    interface Presenter extends BasePresenter<BookContract.View> {
        void deleteBook(Book book, int positionInAdapter);

        void loadAllBooks();

        void registerSwipeToRefresh(SwipeRefreshLayout swipeRefreshLayout);
    }
}
