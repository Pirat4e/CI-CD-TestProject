package com.example.inikolovski.mvpsampleproject.data;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class AppDataRepository implements BaseRepository {

    @Override
    public Flowable<List<Book>> getAllBooks() {
        return DbHelper.getInstance().getAllBooks();
    }

    @Override
    public Observable deleteBook(final Book book) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                DbHelper.getInstance().deleteBook(book);
                e.onComplete();
            }
        });
    }

    @Override
    public Observable insertBook(final Book book) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                DbHelper.getInstance().insertBook(book);
                e.onComplete();
            }
        });
    }
}
