package com.learn.locationmanagement.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.learn.locationmanagement.R;
import com.learn.locationmanagement.databinding.FragmentDetailBinding;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;
import com.learn.locationmanagement.ui.MainActivity;
import com.learn.locationmanagement.ui.common.fragment.BaseFragment;
import com.learn.locationmanagement.ui.common.image.ImageLoader;
import com.learn.locationmanagement.viewmodel.LocationDetailViewModel;
import com.learn.locationmanagement.viewmodel.ViewModelFactory;

import javax.inject.Inject;

public class LocationDetailFragment extends BaseFragment<FragmentDetailBinding> {

    @Inject
    public ViewModelFactory viewModelFactory;
    @Inject
    public MainActivity mainActivity;
    @Inject
    public ImageLoader imageLoader;
    private LocationDetailViewModel locationDetailViewModel;
    private @NonNull
    FavoriteLocation favoriteLocation;
    private NavController navController;

    @Override
    public FragmentDetailBinding bindView() {
        return FragmentDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getInjector().inject(this);
        setControl();
        setEvent();
    }

    private void setControl() {
        favoriteLocation = LocationDetailFragmentArgs.fromBundle(getArguments()).getFavoriteLocation();
        navController = Navigation.findNavController(binding.getRoot());
        locationDetailViewModel = new ViewModelProvider(this, viewModelFactory).get(LocationDetailViewModel.class);
    }

    private void setEvent() {
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        locationDetailViewModel.getLocationDetailLiveData().observe(viewLifecycleOwner, locationDetail -> {
                binding.tvLocationCodeInfo.setText(locationDetail.getCode());
                binding.tvLocationNameInfo.setText(locationDetail.getName());
                binding.tvLocationLatInfo.setText(String.valueOf(locationDetail.getLat()));
                binding.tvLocationLngInfo.setText(String.valueOf(locationDetail.getLng()));
                binding.tvLocationDescriptionInfo.setText(locationDetail.getDescription());
                imageLoader.loadImageWithFragment(favoriteLocation.getImage(), R.drawable.place, R.drawable.place, binding.ivLocationDetail);
        });
        locationDetailViewModel.getNavigateBackToFavoriteScreenLiveData().observe(viewLifecycleOwner, goBack -> {
                navController.popBackStack();
        });
        locationDetailViewModel.getShowProgressBarLiveData().observe(viewLifecycleOwner, visible -> {
                if (visible) {
                    mainActivity.showProgressBar();
                } else {
                    mainActivity.hideProgressBar();
                }
        });
        locationDetailViewModel.getLocationDetail(favoriteLocation.getId());
        binding.btnGoBack.setOnClickListener(view -> {
            locationDetailViewModel.onButtonBackClick();
        });
    }
}
