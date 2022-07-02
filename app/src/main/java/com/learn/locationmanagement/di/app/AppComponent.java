package com.learn.locationmanagement.di.app;

import com.learn.locationmanagement.di.fragment.FragmentComponent;
import com.learn.locationmanagement.di.fragment.FragmentModule;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class})
public interface AppComponent {
    FragmentComponent.Builder newFragmentComponentBuilder();
}
