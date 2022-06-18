package com.learn.locationmanagement.model.location.detail;

import com.google.gson.annotations.SerializedName;

public class LocationDetailData {

	@SerializedName("location")
	private LocationDetail location;

	public LocationDetailData(LocationDetail location) {
		this.location = location;
	}

	public LocationDetail getLocation(){
		return location;
	}
}