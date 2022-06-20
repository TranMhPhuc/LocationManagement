package com.learn.locationmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.locationmanagement.data.repository.location.LocationRepository;
import com.learn.locationmanagement.data.repository.location.LocationRepositoryImpl;
import com.learn.locationmanagement.model.location.detail.LocationDetail;

import javax.inject.Inject;

public class LocationDetailViewModel extends ViewModel {
    private final LocationRepository.DataLoadCallBack callBack = new LocationDetailCallBack();
    private final LocationRepository locationRepository;
    private final MutableLiveData<LocationDetail> locationDetail = new MutableLiveData<>();
    private MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>();
    private MutableLiveData<Boolean> navigateBackToFavoriteScreen = new MutableLiveData<>();

    @Inject
    public LocationDetailViewModel(LocationRepositoryImpl locationRepository) {
        this.locationRepository = locationRepository;
    }

    private class LocationDetailCallBack implements LocationRepository.DataLoadCallBack<LocationDetail> {
        @Override
        public void onDataLoaded(LocationDetail data) {
            setLocationDetailData(data);
        }

        @Override
        public void onDataNotAvailable() {

        }

        @Override
        public void onError() {
            Log.e("===ERROR===", "onError detail: ");
        }

        @Override
        public void onError(int errorCode, String errorMessage) {
            Log.e("===ERROR===", "onError detail: " + errorMessage);
        }
    }

    private void setLocationDetailData(LocationDetail data) {
        showProgressBar.postValue(false);
        locationDetail.postValue(data);
    }

    public void getLocationDetail(String locationId) {
        showProgressBar.postValue(Boolean.TRUE);
        locationRepository.getLocationDetail(locationId, callBack);
    }

    public LiveData<LocationDetail> getLocationDetailLiveData() {
        return locationDetail;
    }

    public LiveData<Boolean> getShowProgressBarLiveData() {
        return showProgressBar;
    }

    public LiveData<Boolean> getNavigateBackToFavoriteScreenLiveData() {
        return navigateBackToFavoriteScreen;
    }

    public void onButtonBackClick() {
        navigateBackToFavoriteScreen.postValue(Boolean.TRUE);
    }
}
