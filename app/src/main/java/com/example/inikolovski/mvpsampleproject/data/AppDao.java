package com.example.inikolovski.mvpsampleproject.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

import static com.example.inikolovski.mvpsampleproject.data.DbConfig.BOOK_TABLE_NAME;
import static com.example.inikolovski.mvpsampleproject.data.DbConfig.BOOK_TITLE_COLUMN;

@Dao
interface AppDao {
    @Query("SELECT * FROM " + BOOK_TABLE_NAME)
    List<Book> getAll();

    @Query("SELECT * FROM " + BOOK_TABLE_NAME)
    Flowable<List<Book>> getAllBooks();

    @Query("SELECT * FROM " + BOOK_TABLE_NAME + " WHERE " + BOOK_TITLE_COLUMN + " LIKE :search ")
    Book findBookWithTitle(String search);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(Book book);

    @Delete
    void deleteBook(Book book);
}
