package com.learn.locationmanagement.data.repository.location;

import com.learn.locationmanagement.data.networking.LocationApi;
import com.learn.locationmanagement.model.location.detail.LocationDetail;
import com.learn.locationmanagement.model.location.detail.LocationDetailResponse;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocationResponse;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationRemoteDataSource implements LocationDataSource.Remote {
    private final LocationApi locationApi;

    @Inject
    public LocationRemoteDataSource(LocationApi locationApi) {
        this.locationApi = locationApi;
    }

    @Override
    public void getFavoriteLocations(LocationRepository.DataLoadCallBack<List<FavoriteLocation>> callBack) {
        locationApi.getFavoriteLocations().enqueue(new Callback<FavoriteLocationResponse>() {
            @Override
            public void onResponse(Call<FavoriteLocationResponse> call, Response<FavoriteLocationResponse> response) {
                if (response.isSuccessful()) {
                    FavoriteLocationResponse body = response.body();
                    if (body != null) {
                        int errorCode = body.getErrorCode();
                        if (errorCode != 0) {
                            callBack.onError(errorCode, body.getErrorMessage());

                        } else {
                            callBack.onDataLoaded(body.getData().getLocations());
                        }
                    } else {
                        callBack.onError();
                    }
                } else {
                    callBack.onError(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<FavoriteLocationResponse> call, Throwable t) {
                callBack.onError();
            }
        });
    }

    @Override
    public void getLocationDetail(String locationId, LocationRepository.DataLoadCallBack<LocationDetail> callBack) {
        locationApi.getLocationDetail(locationId).enqueue(new Callback<LocationDetailResponse>() {
            @Override
            public void onResponse(Call<LocationDetailResponse> call, Response<LocationDetailResponse> response) {
                if (response.isSuccessful()) {
                    LocationDetailResponse body = response.body();
                    if (body != null) {
                        int errorCode = body.getErrorCode();
                        if (errorCode == 0) {
                            callBack.onDataLoaded(body.getData().getLocation());
                        } else {
                            callBack.onError(errorCode, body.getErrorMessage());
                        }
                    } else {
                        callBack.onError();
                    }
                } else {
                    callBack.onError(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<LocationDetailResponse> call, Throwable t) {
                callBack.onError();
            }
        });
    }
}
