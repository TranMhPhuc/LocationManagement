package com.learn.locationmanagement.model.location.detail;

import com.google.gson.annotations.SerializedName;

public class LocationDetailResponse{
	@SerializedName("error_code")
	private int errorCode;

	@SerializedName("error_message")
	private String errorMessage;

	@SerializedName("data")
	private LocationDetailData data;

	public LocationDetailResponse(int errorCode, String errorMessage, LocationDetailData data) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.data = data;
	}

	public String getErrorMessage(){
		return errorMessage;
	}

	public LocationDetailData getData(){
		return data;
	}

	public int getErrorCode(){
		return errorCode;
	}
}