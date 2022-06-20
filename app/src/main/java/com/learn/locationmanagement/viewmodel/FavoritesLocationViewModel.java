package com.learn.locationmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.locationmanagement.data.repository.location.LocationRepository;
import com.learn.locationmanagement.data.repository.location.LocationRepositoryImpl;
import com.learn.locationmanagement.model.location.common.Position;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FavoritesLocationViewModel extends ViewModel {
    private final LocationRepository.DataLoadCallBack callBack = new FavoriteLocationCallBack();
    private LocationRepository locationRepository;
    private MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>();
    private List<FavoriteLocation> favoriteLocationList = new ArrayList<>();
    private MutableLiveData<List<FavoriteLocation>> favoriteLocations = new MutableLiveData<>();
    private MutableLiveData<Position> navigateToMapScreen = new MutableLiveData<>();
    private MutableLiveData<FavoriteLocation> navigateToDetailScreen = new MutableLiveData<>();

    @Inject
    public FavoritesLocationViewModel(LocationRepositoryImpl locationRepository) {
        this.locationRepository = locationRepository;
    }

    private void setFavoriteLocationData(List<FavoriteLocation> data) {
        showProgressBar.postValue(false);
        favoriteLocationList.clear();
        favoriteLocationList.addAll(data);
        favoriteLocations.postValue(favoriteLocationList);
    }

    public void getFavoriteLocations() {
        showProgressBar.postValue(Boolean.TRUE);
        locationRepository.getFavoriteLocations(callBack);
    }

    public LiveData<List<FavoriteLocation>> getFavoriteLocationsLiveData() {
        return favoriteLocations;
    }

    public LiveData<Boolean> getShowProgressBarLiveData() {
        return showProgressBar;
    }

    public LiveData<Position> getNavigateToMapScreenLiveData() {
        return navigateToMapScreen;
    }

    public LiveData<FavoriteLocation> getNavigateToDetailScreenLiveData() {
        return navigateToDetailScreen;
    }

    public void onLocationItemClick(FavoriteLocation favoriteLocation) {
        navigateToDetailScreen.postValue(favoriteLocation);
    }

    public void onBtnDirectionButtonClick(FavoriteLocation favoriteLocation) {
        navigateToMapScreen.postValue(new Position(favoriteLocation, false));
    }

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
}
