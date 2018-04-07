package com.example.inikolovski.mvpsampleproject.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static com.example.inikolovski.mvpsampleproject.data.DbConfig.BOOK_AUTHOR_COLUMN;
import static com.example.inikolovski.mvpsampleproject.data.DbConfig.BOOK_GENRE_COLUMN;
import static com.example.inikolovski.mvpsampleproject.data.DbConfig.BOOK_TABLE_NAME;
import static com.example.inikolovski.mvpsampleproject.data.DbConfig.BOOK_TITLE_COLUMN;

@Entity(tableName = BOOK_TABLE_NAME,
        indices = {@Index(value = {BOOK_TITLE_COLUMN}, unique = true)})
public class Book {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = BOOK_TITLE_COLUMN)
    private final String title;
    @ColumnInfo(name = BOOK_AUTHOR_COLUMN)
    private final String authorName;
    @ColumnInfo(name = BOOK_GENRE_COLUMN)
    private final String genre;

    public Book(String title, String authorName, String genre) {
        this.title = title;
        this.authorName = authorName;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getGenre() {
        return genre;
    }

    public void setId(int id) {
        this.id = id;
    }
}
