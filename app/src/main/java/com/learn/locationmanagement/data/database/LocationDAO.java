package com.learn.locationmanagement.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.learn.locationmanagement.data.domain.Location;

import java.util.List;

@Dao
public interface LocationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveLocations(List<Location> locations);

    @Update
    void update(Location location);

    @Query("SELECT * FROM Location")
    List<Location> getAllLocations();
}
