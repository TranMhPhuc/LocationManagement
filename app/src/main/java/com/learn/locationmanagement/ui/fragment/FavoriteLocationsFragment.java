package com.learn.locationmanagement.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.learn.locationmanagement.databinding.FragmentFavoritesBinding;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;
import com.learn.locationmanagement.ui.MainActivity;
import com.learn.locationmanagement.ui.adapter.LocationAdapter;
import com.learn.locationmanagement.ui.common.fragment.BaseFragment;
import com.learn.locationmanagement.viewmodel.FavoritesLocationViewModel;
import com.learn.locationmanagement.viewmodel.ViewModelFactory;

import javax.inject.Inject;

public class FavoriteLocationsFragment extends BaseFragment<FragmentFavoritesBinding> {
    @Inject
    public ViewModelFactory viewModelFactory;
    @Inject
    public MainActivity mainActivity;
    private FavoritesLocationViewModel favoritesLocationViewModel;
    private LocationAdapter locationAdapter;

    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public FragmentFavoritesBinding bindView() {
        return FragmentFavoritesBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getInjector().inject(this);
        setControl();
        setEvent();
    }

    private void setControl() {
        navController = Navigation.findNavController(binding.getRoot());
        LocationAdapter.OnClickListener listener = new LocationAdapter.OnClickListener() {
            @Override
            public void onItemClick(FavoriteLocation favoriteLocation) {
                favoritesLocationViewModel.onLocationItemClick(favoriteLocation);
            }

            @Override
            public void onDirectionButtonClick(FavoriteLocation favoriteLocation) {
                favoritesLocationViewModel.onBtnDirectionButtonClick(favoriteLocation);
            }
        };
        locationAdapter = new LocationAdapter(listener);
        binding.locationRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.locationRecyclerView.setAdapter(locationAdapter);

        favoritesLocationViewModel = new ViewModelProvider(this, viewModelFactory).get(FavoritesLocationViewModel.class);

    }

    private void setEvent() {
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        binding.swipeRefresh.setOnRefreshListener(() -> favoritesLocationViewModel.onRefresh());

        favoritesLocationViewModel.getShowProgressBarLiveData().observe(viewLifecycleOwner, visible -> {
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
        favoritesLocationViewModel.getNavigateToMapScreenLiveData().observe(viewLifecycleOwner, position -> {
            // TODO show map
        });
        favoritesLocationViewModel.getNavigateToDetailScreenLiveData().observe(viewLifecycleOwner, this::onNavigateChange);
        favoritesLocationViewModel.getOnRefreshStartLiveData().observe(viewLifecycleOwner, this::onRefresh);
        favoritesLocationViewModel.getFavoriteLocations();
    }

    private void onRefresh(Boolean isRefresh) {
        if (isRefresh == null) return;
        binding.swipeRefresh.setRefreshing(false);
    }


    @Override
    public void onDestroyView() {
        favoritesLocationViewModel.getNavigateToDetailScreenLiveData().removeObserver(this::onNavigateChange);
        favoritesLocationViewModel.resetFavoriteLocation();
        favoritesLocationViewModel.getOnRefreshStartLiveData().removeObserver(this::onRefresh);
        favoritesLocationViewModel.resetOnRefresh();
        super.onDestroyView();
    }

    private void onNavigateChange(FavoriteLocation favoriteLocation) {
        if (favoriteLocation == null) return;
        FavoriteLocationsFragmentDirections.ActionFavoritesFragmentToDetailFragment actionFavoritesFragmentToDetailFragment
                = FavoriteLocationsFragmentDirections.actionFavoritesFragmentToDetailFragment(favoriteLocation);
        navController.navigate(actionFavoritesFragmentToDetailFragment);
    }
}
