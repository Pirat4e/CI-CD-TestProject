package com.example.inikolovski.mvpsampleproject.data;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface BaseRepository {

    Flowable<List<Book>> getAllBooks();

    Observable deleteBook(final Book book);

    Observable insertBook(final Book book);
}
