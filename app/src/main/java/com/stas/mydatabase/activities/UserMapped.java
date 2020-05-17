package com.stas.mydatabase.activities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.stas.mydatabase.database.BaseEntity;

import java.util.Arrays;

public class UserMapped {
    public UserMapped(String firstName, String lastName, boolean isNiceName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isNiceName = isNiceName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isNiceName() {
        return isNiceName;
    }

    private String firstName;

    private String lastName;

    private boolean isNiceName;

    @NonNull
    @Override
    public String toString() {
        return "firstName: " + firstName + " lastName: " + lastName + " isNiceName:  f" + isNiceName;
    }
}
