package com.learn.locationmanagement.data.repository.location;

import com.learn.locationmanagement.model.location.detail.LocationDetail;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

import java.util.List;

public class LocationRepositoryImpl implements LocationRepository {



    @Override
    public void getFavoriteLocations(DataLoadCallBack<FavoriteLocation> callBack) {

    }

    @Override
    public void saveFavoriteLocations(List<FavoriteLocation> locations) {

    }

    @Override
    public void getLocationDetail(DataLoadCallBack<LocationDetail> callBack) {

    }

    @Override
    public void saveLocationDetail(LocationDetail detail) {

    }
}
