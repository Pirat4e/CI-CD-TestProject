package com.example.inikolovski.mvpsampleproject.data;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Flowable;

import static com.example.inikolovski.mvpsampleproject.data.DbConfig.DATABASE_NAME;

public final class DbHelper {
    @Nullable
    private static DbHelper INSTANCE;

    private DbHelper() {
    }

    public static synchronized DbHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbHelper();
        }
        return INSTANCE;
    }

    private AppDatabase appDatabase;

    public void init(Context context) {
        appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, DATABASE_NAME).build();
    }

    Flowable<List<Book>> getAllBooks() {
        return appDatabase.appDao().getAllBooks();
    }

    void insertBook(Book book) {
        appDatabase.appDao().insertBook(book);
    }

    void deleteBook(final Book book) {
        appDatabase.appDao().deleteBook(book);
    }
}
