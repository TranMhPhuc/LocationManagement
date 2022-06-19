package com.learn.locationmanagement.di.fragment;

import com.learn.locationmanagement.di.presentation.PresentationComponent;

import dagger.Component;
import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {
    PresentationComponent newPresentationComponent();
}
