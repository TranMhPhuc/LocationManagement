package com.learn.locationmanagement.ui.common.fragment;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.learn.locationmanagement.data.domain.Location;
import com.learn.locationmanagement.model.location.detail.LocationDetail;

public class GoogleMap {
    private Fragment fragment;
    private SupportMapFragment supportMapFragment;
    private Marker marker;

    public GoogleMap(Fragment fragment) {
        this.fragment = fragment;
    }

    public void openGoogleMap(@IdRes int mapLayout, LocationDetail detail) {
        if (detail == null || detail.getLng() == Location.NO_LOCATION) return;
        if (supportMapFragment == null) {
            supportMapFragment = (SupportMapFragment) fragment.getChildFragmentManager().findFragmentById(mapLayout);
        }
        Uri uri = Uri.parse("google.navigation:q=" + detail.getLat() + "," + detail.getLng());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fragment.requireActivity().startActivity(intent);
    }


    public void showOnMap(@IdRes int mapLayout, double lat, double lng) {
        if (supportMapFragment == null) {
            supportMapFragment = (SupportMapFragment) fragment.getChildFragmentManager().findFragmentById(mapLayout);
        }
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
}
