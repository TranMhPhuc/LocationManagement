package com.learn.locationmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.locationmanagement.data.repository.location.LocationRepository;
import com.learn.locationmanagement.model.location.common.Message;
import com.learn.locationmanagement.model.location.detail.LocationDetail;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

import javax.inject.Inject;

public class LocationDetailViewModel extends ViewModel {
    private final LocationRepository.DataLoadCallBack callBack = new LocationDetailCallBack();
    private final LocationRepository locationRepository;
    private final MutableLiveData<LocationDetail> locationDetailLD = new MutableLiveData<>();
    public LocationDetail locationDetail = null;
    private MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>();
    private MutableLiveData<Boolean> navigateBackToFavoriteScreen = new MutableLiveData<>();
    private MutableLiveData<FavoriteLocation> navigateToMapScreen = new MutableLiveData<>();
    private MutableLiveData<Boolean> onRefreshStart = new MutableLiveData<>();

    private MutableLiveData<Message> errorMessage = new MutableLiveData<>();
    @Inject
    public LocationDetailViewModel(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void onButtonGoClick(FavoriteLocation favoriteLocation) {
        navigateToMapScreen.postValue(favoriteLocation);
    }

    public LiveData<FavoriteLocation> getNavigateToMapScreenLiveData() {
        return navigateToMapScreen;
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
            showProgressBar.postValue(false);
            errorMessage.postValue(new Message("C?? l???i x???y ra!", "C?? l???i x???y ra trong qu?? tr??nh l???y th??ng tin v??? tr??. Vui l??ng th??? lai"));
        }

        @Override
        public void onError(int errorCode, String errorMessage) {
            showProgressBar.postValue(false);
            LocationDetailViewModel.this.errorMessage.postValue(new Message("C?? l???i x???y ra!", "C?? l???i x???y ra trong qu?? tr??nh l???y th??ng tin v??? tr??. Vui l??ng th??? lai"));
        }
    }

    private void setLocationDetailData(LocationDetail data) {
        showProgressBar.postValue(false);
        locationDetailLD.postValue(data);
    }

    public void getLocationDetail(FavoriteLocation favoriteLocation) {
        showProgressBar.postValue(Boolean.TRUE);
        locationRepository.getLocationDetail(favoriteLocation.getId(), callBack);
    }

    public LiveData<LocationDetail> getLocationDetailLiveData() {
        return locationDetailLD;
    }

    public LiveData<Boolean> getShowProgressBarLiveData() {
        return showProgressBar;
    }

    public LiveData<Boolean> getNavigateBackToFavoriteScreenLiveData() {
        return navigateBackToFavoriteScreen;
    }

    public void onRefresh(String locationId){
        locationRepository.getRefreshLocationDetail(locationId, callBack);
        onRefreshStart.postValue(true);
    }

    public LiveData<Boolean> getOnRefreshStart() {
        return onRefreshStart;
    }

    public void onButtonBackClick() {
        navigateBackToFavoriteScreen.postValue(Boolean.TRUE);
    }
    public LiveData<Message> getErrorMessageLiveData() {
        return errorMessage;
    }
    public void resetErrorMessage() {
        this.errorMessage.postValue(null);
    }
    public void resetNavigateToMapScreen() {
        this.navigateToMapScreen.postValue(null);
    }
    public void resetNavigateBack(){
        this.navigateBackToFavoriteScreen.postValue(null);
    }
}
