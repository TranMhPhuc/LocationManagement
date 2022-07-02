package com.learn.locationmanagement.ui.common.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewbinding.ViewBinding;

import com.learn.locationmanagement.LocationManagementApp;
import com.learn.locationmanagement.di.fragment.FragmentComponent;
import com.learn.locationmanagement.di.fragment.FragmentModule;
import com.learn.locationmanagement.di.presentation.PresentationComponent;

public abstract class BaseFragment<V extends ViewBinding> extends Fragment {
    protected V binding;
    private FragmentComponent fragmentComponent = null;
    private PresentationComponent presentationComponent = null;

    private FragmentComponent getFragmentComponent() {
        LocationManagementApp application = (LocationManagementApp) requireActivity().getApplication();
        if (application != null) {
            fragmentComponent = application
                    .getAppComponent()
                    .newFragmentComponentBuilder()
                    .fragment(this)
                    .build();
        }
        return fragmentComponent;
    }

    private PresentationComponent getPresentationComponent() {
        if (presentationComponent == null) {
            presentationComponent = getFragmentComponent().newPresentationComponent();
        }
        return presentationComponent;
    }

    protected PresentationComponent getInjector() {
        return getPresentationComponent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = bindView();
        return binding.getRoot();
    }

    public boolean isLifeCycleOnResumedState(LifecycleOwner lifecycleOwner) {
        return lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.RESUMED;
    }

    public abstract V bindView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
