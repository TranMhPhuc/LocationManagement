package com.learn.locationmanagement.di.presentation;

import com.learn.locationmanagement.ui.fragment.FavoriteLocationsFragment;
import com.learn.locationmanagement.ui.fragment.LocationDetailFragment;
import com.learn.locationmanagement.ui.fragment.MapFragment;

import dagger.Subcomponent;

@Subcomponent
@PresentationScope
public interface PresentationComponent {
    void inject(FavoriteLocationsFragment fragment);
    void inject(LocationDetailFragment locationDetailFragment);
    void inject(MapFragment mapFragment);
}
