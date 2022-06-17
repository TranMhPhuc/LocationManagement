package com.learn.locationmanagement.ui.common.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<V extends ViewBinding> extends AppCompatActivity {
    protected V binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = bindView();
        setContentView(binding.getRoot());
    }

    public abstract V bindView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
