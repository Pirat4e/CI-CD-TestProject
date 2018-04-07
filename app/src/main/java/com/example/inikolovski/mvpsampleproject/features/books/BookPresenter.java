package com.example.inikolovski.mvpsampleproject.features.books;


import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.inikolovski.mvpsampleproject.common.util.schedulers.BaseSchedulerProvider;
import com.example.inikolovski.mvpsampleproject.data.BaseRepository;
import com.example.inikolovski.mvpsampleproject.data.Book;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.subscribers.DisposableSubscriber;

import static com.example.inikolovski.mvpsampleproject.common.util.Preconditions.checkNotNull;

class BookPresenter implements BookContract.Presenter {

    @NonNull
    private final BaseRepository repository;

    @NonNull
    private final BaseSchedulerProvider schedulerProvider;

    @NonNull
    private final CompositeDisposable compositeDisposable;

    private BookContract.View view;

    BookPresenter(@NonNull BaseRepository repository,
                  @NonNull BaseSchedulerProvider schedulerProvider) {
        this.repository = checkNotNull(repository, "booksRepository cannot be null");
        this.schedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void registerView(BookContract.View view) {
        this.view = checkNotNull(view, "booksView cannot be null");
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
    public void deleteBook(final Book book, final int position) {
        final Disposable disposable = repository.deleteBook(book)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        if (isViewAttached()) {
                            view.setLoadingIndicator(false);
                            view.successfullyDeletedBook(book.getTitle(), position);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            view.setLoadingIndicator(false);
                            view.onFailure(e.getMessage());
                        }
                    }
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public void loadAllBooks() {
        final Disposable disposable = repository.getAllBooks()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe(onSubscribe())
                .subscribeWith(new DisposableSubscriber<List<Book>>() {
                    @Override
                    public void onNext(List<Book> books) {
                        if (isViewAttached()) {
                            view.setLoadingIndicator(false);
                            view.showAllBooks(books);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (isViewAttached()) {
                            view.setLoadingIndicator(false);
//                            todo: use error utils
                            view.onFailure(t.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public void registerSwipeToRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAllBooks();
            }
        });
    }

    @NonNull
    private Consumer<Subscription> onSubscribe() {
        return new Consumer<Subscription>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Subscription subscription) throws Exception {
                if (isViewAttached())
                    view.setLoadingIndicator(true);
            }
        };
    }

    // todo: use null object pattern
    private boolean isViewAttached() {
        return view != null;
    }
}
