package com.learn.locationmanagement.data.domain;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.learn.locationmanagement.model.location.detail.LocationDetail;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

@Entity
public class Location {
    public static final double NO_LOCATION = 200;

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;
    private String code;
    private String name;
    private String image;

    private double lat = NO_LOCATION;
    private double lng = NO_LOCATION;
    private String description = null;

    public Location(String id, String code, String name, String image, double lat, double lng, String description) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.image = image;
        this.lat = lat;
        this.lng = lng;
        this.description = description;
    }

    @Ignore
    public Location(String id, String code, String name, String image) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.image = image;
    }

    @Ignore
    public void setDetail(@NonNull LocationDetail detail) {
        this.lat = detail.getLat();
        this.lng = detail.getLng();
        this.description = detail.getDescription();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
