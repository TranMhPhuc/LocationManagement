package com.learn.locationmanagement.model.location.favorites;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationData {

    @SerializedName("locations")
    private List<Location> locations;

    public LocationData(List<Location> locations) {
        this.locations = locations;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}