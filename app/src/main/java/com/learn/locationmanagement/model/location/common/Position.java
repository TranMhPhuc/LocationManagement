package com.learn.locationmanagement.model.location.common;

import com.learn.locationmanagement.data.domain.Location;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

public class Position {
    private FavoriteLocation favoriteLocation;
    private boolean isDestination;

    public Position(FavoriteLocation favoriteLocation, boolean isDestination) {
        this.favoriteLocation = favoriteLocation;
        this.isDestination = isDestination;
    }

    public FavoriteLocation getFavoriteLocation() {
        return favoriteLocation;
    }

    public void setFavoriteLocation(FavoriteLocation favoriteLocation) {
        this.favoriteLocation = favoriteLocation;
    }

    public boolean isDestination() {
        return isDestination;
    }

    public void setDestination(boolean destination) {
        isDestination = destination;
    }
}
