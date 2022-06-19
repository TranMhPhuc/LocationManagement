package com.learn.locationmanagement.di.fragment;

import androidx.fragment.app.Fragment;

import dagger.Module;

@Module
public class FragmentModule {
    private final Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }
}
