package com.learn.locationmanagement;

import android.app.Application;

import com.learn.locationmanagement.di.app.AppComponent;
import com.learn.locationmanagement.di.app.AppModule;
import com.learn.locationmanagement.di.app.DaggerAppComponent;

public class LocationManagementApp extends Application {
    private AppComponent appComponent = null;

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this)).build();
        }
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
