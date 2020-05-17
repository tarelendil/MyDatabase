package com.stas.mydatabase.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.stas.mydatabase.activities.UserMapped;
import com.stas.mydatabase.application.App;
import com.stas.mydatabase.security.ByteUtils;
import com.stas.mydatabase.security.DataSecurity;
import com.stas.mydatabase.security.exceptions.DataTypeForEncryptionNotSupportedException;
import com.stas.mydatabase.security.exceptions.EncryptionFailedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseManager {
    private static DatabaseManager instance = new DatabaseManager();
    private DataSecurity dataSecurity = DataSecurity.getInstance();

    @NonNull
    private AppDatabase appDatabase;

    public static DatabaseManager getInstance() {
        return instance;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user ADD COLUMN isNiceName BOOLEAN");
        }
    };

    private DatabaseManager() {
        appDatabase = Room.databaseBuilder(App.getAppContext(),
                AppDatabase.class, "AppDatabase")
                .fallbackToDestructiveMigration()
//                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build();
    }


    public void insertUsers(List<UserMapped> usersMapped) throws EncryptionFailedException, DataTypeForEncryptionNotSupportedException {
        List<User> users = new ArrayList<>();
        for (UserMapped userMapped : usersMapped) {
            users.add(new User(dataSecurity.encryptAndCrc(userMapped.getFirstName(), userMapped.getLastName(), userMapped.isNiceName()), userMapped.getFirstName(), userMapped.getLastName(), userMapped.isNiceName()));
        }
        Log.i("qwe", users.toString());
        appDatabase.userDao().insertAll(users);
    }


    public List<User> getUsers() {
        return appDatabase.userDao().getAll();
    }
}
