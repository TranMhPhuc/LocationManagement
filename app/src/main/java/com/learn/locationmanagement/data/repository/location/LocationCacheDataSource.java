package com.learn.locationmanagement.data.repository.location;

import com.learn.locationmanagement.data.domain.Location;
import com.learn.locationmanagement.data.mapper.LocationMapper;
import com.learn.locationmanagement.model.location.detail.LocationDetail;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class LocationCacheDataSource implements LocationDataSource.Local{
    private final HashMap<String, Location> cachedLocations = new LinkedHashMap<>();

    @Override
    public void getFavoriteLocations(LocationRepository.DataLoadCallBack<List<FavoriteLocation>> callBack) {
        if (!cachedLocations.isEmpty()) {
            List<FavoriteLocation> favoriteLocations = new ArrayList<>();
            for (Location location : cachedLocations.values()) {
                favoriteLocations.add(LocationMapper.toFavoriteLocation(location));
            }
            callBack.onDataLoaded(favoriteLocations);
        } else {
            callBack.onDataNotAvailable();
        }
    }

    @Override
    public void getLocationDetail(String locationId, LocationRepository.DataLoadCallBack<LocationDetail> callBack) {
        Location location = cachedLocations.get(locationId);
        if (location != null) {
            if (location.getLat() == Location.NO_LOCATION || location.getLng() == Location.NO_LOCATION) {
                callBack.onDataNotAvailable();
            } else {
                callBack.onDataLoaded(LocationMapper.toLocationDetail(location));
            }
        } else {
            callBack.onDataNotAvailable();
        }
    }

    @Override
    public void saveFavoriteLocations(List<FavoriteLocation> locations) {
        cachedLocations.clear();
        for (FavoriteLocation favoriteLocation: locations) {
            cachedLocations.put(favoriteLocation.getId(), LocationMapper.toDomain(favoriteLocation));
        }
    }

    @Override
    public void saveLocationDetail(String locationId, LocationDetail detail) {
        Location location = cachedLocations.get(locationId);
        if (location != null) {
            location.setDetail(detail);
        }
    }
}
