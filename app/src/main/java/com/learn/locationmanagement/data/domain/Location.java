package com.learn.locationmanagement.data.domain;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.learn.locationmanagement.model.location.detail.LocationDetail;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

@Entity
public class Location {
    @PrimaryKey(autoGenerate = false)
    private String id;
    private String code;
    private String name;
    private String image;

    private double lat;
    private double lng;
    private String description;

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
    public static Location fromFavoriteLocation(@NonNull FavoriteLocation favoriteLocation) {
        return new Location(
                favoriteLocation.getId(),
                favoriteLocation.getCode(),
                favoriteLocation.getName(),
                favoriteLocation.getImage()
        );
    }

    @Ignore
    public void setDetail(@NonNull LocationDetail detail) {
        this.lat = detail.getLat();
        this.lng = detail.getLng();
        this.description = detail.getDescription();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
