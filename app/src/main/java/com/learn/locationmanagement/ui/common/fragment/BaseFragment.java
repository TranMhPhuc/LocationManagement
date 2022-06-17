package com.learn.locationmanagement.ui.common.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<V extends ViewBinding> extends Fragment {
    protected V binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = bindView();
        return binding.getRoot();
    }

    public abstract V bindView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
