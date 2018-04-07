package com.example.inikolovski.mvpsampleproject.features.start;


import android.support.annotation.NonNull;

import com.example.inikolovski.mvpsampleproject.common.util.schedulers.BaseSchedulerProvider;
import com.example.inikolovski.mvpsampleproject.data.BaseRepository;
import com.example.inikolovski.mvpsampleproject.data.Book;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;

import static com.example.inikolovski.mvpsampleproject.common.util.Preconditions.checkNotNull;

class MainPresenter implements MainContract.Presenter {
    @NonNull
    private final BaseRepository repository;

    @NonNull
    private final BaseSchedulerProvider schedulerProvider;

    @NonNull
    private final CompositeDisposable compositeDisposable;

    private MainContract.View view;

    MainPresenter(@NonNull BaseRepository repository,
                  @NonNull BaseSchedulerProvider schedulerProvider) {
        this.repository = checkNotNull(repository, "mainRepository cannot be null");
        this.schedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void registerView(MainContract.View view) {
        this.view = checkNotNull(view, "mainView cannot be null");
    }

    @Override
    public void unregisterView() {
        view = null;
    }

    @Override
    public void onDestroyed() {
        compositeDisposable.clear();
    }

    @Override
    public void addBook(final Book book) {
        Disposable disposable = repository.insertBook(book)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onError(Throwable t) {
                        if (isViewAttached()) {
//                            todo: use error utils
                            view.onFailure(String.format("Book not inserted, reason: %s ", t.getMessage()));
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (isViewAttached()) {
                            view.successfullyAddedBook(book.getTitle());
                        }
                    }
                });

        compositeDisposable.add(disposable);
    }

    private boolean isViewAttached() {
        return view != null;
    }
}
