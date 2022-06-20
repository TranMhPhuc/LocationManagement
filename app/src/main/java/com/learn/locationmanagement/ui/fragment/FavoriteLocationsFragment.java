package com.learn.locationmanagement.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.learn.locationmanagement.databinding.FragmentFavoritesBinding;
import com.learn.locationmanagement.model.location.common.Message;
import com.learn.locationmanagement.model.location.common.Position;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;
import com.learn.locationmanagement.ui.MainActivity;
import com.learn.locationmanagement.ui.adapter.LocationAdapter;
import com.learn.locationmanagement.ui.common.dialog.DialogNavigator;
import com.learn.locationmanagement.ui.common.fragment.BaseFragment;
import com.learn.locationmanagement.viewmodel.FavoritesLocationViewModel;
import com.learn.locationmanagement.viewmodel.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

public class FavoriteLocationsFragment extends BaseFragment<FragmentFavoritesBinding> {
    @Inject
    public ViewModelFactory viewModelFactory;
    @Inject
    public MainActivity mainActivity;
    @Inject
    public DialogNavigator dialogNavigator;
    private FavoritesLocationViewModel favoritesLocationViewModel;
    private LocationAdapter locationAdapter;
    private NavController navController;

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
        binding.swipeRefresh.setOnRefreshListener(() -> favoritesLocationViewModel.onRefresh());
        binding.locationSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                locationAdapter.getFilter().filter(query);
                return false;
            }
        });

        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        favoritesLocationViewModel.getShowProgressBarLiveData().observe(viewLifecycleOwner, this::onShowProgressBarChange);
        favoritesLocationViewModel.getFavoriteLocationsLiveData().observe(viewLifecycleOwner, this::favoriteLocationsLoaded);
        favoritesLocationViewModel.getErrorMessageLiveData().observe(viewLifecycleOwner, this::onErrorHappen);
        favoritesLocationViewModel.getNavigateToMapScreenLiveData().observe(viewLifecycleOwner, this::onNavigateToMapScreen);
        favoritesLocationViewModel.getNavigateToDetailScreenLiveData().observe(viewLifecycleOwner, this::onNavigateToDetailScreen);
        favoritesLocationViewModel.getOnRefreshStartLiveData().observe(viewLifecycleOwner, this::onRefresh);
        favoritesLocationViewModel.getFavoriteLocations();
    }

    private void onErrorHappen(Message message) {
        if (message == null) return;
        dialogNavigator.showErrorDialog(message);
    }

    private void onRefresh(Boolean isRefresh) {
        if (isRefresh == null) return;
        binding.swipeRefresh.setRefreshing(false);
    }

    private void onNavigateToDetailScreen(FavoriteLocation favoriteLocation) {
        if (favoriteLocation == null) return;
        FavoriteLocationsFragmentDirections.ActionFavoritesFragmentToDetailFragment action
                = FavoriteLocationsFragmentDirections.actionFavoritesFragmentToDetailFragment(favoriteLocation);
        navController.navigate(action);
    }

    private void onNavigateToMapScreen(Position position) {
        if (position == null) return;
        FavoriteLocationsFragmentDirections.ActionFavoritesFragmentToMapFragment action =
                FavoriteLocationsFragmentDirections.actionFavoritesFragmentToMapFragment(position.getFavoriteLocation());
        navController.navigate(action);
    }

    @Override
    public void onDestroyView() {
        favoritesLocationViewModel.getNavigateToDetailScreenLiveData().removeObserver(this::onNavigateToDetailScreen);
        favoritesLocationViewModel.resetNavigateToDetailScreen();
        favoritesLocationViewModel.getOnRefreshStartLiveData().removeObserver(this::onRefresh);
        favoritesLocationViewModel.resetOnRefresh();
        favoritesLocationViewModel.getNavigateToMapScreenLiveData().removeObserver(this::onNavigateToMapScreen);
        favoritesLocationViewModel.resetNavigateToMapScreen();
        favoritesLocationViewModel.getErrorMessageLiveData().removeObserver(this::onErrorHappen);
        favoritesLocationViewModel.resetErrorMessage();
        super.onDestroyView();
    }

    private void favoriteLocationsLoaded(List<FavoriteLocation> locations) {
        locationAdapter.submitList(locations);
    }

    private void onShowProgressBarChange(Boolean visible) {
        if (visible) {
            mainActivity.showProgressBar();
        } else {
            mainActivity.hideProgressBar();
        }
    }
}
