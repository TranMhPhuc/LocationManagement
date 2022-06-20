package com.learn.locationmanagement.ui.common.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewbinding.ViewBinding;

import com.learn.locationmanagement.R;

public abstract class BaseActivity<V extends ViewBinding> extends AppCompatActivity {
    protected V binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = bindView();
        setContentView(binding.getRoot());
        setupStatusBar();
    }

    private void setupStatusBar() {
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryColor));
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public abstract V bindView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
