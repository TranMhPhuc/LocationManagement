package com.learn.locationmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.locationmanagement.data.repository.location.LocationRepository;
import com.learn.locationmanagement.data.repository.location.LocationRepositoryImpl;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FavoritesLocationViewModel extends ViewModel {

    private class FavoriteLocationCallBack implements LocationRepository.DataLoadCallBack<List<FavoriteLocation>> {
        @Override
        public void onDataLoaded(List<FavoriteLocation> data) {
            setFavoriteLocationData(data);
        }

        @Override
        public void onDataNotAvailable() {

        }

        @Override
        public void onError() {
            Log.e("===ERROR===", "onError: ");
        }

        @Override
        public void onError(int errorCode, String errorMessage) {
            Log.e("===ERROR===", "onError: " + errorCode);
        }
    }

    private void setFavoriteLocationData(List<FavoriteLocation> data) {
        showProgressBar.postValue(false);
        favoriteLocationList.clear();
        favoriteLocationList.addAll(data);
        favoriteLocations.postValue(favoriteLocationList);
    }

    private LocationRepository locationRepository;
    @Inject
    public FavoritesLocationViewModel(LocationRepositoryImpl locationRepository) {
        this.locationRepository = locationRepository;
    }
    private final LocationRepository.DataLoadCallBack callBack = new FavoriteLocationCallBack();
    private MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>();

    private List<FavoriteLocation> favoriteLocationList = new ArrayList<>();
    private MutableLiveData<List<FavoriteLocation>> favoriteLocations = new MutableLiveData<>();

    public void getFavoriteLocations() {
        showProgressBar.postValue(Boolean.TRUE);
        locationRepository.getFavoriteLocations(callBack);
    }


    public LiveData<List<FavoriteLocation>> getFavoriteLocationsLiveData() {
        return favoriteLocations;
    }

    public LiveData<Boolean> getShowProgressBar() {
        return showProgressBar;
    }
}
