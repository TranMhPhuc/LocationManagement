package com.learn.locationmanagement.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.locationmanagement.data.repository.location.LocationRepository;
import com.learn.locationmanagement.data.repository.location.LocationRepositoryImpl;
import com.learn.locationmanagement.model.location.common.Message;
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
    private MutableLiveData<Boolean> onRefreshStart = new MutableLiveData<>();
    private MutableLiveData<Message> errorMessage = new MutableLiveData<>();

    @Inject
    public FavoritesLocationViewModel(LocationRepository locationRepository) {
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

    public void resetNavigateToDetailScreen() {
        navigateToDetailScreen.postValue(null);
    }
    public void resetOnRefresh() {onRefreshStart.postValue(null);}

    public void onRefresh() {
        locationRepository.getRefreshFavoriteLocations(callBack);
        onRefreshStart.postValue(Boolean.TRUE);
    }

    public LiveData<Boolean> getOnRefreshStartLiveData() {
        return onRefreshStart;
    }

    public void resetNavigateToMapScreen() {
        navigateToMapScreen.postValue(null);
    }

    private class FavoriteLocationCallBack implements LocationRepository.DataLoadCallBack<List<FavoriteLocation>> {

        @Override
        public void onDataLoaded(List<FavoriteLocation> data) {
            setFavoriteLocationData(data);
        }

        @Override
        public void onDataNotAvailable() {
            throw new UnsupportedOperationException("Not supported on ViewModel");
        }

        @Override
        public void onError() {
            errorMessage.postValue(new Message("Có lỗi xảy ra!", "Có lỗi xảy ra trong quá trình lấy thông tin vị trí. Vui lòng thử lai"));
        }

        @Override
        public void onError(int errorCode, String errorMessage) {
            FavoritesLocationViewModel.this.errorMessage.postValue(new Message("Có lỗi xảy ra!", "Có lỗi xảy ra trong quá trình lấy thông tin vị trí. Vui lòng thử lai"));
        }
    }

    public LiveData<Message> getErrorMessageLiveData() {
        return errorMessage;
    }

    public void resetErrorMessage() {
        this.errorMessage.postValue(null);
    }
}
