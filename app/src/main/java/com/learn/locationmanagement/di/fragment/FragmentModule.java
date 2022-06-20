package com.learn.locationmanagement.di.fragment;

import androidx.fragment.app.Fragment;

import com.learn.locationmanagement.data.database.LocationDAO;
import com.learn.locationmanagement.data.database.LocationDatabase;
import com.learn.locationmanagement.data.repository.location.LocationCacheDataSource;
import com.learn.locationmanagement.data.repository.location.LocationLocalDataSource;
import com.learn.locationmanagement.data.repository.location.LocationRemoteDataSource;
import com.learn.locationmanagement.data.repository.location.LocationRepository;
import com.learn.locationmanagement.data.repository.location.LocationRepositoryImpl;
import com.learn.locationmanagement.ui.MainActivity;
import com.learn.locationmanagement.ui.common.dialog.DialogNavigator;
import com.learn.locationmanagement.ui.common.image.ImageLoader;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private final Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @FragmentScope
    @Provides
    public LocationDAO locationDAO(LocationDatabase locationDatabase) {
        return locationDatabase.getLocationDAO();
    }

    @Provides
    @FragmentScope
    public Fragment fragment() {
        return fragment;
    }

    @Provides
    @FragmentScope
    public ImageLoader imageLoader() {
        return new ImageLoader(fragment);
    }

    @Provides
    public MainActivity mainActivity() {
        return (MainActivity) fragment.requireActivity();
    }

    @FragmentScope
    @Provides
    public DialogNavigator dialogNavigator() {
        return new DialogNavigator(fragment().getContext());
    }

    @FragmentScope
    @Provides
    public Executor executor() {
        return Executors.newSingleThreadExecutor();
    }

    @FragmentScope
    @Provides
    public LocationRepository locationRepository(LocationRemoteDataSource locationRemote, LocationLocalDataSource locationLocal, LocationCacheDataSource cachedLocation) {
        return new LocationRepositoryImpl(locationRemote, locationLocal, cachedLocation);
    }
}
