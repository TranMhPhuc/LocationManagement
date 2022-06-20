package com.learn.locationmanagement.data.repository.location;

import com.learn.locationmanagement.model.location.detail.LocationDetail;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

import java.util.List;

import javax.inject.Inject;

public class LocationRepositoryImpl implements LocationRepository {

    private final LocationDataSource.Remote locationRemote;
    private final LocationDataSource.Local locationLocal;
    private final LocationDataSource.Local cachedLocation;

    @Inject
    public LocationRepositoryImpl(LocationRemoteDataSource locationRemote, LocationLocalDataSource locationLocal, LocationCacheDataSource cachedLocation) {
        this.locationRemote = locationRemote;
        this.locationLocal = locationLocal;
        this.cachedLocation = cachedLocation;
    }

    @Override
    public void getFavoriteLocations(DataLoadCallBack<List<FavoriteLocation>> callBack) {
        if (callBack == null) return;
        cachedLocation.getFavoriteLocations(new DataLoadCallBack<List<FavoriteLocation>>() {
            @Override
            public void onDataLoaded(List<FavoriteLocation> data) {
                callBack.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                getFavoriteLocationsFromLocalDataSource(callBack);
            }
        });
    }

    private void getFavoriteLocationsFromLocalDataSource(DataLoadCallBack<List<FavoriteLocation>> callBack) {
        locationLocal.getFavoriteLocations(new DataLoadCallBack<List<FavoriteLocation>>() {
            @Override
            public void onDataLoaded(List<FavoriteLocation> data) {
                refreshLocationCache(data);
                callBack.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                getFavoriteLocationsFromRemoteDataSource(callBack);
            }
        });
    }

    private void refreshLocationCache(List<FavoriteLocation> data) {
        cachedLocation.saveFavoriteLocations(data);
    }

    private void getFavoriteLocationsFromRemoteDataSource(DataLoadCallBack<List<FavoriteLocation>> callBack) {
        locationRemote.getFavoriteLocations(new DataLoadCallBack<List<FavoriteLocation>>() {
            @Override
            public void onDataLoaded(List<FavoriteLocation> data) {
                refreshLocationCache(data);
                saveFavoriteLocations(data);
                callBack.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }

            @Override
            public void onError() {
                callBack.onError();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                callBack.onError(errorCode, errorMessage);
            }
        });
    }

    public void getRefreshFavoriteLocationsFromRemote(DataLoadCallBack<List<FavoriteLocation>> callBack) {

    }

    @Override
    public void saveFavoriteLocations(List<FavoriteLocation> locations) {
        locationLocal.saveFavoriteLocations(locations);
    }

    @Override
    public void getLocationDetail(String locationId, DataLoadCallBack<LocationDetail> callBack) {
        this.cachedLocation.getLocationDetail(locationId, new DataLoadCallBack<LocationDetail>() {
            @Override
            public void onDataLoaded(LocationDetail data) {
                callBack.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                getLocationDetailFromLocalDataSource(locationId, callBack);
            }
        });
    }

    private void getLocationDetailFromLocalDataSource(String locationId, DataLoadCallBack<LocationDetail> callBack) {
        this.locationLocal.getLocationDetail(locationId, new DataLoadCallBack<LocationDetail>() {
            @Override
            public void onDataLoaded(LocationDetail data) {
                refreshingLocationDetailCache(locationId, data);
                callBack.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                getLocationDetailFromRemoteDataSource(locationId, callBack);
            }
        });
    }

    private void refreshingLocationDetailCache(String locationId, LocationDetail data) {
        cachedLocation.saveLocationDetail(locationId, data);
    }

    private void getLocationDetailFromRemoteDataSource(String locationId, DataLoadCallBack<LocationDetail> callBack) {
        locationRemote.getLocationDetail(locationId, new DataLoadCallBack<LocationDetail>() {
            @Override
            public void onDataLoaded(LocationDetail data) {
                saveLocationDetail(locationId, data);
                refreshingLocationDetailCache(locationId, data);
                callBack.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }

            @Override
            public void onError() {
                callBack.onError();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                callBack.onError(errorCode, errorMessage);
            }
        });
    }

    @Override
    public void saveLocationDetail(String locationId, LocationDetail detail) {
        locationLocal.saveLocationDetail(locationId, detail);
    }

    @Override
    public void getRefreshFavoriteLocations(DataLoadCallBack<List<FavoriteLocation>> callBack) {
        getFavoriteLocationsFromRemoteDataSource(callBack);
    }

    @Override
    public void getRefreshLocationDetail(String locationId, DataLoadCallBack<LocationDetail> callBack) {
        getLocationDetailFromRemoteDataSource(locationId, callBack);
    }
}
