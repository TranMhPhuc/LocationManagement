package com.learn.locationmanagement.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.learn.locationmanagement.R;
import com.learn.locationmanagement.databinding.FragmentMapBinding;
import com.learn.locationmanagement.model.location.common.Message;
import com.learn.locationmanagement.model.location.detail.LocationDetail;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;
import com.learn.locationmanagement.ui.adapter.LocationDropdownAdapter;
import com.learn.locationmanagement.ui.common.dialog.DialogNavigator;
import com.learn.locationmanagement.ui.common.fragment.BaseFragment;
import com.learn.locationmanagement.viewmodel.FavoritesLocationViewModel;
import com.learn.locationmanagement.viewmodel.LocationDetailViewModel;
import com.learn.locationmanagement.viewmodel.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

public class MapFragment extends BaseFragment<FragmentMapBinding> {
    @Inject
    public ViewModelFactory viewModelFactory;
    @Inject
    public DialogNavigator dialogNavigator;
    private FavoriteLocation favoriteLocation;
    private SupportMapFragment supportMapFragment;
    private FavoritesLocationViewModel favoritesLocationViewModel;
    private LocationDetailViewModel locationDetailViewModel;
    private LocationDropdownAdapter locationDropdownAdapter;
    private Marker marker;

    @Override
    public FragmentMapBinding bindView() {
        return FragmentMapBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getInjector().inject(this);
        setControl();
        setEvent();
    }

    private void setControl() {
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        favoritesLocationViewModel = new ViewModelProvider(this, viewModelFactory).get(FavoritesLocationViewModel.class);
        locationDetailViewModel = new ViewModelProvider(this, viewModelFactory).get(LocationDetailViewModel.class);
        if (getArguments() != null && !getArguments().isEmpty()) {
            favoriteLocation = MapFragmentArgs.fromBundle(getArguments()).getFavoriteLocation();
        }
    }

    private void setEvent() {
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        favoritesLocationViewModel.getFavoriteLocationsLiveData().observe(viewLifecycleOwner, this::onFavoriteLocationsLoaded);
        locationDetailViewModel.getLocationDetailLiveData().observe(viewLifecycleOwner, this::onLocationDetailLoaded);
        favoritesLocationViewModel.getFavoriteLocations();
        favoritesLocationViewModel.getErrorMessageLiveData().observe(viewLifecycleOwner, this::onErrorHappen);
        locationDetailViewModel.getErrorMessageLiveData().observe(viewLifecycleOwner, this::onErrorHappen);
        binding.fabOpenGoogleMap.setOnClickListener(this::openGoogleMap);
    }

    private void openGoogleMap(View view) {
        LocationDetail detail = locationDetailViewModel.locationDetail;
        Uri uri = Uri.parse("google.navigation:q=" + detail.getLat() + "," + detail.getLng());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        requireActivity().startActivity(intent);
    }

    private void onErrorHappen(Message message) {
        if (message == null) return;
        dialogNavigator.showErrorDialog(message);
    }

    private void showOnMap(double lat, double lng) {
        supportMapFragment.getMapAsync(googleMap -> {
            if (marker != null) {
                marker.remove();
            }
            LatLng latLng = new LatLng(lat, lng);
            MarkerOptions favorite = new MarkerOptions().position(latLng).title("Favorite");
            marker = googleMap.addMarker(favorite);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
        });
    }

    @Override
    public void onDestroyView() {
        favoritesLocationViewModel.getErrorMessageLiveData().removeObserver(this::onErrorHappen);
        favoritesLocationViewModel.resetErrorMessage();
        locationDetailViewModel.getErrorMessageLiveData().removeObserver(this::onErrorHappen);
        locationDetailViewModel.resetErrorMessage();
        super.onDestroyView();
    }

    private void onFavoriteLocationsLoaded(List<FavoriteLocation> favoriteLocations) {
        if (favoriteLocations.isEmpty()) return;
        locationDropdownAdapter = new LocationDropdownAdapter(MapFragment.this.getContext(), favoriteLocations);
        binding.dropdownLocation.setAdapter(locationDropdownAdapter);
        FavoriteLocation location = favoriteLocation;
        if (location == null) {
            location = favoriteLocations.get(0);
        }
        binding.dropdownLocation.setText(location.getName(), false);
        locationDetailViewModel.getLocationDetail(location);
        binding.dropdownLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FavoriteLocation location = favoriteLocations.get(i);
                locationDetailViewModel.getLocationDetail(location);
            }
        });
    }

    private void onLocationDetailLoaded(LocationDetail locationDetail) {
        locationDetailViewModel.locationDetail = locationDetail;
        showOnMap(locationDetail.getLat(), locationDetail.getLng());
    }
}
