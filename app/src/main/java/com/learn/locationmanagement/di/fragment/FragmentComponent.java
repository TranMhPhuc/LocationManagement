package com.learn.locationmanagement.di.fragment;

import androidx.fragment.app.Fragment;

import com.learn.locationmanagement.di.presentation.PresentationComponent;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {
    PresentationComponent newPresentationComponent();

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance Builder fragment(Fragment fragment);
        FragmentComponent build();
    }
}
