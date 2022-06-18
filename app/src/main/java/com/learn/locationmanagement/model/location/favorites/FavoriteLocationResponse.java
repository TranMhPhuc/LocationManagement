package com.learn.locationmanagement.model.location.favorites;

import com.google.gson.annotations.SerializedName;

public class FavoriteLocationResponse {
    @SerializedName("error_code")
    private int errorCode;

    @SerializedName("error_message")
    private String errorMessage;

    @SerializedName("data")
    private LocationData data;

    public FavoriteLocationResponse(int errorCode, String errorMessage, LocationData data) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocationData getData() {
        return data;
    }
}
