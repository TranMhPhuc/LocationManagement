package com.learn.locationmanagement.di.fragment;

import androidx.fragment.app.Fragment;

import com.learn.locationmanagement.data.database.LocationDAO;
import com.learn.locationmanagement.data.database.LocationDatabase;
import com.learn.locationmanagement.ui.adapter.LocationAdapter;

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

    @FragmentScope
    @Provides
    public Executor executor() {
        return Executors.newSingleThreadExecutor();
    }
}
