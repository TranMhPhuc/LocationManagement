package com.learn.locationmanagement.di.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.learn.locationmanagement.BuildConfig;
import com.learn.locationmanagement.data.database.LocationDAO;
import com.learn.locationmanagement.data.database.LocationDatabase;
import com.learn.locationmanagement.data.networking.LocationApi;
import com.learn.locationmanagement.data.networking.UrlProvider;
import com.learn.locationmanagement.data.repository.location.LocationCacheDataSource;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @AppScope
    @Provides
    public UrlProvider urlProvider() {
        return new UrlProvider();
    }

    @AppScope
    @Provides
    public OkHttpClient httpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    @AppScope
    @Provides
    public Retrofit retrofit(@NonNull UrlProvider urlProvider, @NonNull OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(urlProvider.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    @AppScope
    @Provides
    public LocationApi locationApi(@NonNull Retrofit retrofit) {
        return retrofit.create(LocationApi.class);
    }

    @AppScope
    @Provides
    public Context applicationContext() {
        return application.getApplicationContext();
    }

    @AppScope
    @Provides
    public LocationDatabase locationDatabase(Context applicationContext) {
        return LocationDatabase.getInstance(applicationContext);
    }

    @AppScope
    @Provides
    public LocationCacheDataSource locationCacheDataSource() {
        return new LocationCacheDataSource();
    }

}
