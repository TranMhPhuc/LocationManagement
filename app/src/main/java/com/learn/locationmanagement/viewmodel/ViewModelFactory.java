package com.learn.locationmanagement.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.learn.locationmanagement.model.location.detail.LocationDetail;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Provider<FavoritesLocationViewModel> favoritesLocationViewModel;
    private final Provider<LocationDetailViewModel> locationDetailViewModel;

    @Inject
    public ViewModelFactory(Provider<FavoritesLocationViewModel> favoritesLocationViewModel,
    Provider<LocationDetailViewModel> locationDetailViewModel) {
        this.favoritesLocationViewModel = favoritesLocationViewModel;
        this.locationDetailViewModel = locationDetailViewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        if (FavoritesLocationViewModel.class.equals(aClass)) {
            return (T) favoritesLocationViewModel.get();
        } else if (LocationDetailViewModel.class.equals(aClass)) {
            return (T) locationDetailViewModel.get();
        }
        throw new UnsupportedClassVersionError("unsupported type: " + aClass);
    }
}
