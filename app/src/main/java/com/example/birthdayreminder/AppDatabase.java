package com.example.birthdayreminder;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Birthday.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BirthdayDao getBirthdayDao();
}
