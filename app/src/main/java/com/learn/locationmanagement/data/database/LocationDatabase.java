package com.learn.locationmanagement.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.learn.locationmanagement.data.domain.Location;

@Database(
        entities = {Location.class},
        version = 1
)
public abstract class LocationDatabase extends RoomDatabase {
    private static LocationDatabase instance;

    public static synchronized LocationDatabase getInstance(Context applicationContext) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            applicationContext,
                            LocationDatabase.class,
                            "LocationDatabase.db")
                    // recreate database when column change, and don't care about data
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract LocationDAO getLocationDAO();
}
