package com.learn.locationmanagement.data.repository.location;

import com.learn.locationmanagement.data.database.LocationDAO;
import com.learn.locationmanagement.data.domain.Location;
import com.learn.locationmanagement.data.mapper.LocationMapper;
import com.learn.locationmanagement.model.location.detail.LocationDetail;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class LocationLocalDataSource implements LocationDataSource.Local {
    private final LocationDAO locationDAO;
    private final Executor executor;

    @Inject
    public LocationLocalDataSource(LocationDAO locationDAO, Executor executor) {
        this.locationDAO = locationDAO;
        this.executor = executor;
    }

    @Override
    public void getFavoriteLocations(LocationRepository.DataLoadCallBack<List<FavoriteLocation>> callBack) {
        Runnable runnable = () -> {
            List<Location> locations = locationDAO.getAllLocations();
            if (!locations.isEmpty()) {
                ArrayList<FavoriteLocation> favoriteLocations = new ArrayList<>();
                for (Location location : locations) {
                    favoriteLocations.add(LocationMapper.toFavoriteLocation(location));
                }
                callBack.onDataLoaded(favoriteLocations);
            } else {
                callBack.onDataNotAvailable();
            }
        };
        executor.execute(runnable);
    }

    @Override
    public void getLocationDetail(String locationId, LocationRepository.DataLoadCallBack<LocationDetail> callBack) {
        Runnable runnable = () -> {
            Location location = locationDAO.getLocationById(locationId);
            if (location != null) {
                if (location.getLng() == Location.NO_LOCATION || location.getLat() == Location.NO_LOCATION) {
                    callBack.onDataNotAvailable();
                } else {
                    callBack.onDataLoaded(LocationMapper.toLocationDetail(location));
                }
            } else {
                callBack.onDataNotAvailable();
            }
        };
        executor.execute(runnable);
    }

    @Override
    public void saveFavoriteLocations(List<FavoriteLocation> locations) {
        Runnable runnable = () -> {
            List<Location> mLocations = new ArrayList<>();
            for (FavoriteLocation favoriteLocation : locations) {
                mLocations.add(LocationMapper.toDomain(favoriteLocation));
            }
            locationDAO.saveLocations(mLocations);
        };
        executor.execute(runnable);
    }

    @Override
    public void saveLocationDetail(String locationId, LocationDetail detail) {
        Runnable runnable = () -> {
            Location location = locationDAO.getLocationById(locationId);
            location.setDetail(detail);
            locationDAO.update(location);
        };
        executor.execute(runnable);
    }
}
