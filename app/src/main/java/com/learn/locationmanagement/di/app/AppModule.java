package com.learn.locationmanagement.di.app;

import android.app.Application;

import androidx.annotation.NonNull;

import com.learn.locationmanagement.BuildConfig;
import com.learn.locationmanagement.data.networking.UrlProvider;

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

}
