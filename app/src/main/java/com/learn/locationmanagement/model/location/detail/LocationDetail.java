package com.learn.locationmanagement.model.location.detail;

import com.google.gson.annotations.SerializedName;

public class LocationDetail {

    @SerializedName("code")
    private String code;

    @SerializedName("lng")
    private double lng;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("lat")
    private double lat;

	public LocationDetail(String code, double lng, String name, String description, double lat) {
		this.code = code;
		this.lng = lng;
		this.name = name;
		this.description = description;
		this.lat = lat;
	}

	public String getCode() {
        return code;
    }

    public double getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getLat() {
        return lat;
    }
}