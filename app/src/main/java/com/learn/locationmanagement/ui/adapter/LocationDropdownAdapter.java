package com.learn.locationmanagement.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.learn.locationmanagement.R;
import com.learn.locationmanagement.databinding.DropdownItemLocationBinding;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;

import java.util.List;

public class LocationDropdownAdapter extends ArrayAdapter<FavoriteLocation> {
    private final Filter locationFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((FavoriteLocation) resultValue).getName();
        }
    };
    private List<FavoriteLocation> favoriteLocations;

    public LocationDropdownAdapter(Context context, List<FavoriteLocation> favoriteLocations) {
        super(context, 0, favoriteLocations);
        this.favoriteLocations = favoriteLocations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @NonNull
    private View initView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DropdownItemLocationBinding binding;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.dropdown_item_location, parent, false
            );
        }
        binding = DropdownItemLocationBinding.bind(convertView);
        Context itemContext = binding.getRoot().getContext();
        FavoriteLocation location = getItem(position);
        if (location != null) {
            binding.tvDropdownLocationName.setText(location.getName());
            binding.tvDropdownLocationCode.setText(itemContext.getString(R.string.text_map_code, location.getCode()));
        }
        return binding.getRoot();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return locationFilter;
    }
}
