package com.stas.mydatabase.database;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.stas.mydatabase.application.App;

public class DatabaseManager {
    private static DatabaseManager instance = new DatabaseManager();

    @NonNull
    private AppDatabase appDatabase;

    public static DatabaseManager getInstance() {
        return instance;
    }


    private DatabaseManager() {
        appDatabase = Room.databaseBuilder(App.getAppContext(),
                AppDatabase.class, "AppDatabase").build();
    }

    public void insertUser() {
        appDatabase.userDao().insertAll(new User(new byte[]{1, 2, 3}, "vashe", "huina"));
    }
}
