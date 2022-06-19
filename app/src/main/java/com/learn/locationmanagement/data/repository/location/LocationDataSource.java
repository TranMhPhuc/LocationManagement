package com.learn.locationmanagement.data.repository.location;

import com.learn.locationmanagement.data.domain.Location;
import com.learn.locationmanagement.model.location.detail.LocationDetail;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

import java.util.List;

public interface LocationDataSource {
    interface Remote {
         void getFavoriteLocations(LocationRepository.DataLoadCallBack<List<FavoriteLocation>> callBack);
         void getLocationDetail(String locationId, LocationRepository.DataLoadCallBack<LocationDetail> callBack);
    }

    interface Local extends Remote {
        void saveFavoriteLocations(List<FavoriteLocation> locations);
        void saveLocationDetail(String locationId, LocationDetail detail);
    }
}
