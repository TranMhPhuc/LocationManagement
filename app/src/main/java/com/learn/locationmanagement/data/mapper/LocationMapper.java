package com.learn.locationmanagement.data.mapper;

import androidx.annotation.NonNull;

import com.learn.locationmanagement.data.domain.Location;
import com.learn.locationmanagement.model.location.detail.LocationDetail;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

public class LocationMapper {
    public static Location toDomain(@NonNull FavoriteLocation favoriteLocation) {
        return new Location(
                favoriteLocation.getId(),
                favoriteLocation.getCode(),
                favoriteLocation.getName(),
                favoriteLocation.getImage()
        );
    }

    public static FavoriteLocation toFavoriteLocation(@NonNull Location domain) {
        return new FavoriteLocation(domain.getImage(), domain.getCode(), domain.getName(), domain.getId());
    }

    public static LocationDetail toLocationDetail(Location domain){
        return new LocationDetail(domain.getCode(), domain.getLng(), domain.getName(), domain.getDescription(), domain.getLat());
    }
}
