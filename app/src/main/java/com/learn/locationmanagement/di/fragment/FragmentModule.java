package com.learn.locationmanagement.di.fragment;

import androidx.fragment.app.Fragment;

import com.learn.locationmanagement.data.database.LocationDAO;
import com.learn.locationmanagement.data.database.LocationDatabase;
import com.learn.locationmanagement.data.repository.location.LocationRepository;
import com.learn.locationmanagement.data.repository.location.LocationRepositoryImpl;
import com.learn.locationmanagement.ui.MainActivity;
import com.learn.locationmanagement.ui.common.dialog.DialogNavigator;
import com.learn.locationmanagement.ui.common.fragment.GoogleMap;
import com.learn.locationmanagement.ui.common.image.ImageLoader;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class FragmentModule {

    @FragmentScope
    @Provides
    public static LocationDAO locationDAO(LocationDatabase locationDatabase) {
        return locationDatabase.getLocationDAO();
    }

    @FragmentScope
    @Provides
    public static GoogleMap googleMap(Fragment fragment) {
        return new GoogleMap(fragment);
    }

    @Provides
    @FragmentScope
    public static ImageLoader imageLoader(Fragment fragment) {
        return new ImageLoader(fragment);
    }

    @Provides
    public static MainActivity mainActivity(Fragment fragment) {
        return (MainActivity) fragment.requireActivity();
    }

    @FragmentScope
    @Provides
    public static DialogNavigator dialogNavigator(Fragment fragment) {
        return new DialogNavigator(fragment.getContext());
    }

    @FragmentScope
    @Provides
    public static Executor executor() {
        return Executors.newSingleThreadExecutor();
    }

    @FragmentScope
    @Binds
    public abstract LocationRepository locationRepository(LocationRepositoryImpl locationRepository);
}
