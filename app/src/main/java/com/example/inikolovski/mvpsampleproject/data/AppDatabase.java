package com.example.inikolovski.mvpsampleproject.data;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Book.class}, version = 1)
abstract class AppDatabase extends RoomDatabase{
    abstract AppDao appDao();
}
