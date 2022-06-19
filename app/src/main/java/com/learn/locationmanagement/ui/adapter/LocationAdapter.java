package com.learn.locationmanagement.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.locationmanagement.R;
import com.learn.locationmanagement.databinding.ItemLocationBinding;
import com.learn.locationmanagement.model.location.favorites.FavoriteLocation;
import com.learn.locationmanagement.ui.common.image.ImageLoader;

public class LocationAdapter extends ListAdapter<FavoriteLocation, LocationAdapter.LocationViewHolder> {
    public static final DiffUtil.ItemCallback<FavoriteLocation> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavoriteLocation>() {
        @Override
        public boolean areItemsTheSame(@NonNull FavoriteLocation oldItem, @NonNull FavoriteLocation newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavoriteLocation oldItem, @NonNull FavoriteLocation newItem) {
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

        public void bind(@NonNull FavoriteLocation favoriteLocation) {
            // TODO show info on the item: id, name and image with glide
            binding.tvLocationCode.setText(itemContext.getString(R.string.label_locationItem_code, favoriteLocation.getCode()));
            binding.tvLocationName.setText(itemContext.getString(R.string.label_locationItem_name, favoriteLocation.getName()));
            imageLoader.loadImage(favoriteLocation.getImage(), -1, -1, binding.ivLocationImage);
        }
    }
}
