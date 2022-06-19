package com.learn.locationmanagement.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Provider<FavoritesLocationViewModel> favoritesLocationViewModel;

    @Inject
    public ViewModelFactory(Provider<FavoritesLocationViewModel> favoritesLocationViewModel) {
        this.favoritesLocationViewModel = favoritesLocationViewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        if (FavoritesLocationViewModel.class.equals(aClass)) {
            return (T) favoritesLocationViewModel.get();
        }
        throw new UnsupportedClassVersionError("unsupported type: " + aClass);
    }
}
