package com.learn.locationmanagement.networking;

import com.learn.locationmanagement.model.location.detail.LocationDetailResponse;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocationResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LocationApi {
    @GET("2fe37bd6-9dd0-4384-9a65-14ae709b82d9")
    Response<FavoriteLocationResponse> getFavoriteLocations();

    @GET("{location_id}")
    Response<LocationDetailResponse> getLocationDetail(@Path("location_id") String locationId);
}
