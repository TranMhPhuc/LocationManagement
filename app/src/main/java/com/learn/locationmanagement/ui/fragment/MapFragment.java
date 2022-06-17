package com.learn.locationmanagement.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.learn.locationmanagement.databinding.FragmentMapBinding;
import com.learn.locationmanagement.ui.common.fragment.BaseFragment;

public class MapFragment extends BaseFragment<FragmentMapBinding> {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public FragmentMapBinding bindView() {
        return FragmentMapBinding.inflate(getLayoutInflater());
    }
}
