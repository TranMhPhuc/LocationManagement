package com.learn.locationmanagement.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.learn.locationmanagement.databinding.FragmentFavoritesBinding;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;
import com.learn.locationmanagement.ui.MainActivity;
import com.learn.locationmanagement.ui.adapter.LocationAdapter;
import com.learn.locationmanagement.ui.common.fragment.BaseFragment;
import com.learn.locationmanagement.viewmodel.FavoritesLocationViewModel;
import com.learn.locationmanagement.viewmodel.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

public class FavoritesFragment extends BaseFragment<FragmentFavoritesBinding> {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public FragmentFavoritesBinding bindView() {
        return FragmentFavoritesBinding.inflate(getLayoutInflater());
    }

    @Inject public ViewModelFactory viewModelFactory;
    private FavoritesLocationViewModel favoritesLocationViewModel;
    private LocationAdapter locationAdapter;

    public MainActivity mainActivity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getInjector().inject(this);
        setControl();
        setEvent();
    }

    private void setControl() {
        mainActivity = (MainActivity) requireActivity();

        locationAdapter = new LocationAdapter();
        binding.locationRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.locationRecyclerView.setAdapter(locationAdapter);

        favoritesLocationViewModel = new ViewModelProvider(this, viewModelFactory).get(FavoritesLocationViewModel.class);

    }

    private void setEvent() {
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        favoritesLocationViewModel.getShowProgressBar().observe(viewLifecycleOwner, visible -> {
            if (visible) {
                mainActivity.showProgressBar();
            } else {
                mainActivity.hideProgressBar();
            }
        });
        favoritesLocationViewModel.getFavoriteLocationsLiveData().observe(
                viewLifecycleOwner, locations -> {
                    locationAdapter.submitList(locations);
                }
        );
        favoritesLocationViewModel.getFavoriteLocations();
    }
}
