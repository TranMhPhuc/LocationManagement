package com.learn.locationmanagement.data.repository.location;

import com.learn.locationmanagement.data.domain.Location;
import com.learn.locationmanagement.model.location.detail.LocationDetail;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

import java.util.List;

public interface LocationRepository {
    interface DataLoadCallBack<T> {
        void onDataLoaded(T data);
        void onDataNotAvailable();
        default void onError() {};
        default void onError(int errorCode, String errorMessage){};
    }

    void getFavoriteLocations(DataLoadCallBack<List<FavoriteLocation>> callBack);
    void saveFavoriteLocations(List<FavoriteLocation> locations);

    void getLocationDetail(String locationId, DataLoadCallBack<LocationDetail> callBack);
    void saveLocationDetail(String locationId, LocationDetail detail);
}
