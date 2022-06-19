package com.learn.locationmanagement.di.presentation;

import com.learn.locationmanagement.ui.fragment.FavoritesFragment;

import dagger.Subcomponent;

@Subcomponent
@PresentationScope
public interface PresentationComponent {
    void inject(FavoritesFragment fragment);
}
