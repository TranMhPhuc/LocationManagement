package com.learn.locationmanagement.model.location.favorites;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteLocationData {

    @SerializedName("locations")
    private List<FavoriteLocation> favoriteLocations;

    public FavoriteLocationData(List<FavoriteLocation> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
    }

    public List<FavoriteLocation> getLocations() {
        return favoriteLocations;
    }

    public void setLocations(List<FavoriteLocation> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
    }
}