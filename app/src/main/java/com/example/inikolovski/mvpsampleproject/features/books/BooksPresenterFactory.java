package com.example.inikolovski.mvpsampleproject.features.books;


import com.example.inikolovski.mvpsampleproject.common.PresenterFactory;
import com.example.inikolovski.mvpsampleproject.common.util.schedulers.BaseSchedulerProvider;
import com.example.inikolovski.mvpsampleproject.data.BaseRepository;

class BooksPresenterFactory implements PresenterFactory<BookPresenter> {
    private final BaseRepository repository;
    private final BaseSchedulerProvider baseSchedulerProvider;

    BooksPresenterFactory(BaseRepository repository, BaseSchedulerProvider baseSchedulerProvider) {
        this.repository = repository;
        this.baseSchedulerProvider = baseSchedulerProvider;
    }

    @Override
    public BookPresenter create() {
        return new BookPresenter(repository, baseSchedulerProvider);
    }
}
