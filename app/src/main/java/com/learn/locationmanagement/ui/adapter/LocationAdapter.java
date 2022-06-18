package com.learn.locationmanagement.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.learn.locationmanagement.R;
import com.learn.locationmanagement.databinding.ItemLocationBinding;
import com.learn.locationmanagement.model.location.favorites.Location;
import com.learn.locationmanagement.ui.common.image.ImageLoader;

public class LocationAdapter extends ListAdapter<Location, LocationAdapter.LocationViewHolder> {
    public static final DiffUtil.ItemCallback<Location> DIFF_CALLBACK = new DiffUtil.ItemCallback<Location>() {
        @Override
        public boolean areItemsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Location oldItem, @NonNull Location newItem) {
            return oldItem.equals(newItem);
        }
    };

    protected LocationAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationViewHolder(
                ItemLocationBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        private ItemLocationBinding binding;
        private Context itemContext;
        private ImageLoader imageLoader;

        public LocationViewHolder(@NonNull ItemLocationBinding binding) {
            super(binding.getRoot());
            itemContext = binding.getRoot().getContext();
            imageLoader = new ImageLoader(itemContext);
        }

        public void bind(@NonNull Location location) {
            // TODO show info on the item: id, name and image with glide
            binding.tvLocationCode.setText(itemContext.getString(R.string.label_locationItem_code, location.getCode()));
            binding.tvLocationName.setText(itemContext.getString(R.string.label_locationItem_name, location.getName()));
            imageLoader.loadImage(location.getImage(), -1, -1, binding.ivLocationImage);
        }
    }
}
