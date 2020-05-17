package com.stas.mydatabase.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity
public class User extends BaseEntity {
    public User(byte[] crc, String firstName, String lastName, boolean isNiceName) {
        super(crc);
        this.firstName = firstName;
        this.lastName = lastName;
        this.isNiceName = isNiceName;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    public boolean isNiceName;

    @NonNull
    @Override
    public String toString() {
        return "firstName: " + firstName + " lastName: " + lastName + " isNiceName: " + isNiceName + "\ncrc: " + Arrays.toString(crc);
    }
}
