package com.learn.locationmanagement.ui;

import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.learn.locationmanagement.R;
import com.learn.locationmanagement.databinding.ActivityMainBinding;
import com.learn.locationmanagement.ui.common.activity.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupNavigationComponent();
    }

    @Override
    public ActivityMainBinding bindView() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    private void setupNavigationComponent() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        }
    }

    public void showProgressBar() {
        binding.progressCircular.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        binding.progressCircular.setVisibility(View.INVISIBLE);
    }
}